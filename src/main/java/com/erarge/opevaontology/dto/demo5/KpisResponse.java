// src/main/java/com/erarge/opevaontology/dto/demo5/KpisResponse.java
package com.erarge.opevaontology.dto.demo5;

public class KpisResponse {
    private long routes;
    private long nodes;
    private long customers;
    private long chargingStations;
    private long depots;

    public KpisResponse() {
    }

    public KpisResponse(long routes, long nodes, long customers, long chargingStations, long depots) {
        this.routes = routes;
        this.nodes = nodes;
        this.customers = customers;
        this.chargingStations = chargingStations;
        this.depots = depots;
        System.out.println("KpisResponse created: " + this.toString());
    }

    public long getRoutes() {
        return routes;
    }

    public void setRoutes(long routes) {
        this.routes = routes;
    }

    public long getNodes() {
        return nodes;
    }

    public void setNodes(long nodes) {
        this.nodes = nodes;
    }

    public long getCustomers() {
        return customers;
    }

    public void setCustomers(long customers) {
        this.customers = customers;
    }

    public long getChargingStations() {
        return chargingStations;
    }

    public void setChargingStations(long chargingStations) {
        this.chargingStations = chargingStations;
    }

    public long getDepots() {
        return depots;
    }

    public void setDepots(long depots) {
        this.depots = depots;
    }

    public String toString() {
        return String.format("KpisResponse{routes=%d, nodes=%d, customers=%d, chargingStations=%d, depots=%d}",
                routes, nodes, customers, chargingStations, depots);
    }
}