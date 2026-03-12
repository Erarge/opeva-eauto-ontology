package com.erarge.opevaontology.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;
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
