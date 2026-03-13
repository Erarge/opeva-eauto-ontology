package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo3 {

    private static final String PREFIXES =
        "PREFIX :    <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
        "PREFIX bif: <bif:>\n";

    public static String buildBatteryOptionsQuery() {
        return PREFIXES +
            "SELECT DISTINCT ?battery\n" +
            "WHERE {\n" +
            "  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\n" +
            "    ?b a :Battery .\n" +
            "    BIND(REPLACE(STR(?b), '^.*(#|/)', '') AS ?battery)\n" +
            "  }\n" +
            "}\n" +
            "ORDER BY ?battery";
    }

    public static String buildSocOptionsQuery(String battery) {
        return PREFIXES +
            "SELECT DISTINCT ?soc\n" +
            "WHERE {\n" +
            "  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\n" +
            "    ?b a :Battery ; :has_soc_test ?s .\n" +
            "    ?s :soc_value ?soc .\n" +
            optionalBatteryFilter(battery) +
            "  }\n" +
            "}\n" +
            "ORDER BY ?soc";
    }

    public static String buildEisDatasetQuery(String battery, Double soc) {
        return PREFIXES +
            "SELECT ?battery ?soc ?f ?re ?im\n" +
            "       (COALESCE(?mag,  bif:sqrt(?re * ?re + ?im * ?im)) AS ?zMag)\n" +
            "       (COALESCE(?phi,  bif:atan2(?im, ?re))            AS ?zPhase)\n" +
            "WHERE {\n" +
            "  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\n" +
            "    ?b a :Battery ; :has_soc_test ?s .\n" +
            "    ?s :soc_value ?soc ; ^:part_of_soc_test ?m .\n" +
            "    ?m a :EIS_Measurement ;\n" +
            "       :frequency ?f ;\n" +
            "       :impedance_real ?re ;\n" +
            "       :impedance_imag ?im .\n" +
            "    OPTIONAL { ?m :impedance_magnitude ?mag }\n" +
            "    OPTIONAL { ?m :impedance_phase ?phi }\n" +
            "    BIND(REPLACE(STR(?b), '^.*(#|/)', '') AS ?battery)\n" +
                 optionalBatteryFilter(battery) +
                 optionalSocFilter(soc) +
            "  }\n" +
            "}\n" +
            "ORDER BY ?battery ?soc ?f";
    }

    private static String optionalBatteryFilter(String battery) {
        if (battery == null || battery.isBlank()) {
            return "";
        }
        return "    FILTER(REPLACE(STR(?b), '^.*(#|/)', '') = '" + escapeLiteral(battery.trim()) + "')\n";
    }

    private static String optionalSocFilter(Double soc) {
        if (soc == null) {
            return "";
        }
        return "    FILTER(?soc = " + soc + ")\n";
    }

    private static String escapeLiteral(String value) {
        return value.replace("\\", "\\\\").replace("'", "\\'");
    }
}
