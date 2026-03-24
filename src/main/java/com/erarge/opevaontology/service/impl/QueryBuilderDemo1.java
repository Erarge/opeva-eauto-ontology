package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo1 {

    public static String buildCellBalancingTimeByVoltageDifferenceQuery() {
        return "PREFIX : <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
                "SELECT (FLOOR(?vdiff * 1000) AS ?vdiffBin)\n" +
                "       (AVG(?bt) AS ?avgBalTimeSec)\n" +
                "FROM <https://cloud.erarge.com.tr/data/opeva/demo1>\n" +
                "WHERE {\n" +
                "  ?obs :cell_voltage_difference ?vdiff ;\n" +
                "       :cell_balancing_time_sec ?bt .\n" +
                "}\n" +
                "GROUP BY FLOOR(?vdiff * 1000)\n" +
                "ORDER BY ?vdiffBin";
    }
}
