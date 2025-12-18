package com.erarge.opevaontology.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QueryBuilder {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    public static String buildTimeIntervalQuery(LocalDateTime from, LocalDateTime to) {
        String fromStr = from.format(DATE_TIME_FORMATTER);
        String toStr = to.format(DATE_TIME_FORMATTER);
        
        return "SELECT\n" +
                "  ?session\n" +
                "  ?sourceFile\n" +
                "  ?location\n" +
                "  ?timeOfDay\n" +
                "  ?socPercent\n" +
                "  ?distanceMm\n" +
                "  ?coilType\n" +
                "  ?durationMin\n" +
                "  ?charger\n" +
                "  ?tNorm\n" +
                "\n" +
                "  (MAX(IF(?prop = eauto:op_v_pri_v,     xsd:double(STR(?val)), UNDEF)) AS ?v_pri_v)\n" +
                "  (MAX(IF(?prop = eauto:op_a_pri_a,     xsd:double(STR(?val)), UNDEF)) AS ?a_pri_a)\n" +
                "  (MAX(IF(?prop = eauto:op_v_sin_v,     xsd:double(STR(?val)), UNDEF)) AS ?v_sin_v)\n" +
                "  (MAX(IF(?prop = eauto:op_a_sin_a,     xsd:double(STR(?val)), UNDEF)) AS ?a_sin_a)\n" +
                "  (MAX(IF(?prop = eauto:op_v_sout_v,    xsd:double(STR(?val)), UNDEF)) AS ?v_sout_v)\n" +
                "  (MAX(IF(?prop = eauto:op_a_sout_a,    xsd:double(STR(?val)), UNDEF)) AS ?a_sout_a)\n" +
                "  (MAX(IF(?prop = eauto:op_t_coil_c,    xsd:double(STR(?val)), UNDEF)) AS ?t_coil_c)\n" +
                "  (MAX(IF(?prop = eauto:op_t_ambiant_c, xsd:double(STR(?val)), UNDEF)) AS ?t_ambiant_c)\n" +
                "  (MAX(IF(?prop = eauto:op_p_pri,       xsd:double(STR(?val)), UNDEF)) AS ?p_pri)\n" +
                "  (MAX(IF(?prop = eauto:op_p_sin,       xsd:double(STR(?val)), UNDEF)) AS ?p_sin)\n" +
                "  (MAX(IF(?prop = eauto:op_efficiency,  xsd:double(STR(?val)), UNDEF)) AS ?efficiency)\n" +
                "\n" +
                "WHERE {\n" +
                "  BIND(\"" + fromStr + "\"^^xsd:dateTime AS ?from)\n" +
                "  BIND(\"" + toStr + "\"^^xsd:dateTime AS ?to)\n" +
                "\n" +
                "  BIND(\"\" AS ?p_sourceFile)\n" +
                "  BIND(\"\" AS ?p_location)\n" +
                "  BIND(\"\" AS ?p_timeOfDay)\n" +
                "  BIND(-1.0 AS ?p_socPercent)\n" +
                "  BIND(-1.0 AS ?p_distanceMm)\n" +
                "  BIND(\"\" AS ?p_coilType)\n" +
                "  BIND(-1.0 AS ?p_durationMin)\n" +
                "  BIND(\"\" AS ?p_chargerIriStr)\n" +
                "\n" +
                "  ?session eauto:wpt_sourceFile  ?sourceFile ;\n" +
                "           eauto:wpt_location    ?location ;\n" +
                "           eauto:wpt_timeOfDay   ?timeOfDay ;\n" +
                "           eauto:wpt_socPercent  ?socPercent ;\n" +
                "           eauto:wpt_distanceMm  ?distanceMm ;\n" +
                "           eauto:wpt_coilType    ?coilType ;\n" +
                "           eauto:wpt_durationMin ?durationMin ;\n" +
                "           eauto:usesCharger     ?charger .\n" +
                "\n" +
                "  FILTER( ?p_sourceFile = \"\" || STR(?sourceFile) = ?p_sourceFile )\n" +
                "  FILTER( ?p_location   = \"\" || STR(?location)   = ?p_location )\n" +
                "  FILTER( ?p_timeOfDay  = \"\" || STR(?timeOfDay)  = ?p_timeOfDay )\n" +
                "  FILTER( ?p_coilType   = \"\" || STR(?coilType)   = ?p_coilType )\n" +
                "  FILTER( ?p_socPercent   < 0 || xsd:double(?socPercent)   = ?p_socPercent )\n" +
                "  FILTER( ?p_distanceMm   < 0 || xsd:double(?distanceMm)   = ?p_distanceMm )\n" +
                "  FILTER( ?p_durationMin  < 0 || xsd:double(?durationMin)  = ?p_durationMin )\n" +
                "  FILTER( ?p_chargerIriStr = \"\" || STR(?charger) = ?p_chargerIriStr )\n" +
                "\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:hasFeatureOfInterest ?session ;\n" +
                "       sosa:resultTime ?time ;\n" +
                "       sosa:observedProperty ?prop ;\n" +
                "       sosa:hasSimpleResult ?val .\n" +
                "\n" +
                "  BIND( xsd:dateTime(REPLACE(STR(?time), \" \", \"T\")) AS ?tNorm )\n" +
                "\n" +
                "  FILTER( ?tNorm >= ?from && ?tNorm <= ?to )\n" +
                "\n" +
                "  FILTER (?prop IN (\n" +
                "    eauto:op_v_pri_v, eauto:op_a_pri_a,\n" +
                "    eauto:op_v_sin_v, eauto:op_a_sin_a,\n" +
                "    eauto:op_v_sout_v, eauto:op_a_sout_a,\n" +
                "    eauto:op_t_coil_c, eauto:op_t_ambiant_c,\n" +
                "    eauto:op_p_pri, eauto:op_p_sin, eauto:op_efficiency\n" +
                "  ))\n" +
                "}\n" +
                "GROUP BY\n" +
                "  ?session ?sourceFile ?location ?timeOfDay ?socPercent ?distanceMm ?coilType ?durationMin ?charger ?tNorm\n" +
                "ORDER BY ?session ?tNorm";
    }
    
    public static String buildFilteredQuery(LocalDateTime from, LocalDateTime to, Double distanceMm, Double socPercent) {
        String fromStr = from.format(DATE_TIME_FORMATTER);
        String toStr = to.format(DATE_TIME_FORMATTER);
        
        String distanceFilter = distanceMm != null ? distanceMm.toString() : "-1.0";
        String socFilter = socPercent != null ? socPercent.toString() : "-1.0";
        
        return "SELECT\n" +
                "  ?session\n" +
                "  ?sourceFile\n" +
                "  ?location\n" +
                "  ?timeOfDay\n" +
                "  ?socPercent\n" +
                "  ?distanceMm\n" +
                "  ?coilType\n" +
                "  ?durationMin\n" +
                "  ?charger\n" +
                "  ?tNorm\n" +
                "\n" +
                "  (MAX(IF(?prop = eauto:op_v_pri_v,     xsd:double(STR(?val)), UNDEF)) AS ?v_pri_v)\n" +
                "  (MAX(IF(?prop = eauto:op_a_pri_a,     xsd:double(STR(?val)), UNDEF)) AS ?a_pri_a)\n" +
                "  (MAX(IF(?prop = eauto:op_v_sin_v,     xsd:double(STR(?val)), UNDEF)) AS ?v_sin_v)\n" +
                "  (MAX(IF(?prop = eauto:op_a_sin_a,     xsd:double(STR(?val)), UNDEF)) AS ?a_sin_a)\n" +
                "  (MAX(IF(?prop = eauto:op_v_sout_v,    xsd:double(STR(?val)), UNDEF)) AS ?v_sout_v)\n" +
                "  (MAX(IF(?prop = eauto:op_a_sout_a,    xsd:double(STR(?val)), UNDEF)) AS ?a_sout_a)\n" +
                "  (MAX(IF(?prop = eauto:op_t_coil_c,    xsd:double(STR(?val)), UNDEF)) AS ?t_coil_c)\n" +
                "  (MAX(IF(?prop = eauto:op_t_ambiant_c, xsd:double(STR(?val)), UNDEF)) AS ?t_ambiant_c)\n" +
                "  (MAX(IF(?prop = eauto:op_p_pri,       xsd:double(STR(?val)), UNDEF)) AS ?p_pri)\n" +
                "  (MAX(IF(?prop = eauto:op_p_sin,       xsd:double(STR(?val)), UNDEF)) AS ?p_sin)\n" +
                "  (MAX(IF(?prop = eauto:op_efficiency,  xsd:double(STR(?val)), UNDEF)) AS ?efficiency)\n" +
                "\n" +
                "WHERE {\n" +
                "  BIND(\"" + fromStr + "\"^^xsd:dateTime AS ?from)\n" +
                "  BIND(\"" + toStr + "\"^^xsd:dateTime AS ?to)\n" +
                "\n" +
                "  BIND(\"\" AS ?p_sourceFile)\n" +
                "  BIND(\"\" AS ?p_location)\n" +
                "  BIND(\"\" AS ?p_timeOfDay)\n" +
                "  BIND(" + socFilter + " AS ?p_socPercent)\n" +
                "  BIND(" + distanceFilter + " AS ?p_distanceMm)\n" +
                "  BIND(\"\" AS ?p_coilType)\n" +
                "  BIND(-1.0 AS ?p_durationMin)\n" +
                "  BIND(\"\" AS ?p_chargerIriStr)\n" +
                "\n" +
                "  ?session eauto:wpt_sourceFile  ?sourceFile ;\n" +
                "           eauto:wpt_location    ?location ;\n" +
                "           eauto:wpt_timeOfDay   ?timeOfDay ;\n" +
                "           eauto:wpt_socPercent  ?socPercent ;\n" +
                "           eauto:wpt_distanceMm  ?distanceMm ;\n" +
                "           eauto:wpt_coilType    ?coilType ;\n" +
                "           eauto:wpt_durationMin ?durationMin ;\n" +
                "           eauto:usesCharger     ?charger .\n" +
                "\n" +
                "  FILTER( ?p_sourceFile = \"\" || STR(?sourceFile) = ?p_sourceFile )\n" +
                "  FILTER( ?p_location   = \"\" || STR(?location)   = ?p_location )\n" +
                "  FILTER( ?p_timeOfDay  = \"\" || STR(?timeOfDay)  = ?p_timeOfDay )\n" +
                "  FILTER( ?p_coilType   = \"\" || STR(?coilType)   = ?p_coilType )\n" +
                "  FILTER( ?p_socPercent   < 0 || xsd:double(?socPercent)   = ?p_socPercent )\n" +
                "  FILTER( ?p_distanceMm   < 0 || xsd:double(?distanceMm)   = ?p_distanceMm )\n" +
                "  FILTER( ?p_durationMin  < 0 || xsd:double(?durationMin)  = ?p_durationMin )\n" +
                "  FILTER( ?p_chargerIriStr = \"\" || STR(?charger) = ?p_chargerIriStr )\n" +
                "\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:hasFeatureOfInterest ?session ;\n" +
                "       sosa:resultTime ?time ;\n" +
                "       sosa:observedProperty ?prop ;\n" +
                "       sosa:hasSimpleResult ?val .\n" +
                "\n" +
                "  BIND( xsd:dateTime(REPLACE(STR(?time), \" \", \"T\")) AS ?tNorm )\n" +
                "\n" +
                "  FILTER( ?tNorm >= ?from && ?tNorm <= ?to )\n" +
                "\n" +
                "  FILTER (?prop IN (\n" +
                "    eauto:op_v_pri_v, eauto:op_a_pri_a,\n" +
                "    eauto:op_v_sin_v, eauto:op_a_sin_a,\n" +
                "    eauto:op_v_sout_v, eauto:op_a_sout_a,\n" +
                "    eauto:op_t_coil_c, eauto:op_t_ambiant_c,\n" +
                "    eauto:op_p_pri, eauto:op_p_sin, eauto:op_efficiency\n" +
                "  ))\n" +
                "}\n" +
                "GROUP BY\n" +
                "  ?session ?sourceFile ?location ?timeOfDay ?socPercent ?distanceMm ?coilType ?durationMin ?charger ?tNorm\n" +
                "ORDER BY ?session ?tNorm";
    }
    
    public static String buildTimeRangeQuery() {
        return "SELECT (MIN(?tNorm) AS ?minTime) (MAX(?tNorm) AS ?maxTime)\n" +
                "WHERE {\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:resultTime ?time .\n" +
                "\n" +
                "  BIND(xsd:dateTime(REPLACE(STR(?time), \" \", \"T\")) AS ?tNorm)\n" +
                "}";
    }
    
    public static String buildDistanceOptionsQuery() {
        return "SELECT DISTINCT ?distanceMm\n" +
                "WHERE {\n" +
                "  ?session a eauto:ChargingSession ;\n" +
                "           eauto:wpt_distanceMm ?distanceMm .\n" +
                "}\n" +
                "ORDER BY ?distanceMm";
    }
    
    public static String buildSocOptionsQuery() {
        return "SELECT DISTINCT ?socPercent\n" +
                "WHERE {\n" +
                "  ?session a eauto:ChargingSession ;\n" +
                "           eauto:wpt_socPercent ?socPercent .\n" +
                "}\n" +
                "ORDER BY ?socPercent";
    }
}

