package com.erarge.opevaontology.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FilterOptionsResponse {
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private List<Double> distanceMmOptions;
    private List<Double> socPercentOptions;

    public FilterOptionsResponse() {
    }

    public FilterOptionsResponse(LocalDateTime minTime, LocalDateTime maxTime, 
                                 List<Double> distanceMmOptions, List<Double> socPercentOptions) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.distanceMmOptions = distanceMmOptions;
        this.socPercentOptions = socPercentOptions;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public void setMinTime(LocalDateTime minTime) {
        this.minTime = minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(LocalDateTime maxTime) {
        this.maxTime = maxTime;
    }

    public List<Double> getDistanceMmOptions() {
        return distanceMmOptions;
    }

    public void setDistanceMmOptions(List<Double> distanceMmOptions) {
        this.distanceMmOptions = distanceMmOptions;
    }

    public List<Double> getSocPercentOptions() {
        return socPercentOptions;
    }

    public void setSocPercentOptions(List<Double> socPercentOptions) {
        this.socPercentOptions = socPercentOptions;
    }
}

