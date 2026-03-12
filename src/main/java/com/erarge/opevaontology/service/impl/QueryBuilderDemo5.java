package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo5 {

    private static final String PREFIXES =
        "PREFIX :      <https://cloud.erarge.com.tr/ontologies/opeva_demo5/>\n" +
        "PREFIX eauto: <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
        "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n";

    /** A1. Global KPIs: routes, nodes, customers, charging stations, depots */
    public static String buildKpisQuery() {
        return PREFIXES +
            "SELECT ?routes ?nodes ?customers ?chargingStations ?depots\r\n" + //
            "WHERE {\r\n" + //
                    "  { SELECT (COUNT(DISTINCT ?r) AS ?routes) WHERE { ?r a :Route . } }\r\n" + //
                                "\r\n" + //
                                "  { SELECT (COUNT(DISTINCT ?nid) AS ?nodes)\r\n" + //
                                "    WHERE { ?n a :Node ; :node_id ?nid . } }\r\n" + //
                                "\r\n" + //
                                "  { SELECT (COUNT(DISTINCT ?nid) AS ?customers)\r\n" + //
                                "    WHERE {\r\n" + //
                                "      ?n a :Node ; :node_id ?nid .\r\n" + //
                                "      # type OR node_type OR demand>0 => customer\r\n" + //
                                "      { ?n a :Delivery_Point }\r\n" + //
                                "      UNION\r\n" + //
                                "      { ?n :node_type ?t1 . FILTER(LCASE(STR(?t1)) IN (\"c\",\"customer\",\"delivery_point\")) }\r\n" + //
                                "      UNION\r\n" + //
                                "      { ?n :node_demand_amount ?d . FILTER(xsd:integer(?d) > 0) }\r\n" + //
                                "    }\r\n" + //
                                "  }\r\n" + //
                                "\r\n" + //
                                "  { SELECT (COUNT(DISTINCT ?nid) AS ?chargingStations)\r\n" + //
                                "    WHERE {\r\n" + //
                                "      ?n a :Node ; :node_id ?nid .\r\n" + //
                                "      { ?n a :Charging_Station }\r\n" + //
                                "      UNION\r\n" + //
                                "      { ?n :node_type ?t2 . FILTER(LCASE(STR(?t2)) IN (\"cs\",\"charging_station\")) }\r\n" + //
                                "    }\r\n" + //
                                "  }\r\n" + //
                                "\r\n" + //
                                "  { SELECT (COUNT(DISTINCT ?nid) AS ?depots)\r\n" + //
                                "    WHERE {\r\n" + //
                                "      ?n a :Node ; :node_id ?nid .\r\n" + //
                                "      { ?n a :Warehouse }\r\n" + //
                                "      UNION\r\n" + //
                                "      { ?n :node_type ?t3 . FILTER(LCASE(STR(?t3)) IN (\"d\",\"depot\",\"warehouse\")) }\r\n" + //
                                "    }\r\n" + //
                                "  }\r\n" + //
                                "}";
    }

    /** A2. Route mix by (size, distribution) */
    public static String buildRouteMixSizeDistributionQuery() {
        return PREFIXES +
            "SELECT ?size ?distribution (COUNT(*) AS ?routes)\n" +
            "WHERE {\n" +
            "  ?r a :Route ;\n" +
            "     :route_customer_size ?size ;\n" +
            "     :route_customer_distribution_type ?distribution .\n" +
            "}\n" +
            "GROUP BY ?size ?distribution\n" +
            "ORDER BY ?size ?distribution";
    }

    /** A3. Route mix by time window */
    public static String buildRouteMixTimeWindowQuery() {
        return PREFIXES +
            "SELECT ?tw (COUNT(*) AS ?routes)\n" +
            "WHERE {\n" +
            "  ?r a :Route ; :route_time_window ?tw .\n" +
            "}\n" +
            "GROUP BY ?tw\n" +
            "ORDER BY ?tw";
    }
}
