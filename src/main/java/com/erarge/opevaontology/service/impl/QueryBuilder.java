package com.erarge.opevaontology.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QueryBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // --- Common prefixes (avoid repeating in every method if you want) ---
    private static final String PREFIXES = "PREFIX eauto: <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
            "PREFIX sosa:  <http://www.w3.org/ns/sosa/>\n" +
            "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n\n";

    /**
     * Wide-row query: sessions + metadata + pivoted sensor values per (session,
     * tNorm).
     * Optimized via VALUES(?prop) and less unnecessary joins.
     */
    public static String buildTimeIntervalQuery(LocalDateTime from, LocalDateTime to) {
        String fromStr = from.format(DATE_TIME_FORMATTER);
        String toStr = to.format(DATE_TIME_FORMATTER);

        return PREFIXES +
                "SELECT\n" +
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
                "  # --- Session metadata (join once per session) ---\n" +
                "  ?session a eauto:ChargingSession ;\n" +
                "           eauto:wpt_sourceFile  ?sourceFile ;\n" +
                "           eauto:wpt_location    ?location ;\n" +
                "           eauto:wpt_timeOfDay   ?timeOfDay ;\n" +
                "           eauto:wpt_socPercent  ?socPercent ;\n" +
                "           eauto:wpt_distanceMm  ?distanceMm ;\n" +
                "           eauto:wpt_coilType    ?coilType ;\n" +
                "           eauto:wpt_durationMin ?durationMin ;\n" +
                "           eauto:usesCharger     ?charger .\n" +
                "\n" +
                "  # --- Observations in time window, restricted to necessary properties ---\n" +
                "  VALUES ?prop {\n" +
                "    eauto:op_v_pri_v eauto:op_a_pri_a\n" +
                "    eauto:op_v_sin_v eauto:op_a_sin_a\n" +
                "    eauto:op_v_sout_v eauto:op_a_sout_a\n" +
                "    eauto:op_t_coil_c eauto:op_t_ambiant_c\n" +
                "    eauto:op_p_pri eauto:op_p_sin eauto:op_efficiency\n" +
                "  }\n" +
                "\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:hasFeatureOfInterest ?session ;\n" +
                "       sosa:resultTime ?time ;\n" +
                "       sosa:observedProperty ?prop ;\n" +
                "       sosa:hasSimpleResult ?val .\n" +
                "\n" +
                "  # Normalize time: handle \"YYYY-MM-DD HH:mm:ss\" and ISO \"...Z\"\n" +
                "  BIND(STR(?time) AS ?tStr0)\n" +
                "  BIND(REPLACE(?tStr0, \" \", \"T\") AS ?tStr1)\n" +
                "  BIND(IF(CONTAINS(?tStr1, \"Z\"), SUBSTR(?tStr1, 1, 19), SUBSTR(?tStr1, 1, 19)) AS ?tStr19)\n" +
                "  BIND(xsd:dateTime(?tStr19) AS ?tNorm)\n" +
                "\n" +
                "  FILTER(?tNorm >= ?from && ?tNorm <= ?to)\n" +
                "}\n" +
                "GROUP BY\n" +
                "  ?session ?sourceFile ?location ?timeOfDay ?socPercent ?distanceMm ?coilType ?durationMin ?charger ?tNorm\n"
                +
                "ORDER BY ?session ?tNorm\n";
        // Optional pagination:
        // + "LIMIT 5000 OFFSET 0";
    }

    /**
     * Same as time interval but adds numeric filters for distanceMm and socPercent.
     */
    public static String buildFilteredQuery(LocalDateTime from, LocalDateTime to, Double distanceMm,
            Double socPercent) {
        String fromStr = from.format(DATE_TIME_FORMATTER);
        String toStr = to.format(DATE_TIME_FORMATTER);

        String distanceFilter = (distanceMm != null) ? distanceMm.toString() : null;
        String socFilter = (socPercent != null) ? socPercent.toString() : null;

        StringBuilder sb = new StringBuilder();
        sb.append(PREFIXES);

        sb.append("SELECT\n")
                .append("  ?session\n")
                .append("  ?sourceFile\n")
                .append("  ?location\n")
                .append("  ?timeOfDay\n")
                .append("  ?socPercent\n")
                .append("  ?distanceMm\n")
                .append("  ?coilType\n")
                .append("  ?durationMin\n")
                .append("  ?charger\n")
                .append("  ?tNorm\n")
                .append("\n")
                .append("  (MAX(IF(?prop = eauto:op_v_pri_v,     xsd:double(STR(?val)), UNDEF)) AS ?v_pri_v)\n")
                .append("  (MAX(IF(?prop = eauto:op_a_pri_a,     xsd:double(STR(?val)), UNDEF)) AS ?a_pri_a)\n")
                .append("  (MAX(IF(?prop = eauto:op_v_sin_v,     xsd:double(STR(?val)), UNDEF)) AS ?v_sin_v)\n")
                .append("  (MAX(IF(?prop = eauto:op_a_sin_a,     xsd:double(STR(?val)), UNDEF)) AS ?a_sin_a)\n")
                .append("  (MAX(IF(?prop = eauto:op_v_sout_v,    xsd:double(STR(?val)), UNDEF)) AS ?v_sout_v)\n")
                .append("  (MAX(IF(?prop = eauto:op_a_sout_a,    xsd:double(STR(?val)), UNDEF)) AS ?a_sout_a)\n")
                .append("  (MAX(IF(?prop = eauto:op_t_coil_c,    xsd:double(STR(?val)), UNDEF)) AS ?t_coil_c)\n")
                .append("  (MAX(IF(?prop = eauto:op_t_ambiant_c, xsd:double(STR(?val)), UNDEF)) AS ?t_ambiant_c)\n")
                .append("  (MAX(IF(?prop = eauto:op_p_pri,       xsd:double(STR(?val)), UNDEF)) AS ?p_pri)\n")
                .append("  (MAX(IF(?prop = eauto:op_p_sin,       xsd:double(STR(?val)), UNDEF)) AS ?p_sin)\n")
                .append("  (MAX(IF(?prop = eauto:op_efficiency,  xsd:double(STR(?val)), UNDEF)) AS ?efficiency)\n")
                .append("\n")
                .append("WHERE {\n")
                .append("  BIND(\"").append(fromStr).append("\"^^xsd:dateTime AS ?from)\n")
                .append("  BIND(\"").append(toStr).append("\"^^xsd:dateTime AS ?to)\n")
                .append("\n")
                .append("  ?session a eauto:ChargingSession ;\n")
                .append("           eauto:wpt_sourceFile  ?sourceFile ;\n")
                .append("           eauto:wpt_location    ?location ;\n")
                .append("           eauto:wpt_timeOfDay   ?timeOfDay ;\n")
                .append("           eauto:wpt_socPercent  ?socPercent ;\n")
                .append("           eauto:wpt_distanceMm  ?distanceMm ;\n")
                .append("           eauto:wpt_coilType    ?coilType ;\n")
                .append("           eauto:wpt_durationMin ?durationMin ;\n")
                .append("           eauto:usesCharger     ?charger .\n");

        // Only add filters if user provided them (avoid extra FILTER ops)
        if (socFilter != null) {
            sb.append("  FILTER(xsd:double(?socPercent) = ").append(socFilter).append(")\n");
        }
        if (distanceFilter != null) {
            sb.append("  FILTER(xsd:double(?distanceMm) = ").append(distanceFilter).append(")\n");
        }

        sb.append("\n")
                .append("  VALUES ?prop {\n")
                .append("    eauto:op_v_pri_v eauto:op_a_pri_a\n")
                .append("    eauto:op_v_sin_v eauto:op_a_sin_a\n")
                .append("    eauto:op_v_sout_v eauto:op_a_sout_a\n")
                .append("    eauto:op_t_coil_c eauto:op_t_ambiant_c\n")
                .append("    eauto:op_p_pri eauto:op_p_sin eauto:op_efficiency\n")
                .append("  }\n")
                .append("\n")
                .append("  ?obs a sosa:Observation ;\n")
                .append("       sosa:hasFeatureOfInterest ?session ;\n")
                .append("       sosa:resultTime ?time ;\n")
                .append("       sosa:observedProperty ?prop ;\n")
                .append("       sosa:hasSimpleResult ?val .\n")
                .append("\n")
                .append("  BIND(STR(?time) AS ?tStr0)\n")
                .append("  BIND(REPLACE(?tStr0, \" \", \"T\") AS ?tStr1)\n")
                .append("  BIND(IF(CONTAINS(?tStr1, \"Z\"), SUBSTR(?tStr1, 1, 19), SUBSTR(?tStr1, 1, 19)) AS ?tStr19)\n")
                .append("  BIND(xsd:dateTime(?tStr19) AS ?tNorm)\n")
                .append("\n")
                .append("  FILTER(?tNorm >= ?from && ?tNorm <= ?to)\n")
                .append("}\n")
                .append("GROUP BY\n")
                .append("  ?session ?sourceFile ?location ?timeOfDay ?socPercent ?distanceMm ?coilType ?durationMin ?charger ?tNorm\n")
                .append("ORDER BY ?session ?tNorm\n");

        return sb.toString();
    }

    /**
     * Time range query: optimized by avoiding extra patterns.
     * Note: if your stored times are already xsd:dateTime with Z, this keeps only
     * first 19 chars.
     */
    public static String buildTimeRangeQuery() {
        return PREFIXES +
                "SELECT (MIN(?tNorm) AS ?minTime) (MAX(?tNorm) AS ?maxTime)\n" +
                "WHERE {\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:resultTime ?time .\n" +
                "  BIND(STR(?time) AS ?tStr0)\n" +
                "  BIND(REPLACE(?tStr0, \" \", \"T\") AS ?tStr1)\n" +
                "  BIND(SUBSTR(?tStr1, 1, 19) AS ?tStr19)\n" +
                "  BIND(xsd:dateTime(?tStr19) AS ?tNorm)\n" +
                "}";
    }

    /**
     * Distance options: keep DISTINCT (needed for UI), but avoid ORDER BY if you
     * don't need sorted.
     * If you want sorted in UI, keep ORDER BY here or sort client-side.
     */
    public static String buildDistanceOptionsQuery() {
        return PREFIXES +
                "SELECT DISTINCT ?distanceMm\n" +
                "WHERE {\n" +
                "  ?session a eauto:ChargingSession ;\n" +
                "           eauto:wpt_distanceMm ?distanceMm .\n" +
                "}\n" +
                "ORDER BY xsd:double(?distanceMm)";
    }

    /**
     * SOC options: same logic as distance.
     */
    public static String buildSocOptionsQuery() {
        return PREFIXES +
                "SELECT DISTINCT ?socPercent\n" +
                "WHERE {\n" +
                "  ?session a eauto:ChargingSession ;\n" +
                "           eauto:wpt_socPercent ?socPercent .\n" +
                "}\n" +
                "ORDER BY xsd:double(?socPercent)";
    }

    /**
     * OPTIONAL: if you decide to follow the "split queries" approach,
     * this returns session metadata only (fast, small).
     */
    public static String buildSessionMetadataQuery(LocalDateTime from, LocalDateTime to) {
        String fromStr = from.format(DATE_TIME_FORMATTER);
        String toStr = to.format(DATE_TIME_FORMATTER);

        return PREFIXES +
                "SELECT DISTINCT ?session ?sourceFile ?location ?timeOfDay ?socPercent ?distanceMm ?coilType ?durationMin ?charger\n"
                +
                "WHERE {\n" +
                "  BIND(\"" + fromStr + "\"^^xsd:dateTime AS ?from)\n" +
                "  BIND(\"" + toStr + "\"^^xsd:dateTime AS ?to)\n" +
                "\n" +
                "  ?session a eauto:ChargingSession ;\n" +
                "           eauto:wpt_sourceFile  ?sourceFile ;\n" +
                "           eauto:wpt_location    ?location ;\n" +
                "           eauto:wpt_timeOfDay   ?timeOfDay ;\n" +
                "           eauto:wpt_socPercent  ?socPercent ;\n" +
                "           eauto:wpt_distanceMm  ?distanceMm ;\n" +
                "           eauto:wpt_coilType    ?coilType ;\n" +
                "           eauto:wpt_durationMin ?durationMin ;\n" +
                "           eauto:usesCharger     ?charger .\n" +
                "\n" +
                "  # only sessions that have observations in the window\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:hasFeatureOfInterest ?session ;\n" +
                "       sosa:resultTime ?time .\n" +
                "\n" +
                "  BIND(STR(?time) AS ?tStr0)\n" +
                "  BIND(REPLACE(?tStr0, \" \", \"T\") AS ?tStr1)\n" +
                "  BIND(SUBSTR(?tStr1, 1, 19) AS ?tStr19)\n" +
                "  BIND(xsd:dateTime(?tStr19) AS ?tNorm)\n" +
                "  FILTER(?tNorm >= ?from && ?tNorm <= ?to)\n" +
                "}\n";
    }

    /**
     * OPTIONAL: narrow readings (often fastest); pivot in Java instead of SPARQL.
     */
    public static String buildNarrowReadingsQuery(LocalDateTime from, LocalDateTime to) {
        String fromStr = from.format(DATE_TIME_FORMATTER);
        String toStr = to.format(DATE_TIME_FORMATTER);

        return PREFIXES +
                "SELECT ?session ?tNorm ?prop ?val\n" +
                "WHERE {\n" +
                "  BIND(\"" + fromStr + "\"^^xsd:dateTime AS ?from)\n" +
                "  BIND(\"" + toStr + "\"^^xsd:dateTime AS ?to)\n" +
                "\n" +
                "  VALUES ?prop {\n" +
                "    eauto:op_v_pri_v eauto:op_a_pri_a\n" +
                "    eauto:op_v_sin_v eauto:op_a_sin_a\n" +
                "    eauto:op_v_sout_v eauto:op_a_sout_a\n" +
                "    eauto:op_t_coil_c eauto:op_t_ambiant_c\n" +
                "    eauto:op_p_pri eauto:op_p_sin eauto:op_efficiency\n" +
                "  }\n" +
                "\n" +
                "  ?obs a sosa:Observation ;\n" +
                "       sosa:hasFeatureOfInterest ?session ;\n" +
                "       sosa:resultTime ?time ;\n" +
                "       sosa:observedProperty ?prop ;\n" +
                "       sosa:hasSimpleResult ?val .\n" +
                "\n" +
                "  BIND(STR(?time) AS ?tStr0)\n" +
                "  BIND(REPLACE(?tStr0, \" \", \"T\") AS ?tStr1)\n" +
                "  BIND(SUBSTR(?tStr1, 1, 19) AS ?tStr19)\n" +
                "  BIND(xsd:dateTime(?tStr19) AS ?tNorm)\n" +
                "\n" +
                "  FILTER(?tNorm >= ?from && ?tNorm <= ?to)\n" +
                "}\n" +
                "ORDER BY ?session ?tNorm\n";
    }

    public static String buildFunctionalSafetyQuery() {
        return "PREFIX : <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
                "PREFIX d: <https://cloud.erarge.com.tr/data/opeva/demo7#>\n" +
                "SELECT ?battery ?obs ?max_temp ?min_voltage ?soc\n" +
                "WHERE {\n" +
                "  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\n" +
                "    ?battery :hasObservation ?obs .\n" +
                "    ?obs :bms_max_cell_temperature ?max_temp ;\n" +
                "         :bms_min_cell_voltage ?min_voltage ;\n" +
                "         :battery_state_of_charge ?soc .\n" +
                "    \n" +
                "    # Define safety thresholds (e.g., Temperature > 45C and Voltage < 2.5V)\n" +
                "    FILTER (?max_temp > 45.0 || ?min_voltage < 2.5)\n" +
                "  }\n" +
                "}\n" +
                "ORDER BY DESC(?max_temp)\n" +
                "LIMIT 50";
    }
}
