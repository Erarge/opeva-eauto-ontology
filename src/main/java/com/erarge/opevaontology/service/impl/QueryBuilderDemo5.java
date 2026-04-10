package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo5 {

    private static final String PREFIXES =
        "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
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

    /** B1. List all routes with metadata (for route selector dropdown) */
    public static String buildRouteListQuery() {
        return PREFIXES +
            "SELECT ?routeId ?size ?distribution ?tw\n" +
            "WHERE {\n" +
            "  ?r a :Route ;\n" +
            "     :route_customer_size ?size ;\n" +
            "     :route_customer_distribution_type ?distribution ;\n" +
            "     :route_time_window ?tw .\n" +
            "  BIND(STRAFTER(STR(?r), \"https://cloud.erarge.com.tr/ontologies/opeva_demo5/\") AS ?routeId)\n" +
            "}\n" +
            "ORDER BY ?distribution ?size ?tw";
    }

    /** B2. All nodes for a specific route (lat/lon + properties) */
    public static String buildRouteNodesQuery(String routeId) {
        return PREFIXES +
            "SELECT ?nodeId ?nodeType ?lat ?lon ?demand ?serviceTime ?earliest ?latest\n" +
            "WHERE {\n" +
            "  :" + routeId + " :has_node ?n .\n" +
            "  ?n :node_id ?nodeId ;\n" +
            "     :node_type ?nodeType ;\n" +
            "     :node_latitude ?lat ;\n" +
            "     :node_longitude ?lon ;\n" +
            "     :node_demand_amount ?demand ;\n" +
            "     :node_service_time ?serviceTime ;\n" +
            "     :node_earliest_arrival_time ?earliest ;\n" +
            "     :node_latest_arrival_time ?latest .\n" +
            "}\n" +
            "ORDER BY ?nodeType ?nodeId";
    }

    /**
     * B3. Smart SPARQL aggregation for a route:
     *   - total demand, total service time
     *   - customer count, charging station count
     *   - avg/min/max time window width among customers
     */
    public static String buildRouteSummaryQuery(String routeId) {
        return PREFIXES +
            "SELECT\n" +
            "  (SUM(?demand) AS ?totalDemand)\n" +
            "  (SUM(?svcTime) AS ?totalServiceTimeSec)\n" +
            "  (COUNT(DISTINCT ?custNode) AS ?numCustomers)\n" +
            "  (COUNT(DISTINCT ?csNode) AS ?numChargingStations)\n" +
            "  (AVG(?winWidth) AS ?avgTimeWindowWidth)\n" +
            "  (MIN(?winWidth) AS ?tightestWindow)\n" +
            "  (MAX(?winWidth) AS ?widestWindow)\n" +
            "WHERE {\n" +
            "  :" + routeId + " :has_node ?n .\n" +
            "  ?n :node_demand_amount ?demand ;\n" +
            "     :node_service_time ?svcTime ;\n" +
            "     :node_earliest_arrival_time ?earliest ;\n" +
            "     :node_latest_arrival_time ?latest .\n" +
            "  BIND(xsd:integer(?latest) - xsd:integer(?earliest) AS ?winWidth)\n" +
            "  OPTIONAL {\n" +
            "    ?n :node_type ?t .\n" +
            "    FILTER(STR(?t) = \"c\")\n" +
            "    BIND(?n AS ?custNode)\n" +
            "  }\n" +
            "  OPTIONAL {\n" +
            "    ?n :node_type ?tcs .\n" +
            "    FILTER(STR(?tcs) = \"cs\")\n" +
            "    BIND(?n AS ?csNode)\n" +
            "  }\n" +
            "}";
    }

    /**
     * C1. All literal-valued properties of :EV_DEFAULT (excludes rdf:type).
     * Returns (?prop URI, ?val literal) rows.
     */
    public static String buildVehicleParamsQuery() {
        return PREFIXES +
            "SELECT ?prop ?val\n" +
            "WHERE {\n" +
            "  :EV_DEFAULT ?prop ?val .\n" +
            "  FILTER(?prop != rdf:type)\n" +
            "  FILTER(!isURI(?val))\n" +
            "}";
    }
}
