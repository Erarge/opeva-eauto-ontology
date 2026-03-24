package com.erarge.opevaontology.dto.demo5;

public class RouteSizeDistributionDTO {
    private int size;
    private String distribution; // R, C, RC
    private long routes;

    public RouteSizeDistributionDTO() {
    }

    public RouteSizeDistributionDTO(int size, String distribution, long routes) {
        this.size = size;
        this.distribution = distribution;
        this.routes = routes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public long getRoutes() {
        return routes;
    }

    public void setRoutes(long routes) {
        this.routes = routes;
    }
}