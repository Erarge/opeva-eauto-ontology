package com.erarge.opevaontology.dto.demo5;

public class RouteListItemDTO {
    private String routeId;
    private int size;
    private String distribution; // R, C, RC
    private int tw;              // 1=Wide, 2=Medium, 3=Narrow

    public RouteListItemDTO() {}

    public RouteListItemDTO(String routeId, int size, String distribution, int tw) {
        this.routeId = routeId;
        this.size = size;
        this.distribution = distribution;
        this.tw = tw;
    }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getDistribution() { return distribution; }
    public void setDistribution(String distribution) { this.distribution = distribution; }

    public int getTw() { return tw; }
    public void setTw(int tw) { this.tw = tw; }
}
