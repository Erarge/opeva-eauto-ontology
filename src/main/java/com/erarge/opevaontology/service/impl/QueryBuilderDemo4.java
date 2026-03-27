package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo4 {

    public static String buildEnergyRateQuery() {
        return "PREFIX eauto: <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
               "PREFIX data:  <https://cloud.erarge.com.tr/data/opeva/demo4#>\n" +
               "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" +
               "# ─────────────────────────────────────────────────────────────────────────────\n" +
               "# Q14  INSTANTANEOUS ENERGY RATE vs SPEED\n" +
               "# Chart : Scatter plot (x = speed_kmh, y = instantaneous energy rate,\n" +
               "#                       colour = weather, size = sensorPower_W)\n" +
               "# Vars  : weather | frame | speed_kmh | energyConsumed_kWh |\n" +
               "#         sensorPower_W | soc_pct\n" +
               "# Use   : Reveal non-linear relationship between speed and power demand;\n" +
               "#         overlay sensor load to separate traction from perception cost.\n" +
               "# ─────────────────────────────────────────────────────────────────────────────\n" +
               "SELECT\n" +
               "  ?weather\n" +
               "  ?frame\n" +
               "  ?speed_kmh\n" +
               "  ?energyConsumed_kWh\n" +
               "  ?sensorPower_W\n" +
               "  ?soc_pct\n" +
               "FROM <https://cloud.erarge.com.tr/data/opeva/demo4>\n" +
               "WHERE {\n" +
               "  ?session a eauto:DrivingSession ;\n" +
               "    eauto:session_weather_label ?weather ;\n" +
               "    eauto:has_digital_twin_obs  ?obs .\n" +
               "  ?obs\n" +
               "    eauto:obs_frame               ?frame ;\n" +
               "    eauto:obs_speed_kmh           ?speed_kmh ;\n" +
               "    eauto:obs_energy_consumed_kwh ?energyConsumed_kWh ;\n" +
               "    eauto:obs_sensor_power_w      ?sensorPower_W ;\n" +
               "    eauto:obs_soc_pct             ?soc_pct .\n" +
               "  FILTER(?speed_kmh > 0)\n" +
               "}\n" +
               "ORDER BY ?weather ?frame";
    }
}
