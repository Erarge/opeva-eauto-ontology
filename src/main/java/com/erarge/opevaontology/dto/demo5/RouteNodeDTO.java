package com.erarge.opevaontology.dto.demo5;

public class RouteNodeDTO {
    private String nodeId;
    private String nodeType; // "d" = depot, "c" = customer, "cs" = charging station
    private double lat;
    private double lon;
    private int demand;
    private int serviceTime;
    private int earliest;
    private int latest;

    public RouteNodeDTO() {}

    public RouteNodeDTO(String nodeId, String nodeType, double lat, double lon,
                        int demand, int serviceTime, int earliest, int latest) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.lat = lat;
        this.lon = lon;
        this.demand = demand;
        this.serviceTime = serviceTime;
        this.earliest = earliest;
        this.latest = latest;
    }

    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }

    public String getNodeType() { return nodeType; }
    public void setNodeType(String nodeType) { this.nodeType = nodeType; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }

    public int getDemand() { return demand; }
    public void setDemand(int demand) { this.demand = demand; }

    public int getServiceTime() { return serviceTime; }
    public void setServiceTime(int serviceTime) { this.serviceTime = serviceTime; }

    public int getEarliest() { return earliest; }
    public void setEarliest(int earliest) { this.earliest = earliest; }

    public int getLatest() { return latest; }
    public void setLatest(int latest) { this.latest = latest; }
}
