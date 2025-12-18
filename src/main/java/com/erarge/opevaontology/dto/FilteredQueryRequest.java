package com.erarge.opevaontology.dto;

import java.time.LocalDateTime;

public class FilteredQueryRequest {
    private LocalDateTime from;
    private LocalDateTime to;
    private Double distanceMm;
    private Double socPercent;

    public FilteredQueryRequest() {
    }

    public FilteredQueryRequest(LocalDateTime from, LocalDateTime to, Double distanceMm, Double socPercent) {
        this.from = from;
        this.to = to;
        this.distanceMm = distanceMm;
        this.socPercent = socPercent;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public Double getDistanceMm() {
        return distanceMm;
    }

    public void setDistanceMm(Double distanceMm) {
        this.distanceMm = distanceMm;
    }

    public Double getSocPercent() {
        return socPercent;
    }

    public void setSocPercent(Double socPercent) {
        this.socPercent = socPercent;
    }
}

