package com.erarge.opevaontology.dto.demo5;

public class RouteTimeWindowDTO {
    private int tw;     // 1,2,3
    private long routes;

    public RouteTimeWindowDTO() {}
    public RouteTimeWindowDTO(int tw, long routes) {
        this.tw = tw;
        this.routes = routes;
    }
    public int getTw() { return tw; }
    public void setTw(int tw) { this.tw = tw; }
    public long getRoutes() { return routes; }
    public void setRoutes(long routes) { this.routes = routes; }
}
