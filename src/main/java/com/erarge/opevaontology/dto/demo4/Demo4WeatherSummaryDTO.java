package com.erarge.opevaontology.dto.demo4;

public class Demo4WeatherSummaryDTO {
    private int totalSessions;
    private int weatherConditionCount;
    private double overallMinEffKwh100km;
    private double overallAvgEffKwh100km;
    private double overallMaxEffKwh100km;
    private double totalDistanceKm;
    private double totalEnergyKwh;

    public Demo4WeatherSummaryDTO() {}

    public Demo4WeatherSummaryDTO(int totalSessions, int weatherConditionCount,
            double overallMinEffKwh100km, double overallAvgEffKwh100km,
            double overallMaxEffKwh100km, double totalDistanceKm, double totalEnergyKwh) {
        this.totalSessions = totalSessions;
        this.weatherConditionCount = weatherConditionCount;
        this.overallMinEffKwh100km = overallMinEffKwh100km;
        this.overallAvgEffKwh100km = overallAvgEffKwh100km;
        this.overallMaxEffKwh100km = overallMaxEffKwh100km;
        this.totalDistanceKm = totalDistanceKm;
        this.totalEnergyKwh = totalEnergyKwh;
    }

    public int getTotalSessions() { return totalSessions; }
    public void setTotalSessions(int v) { this.totalSessions = v; }
    public int getWeatherConditionCount() { return weatherConditionCount; }
    public void setWeatherConditionCount(int v) { this.weatherConditionCount = v; }
    public double getOverallMinEffKwh100km() { return overallMinEffKwh100km; }
    public void setOverallMinEffKwh100km(double v) { this.overallMinEffKwh100km = v; }
    public double getOverallAvgEffKwh100km() { return overallAvgEffKwh100km; }
    public void setOverallAvgEffKwh100km(double v) { this.overallAvgEffKwh100km = v; }
    public double getOverallMaxEffKwh100km() { return overallMaxEffKwh100km; }
    public void setOverallMaxEffKwh100km(double v) { this.overallMaxEffKwh100km = v; }
    public double getTotalDistanceKm() { return totalDistanceKm; }
    public void setTotalDistanceKm(double v) { this.totalDistanceKm = v; }
    public double getTotalEnergyKwh() { return totalEnergyKwh; }
    public void setTotalEnergyKwh(double v) { this.totalEnergyKwh = v; }
}
