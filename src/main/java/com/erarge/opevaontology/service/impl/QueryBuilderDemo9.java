package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo9 {

    public static String buildActivePowerSeriesQuery() {
        return "PREFIX : <https://cloud.erarge.com.tr/ontologies/eauto#>\n\n" +
               "SELECT ?timestamp ?activePowerW ?isShiftable\n" +
               "WHERE {\n" +
               "  ?appliance :has_operating_cycle ?cycle .\n\n" +
               "  ?cycle :is_demand_response_eligible ?isShiftable ;\n" +
               "         :has_slow_observation ?obs .\n\n" +
               "  ?obs :obs_timestamp_unix ?timestamp ;\n" +
               "       :obs_active_power_W ?activePowerW .\n" +
               "}\n" +
               "ORDER BY ?timestamp\n" +
               "LIMIT 5000";
    }
}
