import sys
import json

html_content = """<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>OPEVA E-Auto Ontology Query Interface - Demo 7</title>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
  <link rel="stylesheet" href="../css/demo.css">
</head>

<body class="demo-analytics">
  <div class="topbar">
    <a class="back-link" href="../index.html">← Back</a>
  </div>

  <div class="container">
    <div class="header">
      <h1>Demo 7: Modular batteries storage based second-life EV module variants</h1>
      <p style="margin-top: 10px; color: #d1d5db; font-size: 0.95rem; line-height: 1.6;">
        Demonstration 7 uses second-life EV batteries as an energy storage solution (ESS) in an EV charger station with
        photovoltaic panels and a utility grid connection. The query interface below allows you to explore
        efficiency, temperatures, active power, and anomalies of second-life batteries over time.
      </p>
      <div class="figures-row" style="margin-top:20px;">
        <figure class="figure w45">
          <img src="../img/demo7-1.webp" alt="Demonstration 7 Architecture" class="header-img" />
          <figcaption class="figure-caption">Figure 1: Demonstration 7 Architecture</figcaption>
        </figure>

        <figure class="figure w45">
          <img src="../img/demo7-2.webp" alt="Second-life Battery Pack System" class="header-img header-img-tall" />
          <figcaption class="figure-caption">Figure 2: Second-life Battery Pack System</figcaption>
        </figure>
      </div>
    </div>

    <div class="content">
      <div id="error-message" class="error" style="display:none"></div>
      <div id="success-message" class="success" style="display:none"></div>

      <div class="form-section">
        <h2>Query Explorer</h2>
        
        <form id="query-form" style="margin-top: 16px;">
          <div style="display: flex; gap: 16px; margin-bottom: 12px; flex-wrap: wrap;">
            <div style="flex: 1; min-width: 200px;">
              <label for="from-time" style="display:block; margin-bottom:5px; color:#d1d5db;">From Time *</label>
              <input type="datetime-local" id="from-time" name="from" style="width: 100%; padding: 8px; border-radius: 4px; background:#1f2937; border:1px solid #374151; color:#fff;" required />
            </div>
            <div style="flex: 1; min-width: 200px;">
              <label for="to-time" style="display:block; margin-bottom:5px; color:#d1d5db;">To Time *</label>
              <input type="datetime-local" id="to-time" name="to" style="width: 100%; padding: 8px; border-radius: 4px; background:#1f2937; border:1px solid #374151; color:#fff;" required />
            </div>
          </div>

          <div style="display: flex; gap: 16px; margin-bottom: 20px; flex-wrap: wrap;">
            <div style="flex: 1; min-width: 200px;">
              <label for="distance-mm" style="display:block; margin-bottom:5px; color:#d1d5db;">Coil Distance (mm)</label>
              <select id="distance-mm" name="distanceMm" style="width: 100%; padding: 8px; border-radius: 4px; background:#1f2937; border:1px solid #374151; color:#fff;">
                <option value="">All distances</option>
              </select>
            </div>
            <div style="flex: 1; min-width: 200px;">
              <label for="soc-percent" style="display:block; margin-bottom:5px; color:#d1d5db;">SOC (%)</label>
              <select id="soc-percent" name="socPercent" style="width: 100%; padding: 8px; border-radius: 4px; background:#1f2937; border:1px solid #374151; color:#fff;">
                <option value="">All SOC values</option>
              </select>
            </div>
          </div>
        </form>

        <div class="button-group" style="margin-bottom: 16px;">
          <button class="btn-primary" onclick="queryByTimeInterval()">Query by Time Interval</button>
          <button class="btn-primary" onclick="queryFiltered()">Query with Filters</button>
          <button class="btn-primary" onclick="queryFunctionalSafety()" style="background-color: #d9534f; border-color: #d43f3a;">Functional Safety Check</button>
          <button class="btn-secondary" onclick="downloadCsv()">Download CSV</button>
          <button class="btn-secondary" onclick="downloadJson()">Download JSON</button>
          <button class="btn-clear" onclick="clearForm()">Clear Form</button>
        </div>
        
        <div class="info-box muted" id="options-info" style="font-size: 0.9em; padding: 10px; background:#1f2937; border-radius:4px;">
          Loading filter options...
        </div>
      </div>

      <div class="kpi-grid mt-24">
        <div class="kpi-card">
          <div class="kpi-label">Total Results</div>
          <div class="kpi-value" id="kpiSampleCount">-</div>
        </div>
        <div class="kpi-card">
          <div class="kpi-label" id="kpiLabel2">Avg Efficiency</div>
          <div class="kpi-value" id="kpiValue2">-</div>
        </div>
        <div class="kpi-card">
          <div class="kpi-label" id="kpiLabel3">Max Power</div>
          <div class="kpi-value" id="kpiValue3">-</div>
        </div>
        <div class="kpi-card">
          <div class="kpi-label" id="kpiLabel4">Total Sessions</div>
          <div class="kpi-value" id="kpiValue4">-</div>
        </div>
      </div>

      <div class="info-grid mt-24">
        <div class="info-card">
          <h3 class="section-title">Active SPARQL</h3>
          <div class="mono" id="queryText" style="white-space: pre-wrap; word-break: break-all; font-size: 13px;">No query executed yet.</div>
        </div>
        <div class="info-card">
          <h3 class="section-title" id="datasetNotesTitle">Dataset notes</h3>
          <div id="datasetMeta" class="muted">No data loaded yet. Use the Query Parameters above to fetch data.</div>
        </div>
      </div>

      <div id="charts-wrapper" style="display: none;">
        <div class="chart-card mt-24">
          <h3 class="section-title">Efficiency vs Power</h3>
          <div class="chart-wrap">
            <canvas id="efficiencyChart"></canvas>
          </div>
        </div>
        <div class="chart-card mt-24">
          <h3 class="section-title">Temperatures vs Power</h3>
          <div class="chart-wrap">
            <canvas id="temperatureChart"></canvas>
          </div>
        </div>
      </div>

      <div class="form-section mt-28">
        <h2>Query Results Raw Data</h2>
        <div class="toolbar-inline">
          <span class="muted" id="tableMeta">0 rows</span>
        </div>
        <div class="table-container" id="results-container">
          <div class="empty-state">No results to display.</div>
        </div>
      </div>
    </div>
  </div>

  <script>
    const API_BASE_URL = window.location.origin;

    const SPARQL_TIME_INTERVAL = `SELECT ?session ?tNorm ?p_coil_w ?t_coil_c ?t_ambiant_c ?efficiency\\nWHERE { ... FILTER(?tNorm >= ?from && ?tNorm <= ?to) }\\nORDER BY ?session ?tNorm`;
    const SPARQL_FILTERED = `SELECT ?session ?tNorm ...\\nWHERE { ... FILTER(?soc_pct = ?socPercent && ?dist_mm = ?distanceMm) }`;
    const SPARQL_SAFETY = `SELECT ?battery ?obs ?max_temp ?min_voltage ?soc\\nWHERE {\\n  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\\n    ?battery :hasObservation ?obs .\\n    ?obs :bms_max_cell_temperature ?max_temp ;\\n         :bms_min_cell_voltage ?min_voltage ;\\n         :battery_state_of_charge ?soc .\\n    FILTER (?max_temp > 45.0 || ?min_voltage < 2.5)\\n  }\\n}\\nORDER BY DESC(?max_temp)\\nLIMIT 50`;

    let cachedDataset = [];
    let efficiencyChart = null;
    let temperatureChart = null;

    const sessionColors = [
      'rgba(102, 126, 234, 1)', 'rgba(118, 75, 162, 1)', 'rgba(237, 100, 166, 1)', 'rgba(255, 159, 64, 1)',
      'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)',
      'rgba(255, 206, 86, 1)', 'rgba(201, 203, 207, 1)'
    ];

    function el(id) { return document.getElementById(id); }
    function setText(id, value) { const target = el(id); if (target) target.textContent = value; }

    function showError(message) {
      el('error-message').textContent = message || 'An unexpected error occurred.';
      el('error-message').style.display = 'block';
      el('success-message').style.display = 'none';
      el('charts-wrapper').style.display = 'none';
    }

    function showSuccess(message) {
      el('success-message').textContent = message || '';
      el('success-message').style.display = 'block';
      el('error-message').style.display = 'none';
    }

    function hideMessages() {
      el('error-message').style.display = 'none';
      el('success-message').style.display = 'none';
    }

    function escapeHtml(text) {
      const div = document.createElement('div');
      div.textContent = text;
      return div.innerHTML;
    }

    function cleanValue(v) {
      if (typeof v === 'string') return v.replace(/^"|"$/g, '').replace(/\\^\\^.*$/, '').trim();
      return v;
    }

    async function loadFilterOptions() {
      try {
        const response = await fetch(`${API_BASE_URL}/api/filter-options`);
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        const data = await response.json();
        
        el('options-info').innerHTML = `
          <strong>Available ranges:</strong><br>
          Time: ${data.minTime ? new Date(data.minTime).toLocaleString() : 'N/A'} to ${data.maxTime ? new Date(data.maxTime).toLocaleString() : 'N/A'} <br>
          Distance options: ${data.distanceMmOptions ? data.distanceMmOptions.join(', ') : 'None'} mm <br>
          SOC options: ${data.socPercentOptions ? data.socPercentOptions.join(', ') : 'None'} %
        `;

        const distanceSelect = el('distance-mm');
        distanceSelect.innerHTML = '<option value="">All distances</option>';
        (data.distanceMmOptions || []).forEach(distance => {
          const option = document.createElement('option');
          option.value = distance;
          option.textContent = `${distance} mm`;
          distanceSelect.appendChild(option);
        });

        const socSelect = el('soc-percent');
        socSelect.innerHTML = '<option value="">All SOC values</option>';
        (data.socPercentOptions || []).forEach(soc => {
          const option = document.createElement('option');
          option.value = soc;
          option.textContent = `${soc}%`;
          socSelect.appendChild(option);
        });

        if (data.minTime && !el('from-time').value) {
          el('from-time').value = new Date(data.minTime).toISOString().slice(0, 16);
        }
        if (data.maxTime && !el('to-time').value) {
          el('to-time').value = new Date(data.maxTime).toISOString().slice(0, 16);
        }
      } catch (error) {
        el('options-info').textContent = 'Failed to load filter options.';
        console.error(error);
      }
    }

    window.addEventListener('DOMContentLoaded', () => {
      loadFilterOptions();
    });

    async function queryByTimeInterval() {
      const from = el('from-time').value;
      const to = el('to-time').value;
      if (!from || !to) { showError('Please provide both From Time and To Time'); return; }
      
      const reqBody = { from: from + ':00', to: to + ':00' };
      setText('queryText', SPARQL_TIME_INTERVAL.replace('?from', \`"\${from}"\`).replace('?to', \`"\${to}"\`));
      await executeQuery('/api/query/time-interval', reqBody, false);
    }

    async function queryFiltered() {
      const from = el('from-time').value;
      const to = el('to-time').value;
      const dist = el('distance-mm').value;
      const soc = el('soc-percent').value;
      if (!from || !to) { showError('Please provide both From Time and To Time'); return; }

      const reqBody = { 
        from: from + ':00', 
        to: to + ':00',
        distanceMm: dist ? parseFloat(dist) : null,
        socPercent: soc ? parseFloat(soc) : null
      };
      
      let sparqlText = SPARQL_FILTERED.replace('?from', \`"\${from}"\`).replace('?to', \`"\${to}"\`);
      setText('queryText', sparqlText);
      await executeQuery('/api/query/filtered', reqBody, false);
    }

    async function queryFunctionalSafety() {
      setText('queryText', SPARQL_SAFETY);
      await executeQuery('/api/query/functional-safety', null, true);
    }

    function clearForm() {
      el('query-form').reset();
      el('results-container').innerHTML = '<div class="empty-state">No results to display.</div>';
      el('charts-wrapper').style.display = 'none';
      if(efficiencyChart) efficiencyChart.destroy();
      if(temperatureChart) temperatureChart.destroy();
      hideMessages();
      setText('tableMeta', '0 rows');
      setText('kpiSampleCount', '-');
      setText('kpiValue2', '-');
      setText('kpiValue3', '-');
      setText('kpiValue4', '-');
      cachedDataset = [];
    }

    async function executeQuery(endpoint, requestBody, isGet) {
      hideMessages();
      cachedDataset = [];
      el('results-container').innerHTML = '<div class="empty-state">Loading...</div>';

      try {
        let response;
        if (isGet) {
          response = await fetch(API_BASE_URL + endpoint, { method: 'GET' });
        } else {
          response = await fetch(API_BASE_URL + endpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
          });
        }

        if (!response.ok) throw new Error(\`HTTP \${response.status}\`);
        
        cachedDataset = await response.json();
        
        if (!Array.isArray(cachedDataset) || cachedDataset.length === 0) {
          showSuccess('Query executed successfully, but no results found.');
          clearResultsView();
          return;
        }

        renderTable(cachedDataset);
        renderMetricsAndCharts(cachedDataset, endpoint.includes('safety'));

        showSuccess(\`Query executed successfully. Found \${cachedDataset.length} results.\`);
      } catch (error) {
        showError('Query failed: ' + error.message);
        clearResultsView();
      }
    }

    function clearResultsView() {
      el('results-container').innerHTML = '<div class="empty-state">No results to display.</div>';
      el('charts-wrapper').style.display = 'none';
      setText('tableMeta', '0 rows');
    }

    function renderTable(data) {
      const keys = Array.from(new Set(data.flatMap(Object.keys))).sort();
      let html = '<table><thead><tr>';
      keys.forEach(k => { html += \`<th>\${k}</th>\`; });
      html += '</tr></thead><tbody>';

      data.forEach(r => {
        html += '<tr>';
        keys.forEach(k => {
          html += \`<td>\${escapeHtml(cleanValue(r[k] ?? ''))}</td>\`;
        });
        html += '</tr>';
      });
      html += '</tbody></table>';

      el('results-container').innerHTML = html;
      setText('tableMeta', \`\${data.length} rows\`);
    }

    function renderMetricsAndCharts(data, isSafety) {
      setText('kpiSampleCount', data.length);
      
      if (isSafety) {
        // Safety metrics
        setText('kpiLabel2', 'Max Recorded Temp');
        setText('kpiLabel3', 'Min Recorded Volt');
        setText('kpiLabel4', 'Anomalous Batteries');

        let maxTemp = 0;
        let minVolt = 999;
        const uniqueBatteries = new Set();

        data.forEach(r => {
          const t = parseFloat(cleanValue(r.max_temp || '0'));
          const v = parseFloat(cleanValue(r.min_voltage || '999'));
          if(t > maxTemp) maxTemp = t;
          if(v < minVolt) minVolt = v;
          if(r.battery) uniqueBatteries.add(cleanValue(r.battery));
        });

        setText('kpiValue2', maxTemp > 0 ? \`\${maxTemp.toFixed(1)} °C\` : '-');
        setText('kpiValue3', minVolt < 999 ? \`\${minVolt.toFixed(2)} V\` : '-');
        setText('kpiValue4', uniqueBatteries.size);
        
        el('charts-wrapper').style.display = 'none';
        setText('datasetNotesTitle', 'Safety Anomalies');
        setText('datasetMeta', \`Found \${data.length} anomalous readings from \${uniqueBatteries.size} unique batteries, where temperatures exceeded 45°C or voltages dropped below 2.5V.\`);
      } else {
        // Normal power/efficiency metrics
        setText('kpiLabel2', 'Avg Efficiency');
        setText('kpiLabel3', 'Max Power');
        setText('kpiLabel4', 'Total Sessions');

        let totalEff = 0;
        let effCount = 0;
        let maxPower = 0;
        const sessionGroups = {};

        data.forEach((r, idx) => {
          const session = cleanValue(r.session || \`session_\${idx}\`);
          if (!sessionGroups[session]) sessionGroups[session] = [];
          sessionGroups[session].push(r);

          const eff = parseFloat(cleanValue(r.efficiency));
          if(Number.isFinite(eff)) { totalEff += eff; effCount++; }

          const pwr = calculatePower(r);
          if(Number.isFinite(pwr) && pwr > maxPower) maxPower = pwr;
        });

        const avgEff = effCount > 0 ? (totalEff / effCount)*100 : 0;
        
        setText('kpiValue2', effCount > 0 ? \`\${avgEff.toFixed(1)} %\` : '-');
        setText('kpiValue3', maxPower > 0 ? \`\${maxPower.toFixed(0)} W\` : '-');
        setText('kpiValue4', Object.keys(sessionGroups).length);
        
        setText('datasetNotesTitle', 'Dataset notes');
        setText('datasetMeta', \`Loaded \${data.length} query records encompassing \${Object.keys(sessionGroups).length} charging sessions. Displaying efficiency and temperature characteristics plotted against power.\`);

        el('charts-wrapper').style.display = 'block';
        generateEfficiencyChart(sessionGroups);
        generateTemperatureChart(sessionGroups);
      }
    }

    function calculatePower(r) {
      let p = parseFloat(cleanValue(r.p_coil_w || r.pCoilW));
      if (Number.isFinite(p)) return p;
      let v = parseFloat(cleanValue(r.v_coil_rms_v || r.vCoilRmsV));
      let i = parseFloat(cleanValue(r.i_coil_rms_a || r.iCoilRmsA));
      if (Number.isFinite(v) && Number.isFinite(i)) return v * i;
      return null;
    }

    function parseTime(t) {
      if (!t) return null;
      return new Date(cleanValue(t)).getTime();
    }

    function generateEfficiencyChart(sessionGroups) {
      const canvas = el('efficiencyChart');
      if (!canvas) return;
      const ctx = canvas.getContext('2d');
      if (efficiencyChart) efficiencyChart.destroy();

      const datasets = [];
      const sessionKeys = Object.keys(sessionGroups);

      sessionKeys.forEach((sessionKey, sessionIndex) => {
        const sessionData = sessionGroups[sessionKey];
        const color = sessionColors[sessionIndex % sessionColors.length];

        const points = sessionData
          .map(r => {
            const power = calculatePower(r);
            const effRaw = parseFloat(cleanValue(r.efficiency));
            return { x: power, y: effRaw * 100, time: parseTime(r.tNorm || r.timestamp) };
          })
          .filter(p => Number.isFinite(p.x) && p.x > 0 && Number.isFinite(p.y) && p.y >= 0 && p.y <= 100)
          .sort((a, b) => a.x - b.x);

        if (points.length > 0) {
          datasets.push({
            label: \`\${sessionKey} Efficiency\`,
            data: points,
            borderColor: color,
            backgroundColor: color.replace('1)', '0.2)'),
            borderWidth: 2,
            fill: false,
            tension: 0.2,
            pointRadius: 0
          });
        }
      });

      efficiencyChart = new Chart(ctx, {
        type: 'line',
        data: { datasets },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          interaction: { mode: 'index', intersect: false },
          scales: {
            x: { type: 'linear', title: { display: true, text: 'Power (W)' } },
            y: { title: { display: true, text: 'Efficiency (%)' }, max: 100 }
          }
        }
      });
    }

    function generateTemperatureChart(sessionGroups) {
      const canvas = el('temperatureChart');
      if (!canvas) return;
      const ctx = canvas.getContext('2d');
      if (temperatureChart) temperatureChart.destroy();

      const datasets = [];
      const sessionKeys = Object.keys(sessionGroups);

      sessionKeys.forEach((sessionKey, sessionIndex) => {
        const sessionData = sessionGroups[sessionKey];
        const baseColor = sessionColors[sessionIndex % sessionColors.length];

        const coilPoints = [];
        const ambientPoints = [];

        sessionData.forEach(r => {
          const power = calculatePower(r);
          const coilTemp = parseFloat(cleanValue(r.t_coil_c || r.tCoilC));
          const ambientTemp = parseFloat(cleanValue(r.t_ambiant_c || r.tAmbiantC || r.t_ambiantC));

          if (Number.isFinite(power) && power > 0) {
            if (Number.isFinite(coilTemp)) coilPoints.push({ x: power, y: coilTemp });
            if (Number.isFinite(ambientTemp)) ambientPoints.push({ x: power, y: ambientTemp });
          }
        });

        coilPoints.sort((a, b) => a.x - b.x);
        ambientPoints.sort((a, b) => a.x - b.x);

        if (coilPoints.length > 0) {
          datasets.push({
            label: \`\${sessionKey} Coil Temp\`,
            data: coilPoints,
            borderColor: baseColor,
            borderWidth: 2,
            tension: 0.2,
            pointRadius: 0,
            fill: false
          });
        }

        if (ambientPoints.length > 0) {
          const ambientColor = baseColor.replace('1)', '0.5)');
          datasets.push({
            label: \`\${sessionKey} Ambient Temp\`,
            data: ambientPoints,
            borderColor: ambientColor,
            borderWidth: 2,
            borderDash: [5, 5],
            tension: 0.2,
            pointRadius: 0,
            fill: false
          });
        }
      });

      temperatureChart = new Chart(ctx, {
        type: 'line',
        data: { datasets },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          interaction: { mode: 'index', intersect: false },
          scales: {
            x: { type: 'linear', title: { display: true, text: 'Power (W)' } },
            y: { title: { display: true, text: 'Temperature (°C)' } }
          }
        }
      });
    }

    function downloadFile(filename, content, type) {
      const blob = new Blob([content], { type });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      a.remove();
      URL.revokeObjectURL(url);
    }

    function downloadCsv() {
      if (!cachedDataset.length) return showError('No data to download');
      const keys = Array.from(new Set(cachedDataset.flatMap(Object.keys))).sort();
      const csv = [
        keys.join(','),
        ...cachedDataset.map(r => keys.map(k => \`"\${cleanValue(r[k] ?? '')}"\`).join(','))
      ].join('\\n');
      downloadFile('demo7_results.csv', csv, 'text/csv');
    }

    function downloadJson() {
      if (!cachedDataset.length) return showError('No data to download');
      downloadFile('demo7_results.json', JSON.stringify(cachedDataset, null, 2), 'application/json');
    }

  </script>
</body>
</html>
"""

with open(r'c:\Users\ibrahimarif\Desktop\____Opeva\Development\spring-workplace\opeva-eauto-ontology\src\main\resources\static\demo\demo7.html', 'w', encoding='utf-8') as f:
    f.write(html_content)

print("demo7.html rewritten successfully!")
