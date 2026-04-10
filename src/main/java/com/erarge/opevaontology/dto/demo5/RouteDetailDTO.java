package com.erarge.opevaontology.dto.demo5;

import java.util.List;

public class RouteDetailDTO {
    private String routeId;
    private int size;
    private String distribution;
    private int tw;

    // Smart SPARQL-aggregated stats
    private long totalDemand;
    private long totalServiceTimeSec;
    private int numCustomers;
    private int numChargingStations;
    private double avgTimeWindowWidth; // avg (latest - earliest) for customers
    private int tightestWindow;        // min (latest - earliest) among customers
    private int widestWindow;          // max (latest - earliest) among customers

    private List<RouteNodeDTO> nodes;

    public RouteDetailDTO() {}

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getDistribution() { return distribution; }
    public void setDistribution(String distribution) { this.distribution = distribution; }

    public int getTw() { return tw; }
    public void setTw(int tw) { this.tw = tw; }

    public long getTotalDemand() { return totalDemand; }
    public void setTotalDemand(long totalDemand) { this.totalDemand = totalDemand; }

    public long getTotalServiceTimeSec() { return totalServiceTimeSec; }
    public void setTotalServiceTimeSec(long totalServiceTimeSec) { this.totalServiceTimeSec = totalServiceTimeSec; }

    public int getNumCustomers() { return numCustomers; }
    public void setNumCustomers(int numCustomers) { this.numCustomers = numCustomers; }

    public int getNumChargingStations() { return numChargingStations; }
    public void setNumChargingStations(int numChargingStations) { this.numChargingStations = numChargingStations; }

    public double getAvgTimeWindowWidth() { return avgTimeWindowWidth; }
    public void setAvgTimeWindowWidth(double avgTimeWindowWidth) { this.avgTimeWindowWidth = avgTimeWindowWidth; }

    public int getTightestWindow() { return tightestWindow; }
    public void setTightestWindow(int tightestWindow) { this.tightestWindow = tightestWindow; }

    public int getWidestWindow() { return widestWindow; }
    public void setWidestWindow(int widestWindow) { this.widestWindow = widestWindow; }

    public List<RouteNodeDTO> getNodes() { return nodes; }
    public void setNodes(List<RouteNodeDTO> nodes) { this.nodes = nodes; }
}
