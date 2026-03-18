package com.erarge.opevaontology.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.demo3.Demo3DatasetResponse;
import com.erarge.opevaontology.dto.demo3.Demo3EisPointDTO;
import com.erarge.opevaontology.dto.demo3.Demo3FilterOptionsResponse;
import com.erarge.opevaontology.dto.demo3.Demo3SummaryResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;
import com.erarge.opevaontology.dto.demo2.Demo2PowerPointDTO;
import com.erarge.opevaontology.dto.demo2.Demo2PowerResponseDTO;
import com.erarge.opevaontology.dto.demo2.Demo2PowerSummaryDTO;
import com.erarge.opevaontology.dto.demo5.KpisResponse;
import com.erarge.opevaontology.dto.demo5.RouteSizeDistributionDTO;
import com.erarge.opevaontology.dto.demo5.RouteTimeWindowDTO;
import com.erarge.opevaontology.repository.CustomSPARQL;
import com.erarge.opevaontology.repository.CustomSPARQLDemo5;
import com.erarge.opevaontology.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceImpl implements IService {

    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Your API DTOs currently use LocalDateTime; we will parse offset-aware values and convert.
    private static final DateTimeFormatter ISO_OFFSET_OR_LOCAL = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public Object sparql(String query) {
        return CustomSPARQL.sparqlQueryExecution(query);
    }

    @Override
    public Object sparqlDemo5(String query) {
        return CustomSPARQLDemo5.sparqlQueryExecution(query);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> queryByTimeInterval(TimeIntervalQueryRequest request) {
        if (request.getFrom() == null || request.getTo() == null) {
            throw new IllegalArgumentException("From and To dates are required");
        }
        String query = QueryBuilder.buildTimeIntervalQuery(request.getFrom(), request.getTo());
        Object result = CustomSPARQL.sparqlQueryExecution(query);
        return (List<Map<String, String>>) result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> queryFiltered(FilteredQueryRequest request) {
        if (request.getFrom() == null || request.getTo() == null) {
            throw new IllegalArgumentException("From and To dates are required");
        }
        String query = QueryBuilder.buildFilteredQuery(
            request.getFrom(),
            request.getTo(),
            request.getDistanceMm(),
            request.getSocPercent()
        );
        Object result = CustomSPARQL.sparqlQueryExecution(query);
        return (List<Map<String, String>>) result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FilterOptionsResponse getFilterOptions() {
        // Get time range
        String timeRangeQuery = QueryBuilder.buildTimeRangeQuery();
        Object timeRangeResult = CustomSPARQL.sparqlQueryExecution(timeRangeQuery);
        List<Map<String, String>> timeRangeList = (List<Map<String, String>>) timeRangeResult;

        LocalDateTime minTime = null;
        LocalDateTime maxTime = null;

        if (!timeRangeList.isEmpty()) {
            Map<String, String> timeRange = timeRangeList.get(0);

            String minTimeStr = stripDatatypeAndQuotes(timeRange.get("minTime"));
            String maxTimeStr = stripDatatypeAndQuotes(timeRange.get("maxTime"));

            // Parse as OffsetDateTime if timezone exists (e.g., ...Z), otherwise LocalDateTime.
            if (minTimeStr != null && !minTimeStr.isEmpty()) {
                minTime = parseToLocalDateTime(minTimeStr);
            }
            if (maxTimeStr != null && !maxTimeStr.isEmpty()) {
                maxTime = parseToLocalDateTime(maxTimeStr);
            }
        }

        // Get distance options
        String distanceQuery = QueryBuilder.buildDistanceOptionsQuery();
        Object distanceResult = CustomSPARQL.sparqlQueryExecution(distanceQuery);
        List<Map<String, String>> distanceList = (List<Map<String, String>>) distanceResult;

        List<Double> distanceOptions = distanceList.stream()
            .map(m -> stripDatatypeAndQuotes(m.get("distanceMm")))
            .filter(d -> d != null && !d.isEmpty())
            .map(ServiceImpl::tryParseDouble)
            .filter(d -> d != null)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        // Get SOC options
        String socQuery = QueryBuilder.buildSocOptionsQuery();
        Object socResult = CustomSPARQL.sparqlQueryExecution(socQuery);
        List<Map<String, String>> socList = (List<Map<String, String>>) socResult;

        List<Double> socOptions = socList.stream()
            .map(m -> stripDatatypeAndQuotes(m.get("socPercent")))
            .filter(s -> s != null && !s.isEmpty())
            .map(ServiceImpl::tryParseDouble)
            .filter(s -> s != null)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        return new FilterOptionsResponse(minTime, maxTime, distanceOptions, socOptions);
    }

    /**
     * Handles values like:
     * - "\"2025-04-28 09:51:06\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
     * - "2025-04-28T09:51:06Z^^http://www.w3.org/2001/XMLSchema#dateTime"
     * - "\"Umraniye\"@en"
     */
    private static String stripDatatypeAndQuotes(String raw) {
        if (raw == null) return null;

        String s = raw.trim();

        // If it's quoted, keep only the inside of the first "...".
        if (s.startsWith("\"")) {
            int end = s.indexOf('"', 1);
            if (end > 0) {
                s = s.substring(1, end);
            } else {
                // malformed, best effort: strip leading quote
                s = s.substring(1);
            }
        }

        // Remove datatype suffix if present (handles unquoted cases too)
        int idx = s.indexOf("^^");
        if (idx > 0) {
            s = s.substring(0, idx);
        }

        // Normalize Virtuoso space-separated datetime to ISO form
        s = s.replace(" ", "T").trim();

        return s;
    }

    private static LocalDateTime parseToLocalDateTime(String iso) {
        // If it has timezone info (Z or +hh:mm), parse as OffsetDateTime then drop zone.
        if (iso.endsWith("Z") || iso.matches(".*[+-]\\d{2}:\\d{2}$")) {
            return OffsetDateTime.parse(iso, ISO_OFFSET_OR_LOCAL).toLocalDateTime();
        }
        return LocalDateTime.parse(iso, ISO_OFFSET_OR_LOCAL);
    }

    private static Double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    
    @Override
    @SuppressWarnings("unchecked")
    public Demo3FilterOptionsResponse getDemo3FilterOptions(String battery) {
        Object batteryRaw = CustomSPARQL.sparqlQueryExecution(QueryBuilderDemo3.buildBatteryOptionsQuery());
        List<Map<String, String>> batteryRows = (List<Map<String, String>>) batteryRaw;
        List<String> batteries = batteryRows.stream()
            .map(row -> row.get("battery"))
            .filter(value -> value != null && !value.isBlank())
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        Object socRaw = CustomSPARQL.sparqlQueryExecution(QueryBuilderDemo3.buildSocOptionsQuery(battery));
        List<Map<String, String>> socRows = (List<Map<String, String>>) socRaw;
        List<Double> socValues = socRows.stream()
            .map(row -> row.get("soc"))
            .map(ServiceImpl::tryParseDouble)
            .filter(value -> value != null)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        return new Demo3FilterOptionsResponse(batteries, socValues);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Demo3DatasetResponse getDemo3Dataset(String battery, Double soc) {
        Object raw = CustomSPARQL.sparqlQueryExecution(QueryBuilderDemo3.buildEisDatasetQuery(battery, soc));
        List<Map<String, String>> rows = (List<Map<String, String>>) raw;

        List<Demo3EisPointDTO> points = rows.stream()
            .map(this::mapDemo3Point)
            .filter(point -> point != null)
            .sorted(Comparator
                .comparing(Demo3EisPointDTO::getBattery)
                .thenComparingDouble(Demo3EisPointDTO::getSoc)
                .thenComparingDouble(Demo3EisPointDTO::getFrequency))
            .collect(Collectors.toList());

        Set<String> batteries = new LinkedHashSet<>();
        Set<Double> socValues = new LinkedHashSet<>();
        Double minFrequency = null;
        Double maxFrequency = null;
        Double maxImpedanceMagnitude = null;
        double phaseSum = 0.0;

        for (Demo3EisPointDTO point : points) {
            batteries.add(point.getBattery());
            socValues.add(point.getSoc());

            if (minFrequency == null || point.getFrequency() < minFrequency) minFrequency = point.getFrequency();
            if (maxFrequency == null || point.getFrequency() > maxFrequency) maxFrequency = point.getFrequency();
            if (maxImpedanceMagnitude == null || point.getImpedanceMagnitude() > maxImpedanceMagnitude) {
                maxImpedanceMagnitude = point.getImpedanceMagnitude();
            }
            phaseSum += point.getImpedancePhase();
        }

        Double averagePhase = points.isEmpty() ? null : phaseSum / points.size();
        Demo3SummaryResponse summary = new Demo3SummaryResponse(
            batteries.size(),
            socValues.size(),
            points.size(),
            minFrequency,
            maxFrequency,
            maxImpedanceMagnitude,
            averagePhase
        );

        return new Demo3DatasetResponse(summary, points);
    }

    private Demo3EisPointDTO mapDemo3Point(Map<String, String> row) {
        String battery = row.get("battery");
        Double soc = tryParseDouble(row.get("soc"));
        Double frequency = tryParseDouble(row.get("f"));
        Double re = tryParseDouble(row.get("re"));
        Double im = tryParseDouble(row.get("im"));
        Double zMag = tryParseDouble(row.get("zMag"));
        Double zPhase = tryParseDouble(row.get("zPhase"));

        if (battery == null || soc == null || frequency == null || re == null || im == null || zMag == null || zPhase == null) {
            return null;
        }

        return new Demo3EisPointDTO(battery, soc, frequency, re, im, zMag, zPhase);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Demo2PowerResponseDTO getDemo2PowerSeries() {
        String query = QueryBuilderDemo2.buildVoltageCurrentPowerQuery();
        Object result = CustomSPARQL.sparqlQueryExecution(query);
        List<Map<String, String>> rows = (List<Map<String, String>>) result;

        List<Demo2PowerPointDTO> points = rows.stream()
            .map(this::toDemo2PowerPoint)
            .filter(p -> p != null)
            .sorted(Comparator.comparing(Demo2PowerPointDTO::getTime, Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList());

        Demo2PowerSummaryDTO summary = buildDemo2PowerSummary(points);
        return new Demo2PowerResponseDTO(summary, points);
    }

    private Demo2PowerPointDTO toDemo2PowerPoint(Map<String, String> row) {
        String time = stripDatatypeAndQuotes(row.get("time"));
        Double voltage = tryParseDouble(stripDatatypeAndQuotes(row.get("voltage")));
        Double current = tryParseDouble(stripDatatypeAndQuotes(row.get("current")));
        Double powerW = tryParseDouble(stripDatatypeAndQuotes(row.get("power_W")));

        if (time == null || time.isBlank() || voltage == null || current == null || powerW == null) {
            return null;
        }

        return new Demo2PowerPointDTO(time, voltage, current, powerW);
    }

    private Demo2PowerSummaryDTO buildDemo2PowerSummary(List<Demo2PowerPointDTO> points) {
        if (points == null || points.isEmpty()) {
            return new Demo2PowerSummaryDTO(0, null, null, 0, 0, 0, 0, 0, 0, 0);
        }

        double minVoltage = points.stream().mapToDouble(Demo2PowerPointDTO::getVoltage).min().orElse(0);
        double maxVoltage = points.stream().mapToDouble(Demo2PowerPointDTO::getVoltage).max().orElse(0);

        double minCurrent = points.stream().mapToDouble(Demo2PowerPointDTO::getCurrent).min().orElse(0);
        double maxCurrent = points.stream().mapToDouble(Demo2PowerPointDTO::getCurrent).max().orElse(0);

        double minPowerW = points.stream().mapToDouble(Demo2PowerPointDTO::getPowerW).min().orElse(0);
        double maxPowerW = points.stream().mapToDouble(Demo2PowerPointDTO::getPowerW).max().orElse(0);
        double averagePowerW = points.stream().mapToDouble(Demo2PowerPointDTO::getPowerW).average().orElse(0);

        String startTime = points.stream().map(Demo2PowerPointDTO::getTime).min(String::compareTo).orElse(null);
        String endTime = points.stream().map(Demo2PowerPointDTO::getTime).max(String::compareTo).orElse(null);

        return new Demo2PowerSummaryDTO(
            points.size(),
            startTime,
            endTime,
            minVoltage,
            maxVoltage,
            minCurrent,
            maxCurrent,
            minPowerW,
            maxPowerW,
            averagePowerW
        );
    }

    @Override
    public KpisResponse getKpis() {
        String sparql = QueryBuilderDemo5.buildKpisQuery();
        Object raw = CustomSPARQLDemo5.sparqlQueryExecution(sparql); // your call
        JsonNode root = toJson(raw);
        long routes = 0, nodes = 0, customers = 0, cs = 0, depots = 0;
        JsonNode b = root.get(0);
        routes = b.get("routes").asLong();
        nodes = b.get("nodes").asLong();
        customers = b.get("customers").asLong();
        cs = b.get("chargingStations").asLong();
        depots = b.get("depots").asLong();
        return new KpisResponse(routes, nodes, customers, cs, depots);
    }

    @Override
    public List<RouteSizeDistributionDTO> getRouteMixSizeDistribution() {
        String sparql = QueryBuilderDemo5.buildRouteMixSizeDistributionQuery();
        Object raw = CustomSPARQLDemo5.sparqlQueryExecution(sparql);
        JsonNode root = toJson(raw);
        JsonNode bindings = getBindings(root);
        List<RouteSizeDistributionDTO> out = new ArrayList<>();
        if (root.isArray()) {
            for (JsonNode b : root) {
                int size = b.get("size").asInt();
                String distribution = b.get("distribution").asText();
                long routes = b.get("routes").asLong();
                out.add(new RouteSizeDistributionDTO(size, distribution, routes));
            }
        }
        return out;
    }

    @Override
    public List<RouteTimeWindowDTO> getRouteMixTimeWindow() {
        String sparql = QueryBuilderDemo5.buildRouteMixTimeWindowQuery();
        Object raw = CustomSPARQLDemo5.sparqlQueryExecution(sparql);
        JsonNode root = toJson(raw);
        JsonNode bindings = getBindings(root);
        List<RouteTimeWindowDTO> out = new ArrayList<>();
        if (root.isArray()) {
            for (JsonNode b : root) {
                int tw = b.get("tw").asInt();
                long routes = b.get("routes").asLong();
                out.add(new RouteTimeWindowDTO(tw, routes));
            }
        }
        return out;
    }

    // ---------- helpers for SPARQL-JSON ----------
    private static JsonNode toJson(Object raw) {
        return MAPPER.valueToTree(raw);
    }
    private static JsonNode getBindings(JsonNode root) {
        JsonNode results = root.path(0);
        return results.path("bindings");
    }
    private static String asString(JsonNode binding, String var) {
        JsonNode v = binding.path(var).path("value");
        return v.isMissingNode() ? null : v.asText();
    }
    private static long asLong(JsonNode binding, String var) {
        String s = asString(binding, var);
        if (s == null || s.isEmpty()) return 0L;
        try { return Math.round(Double.parseDouble(s)); } catch (Exception ignore) { return 0L; }
    }

}
