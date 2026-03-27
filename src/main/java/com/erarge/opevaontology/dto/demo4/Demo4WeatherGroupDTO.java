package com.erarge.opevaontology.dto.demo4;

public class Demo4WeatherGroupDTO {
    private String weather;
    private int sessionCount;
    private double minEnergyEffKwh100km;
    private double avgEnergyEffKwh100km;
    private double maxEnergyEffKwh100km;
    private double totalDistanceM;
    private double totalEnergyKwh;

    public Demo4WeatherGroupDTO() {}

    public Demo4WeatherGroupDTO(String weather, int sessionCount,
            double minEnergyEffKwh100km, double avgEnergyEffKwh100km,
            double maxEnergyEffKwh100km, double totalDistanceM, double totalEnergyKwh) {
        this.weather = weather;
        this.sessionCount = sessionCount;
        this.minEnergyEffKwh100km = minEnergyEffKwh100km;
        this.avgEnergyEffKwh100km = avgEnergyEffKwh100km;
        this.maxEnergyEffKwh100km = maxEnergyEffKwh100km;
        this.totalDistanceM = totalDistanceM;
        this.totalEnergyKwh = totalEnergyKwh;
    }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public int getSessionCount() { return sessionCount; }
    public void setSessionCount(int v) { this.sessionCount = v; }
    public double getMinEnergyEffKwh100km() { return minEnergyEffKwh100km; }
    public void setMinEnergyEffKwh100km(double v) { this.minEnergyEffKwh100km = v; }
    public double getAvgEnergyEffKwh100km() { return avgEnergyEffKwh100km; }
    public void setAvgEnergyEffKwh100km(double v) { this.avgEnergyEffKwh100km = v; }
    public double getMaxEnergyEffKwh100km() { return maxEnergyEffKwh100km; }
    public void setMaxEnergyEffKwh100km(double v) { this.maxEnergyEffKwh100km = v; }
    public double getTotalDistanceM() { return totalDistanceM; }
    public void setTotalDistanceM(double v) { this.totalDistanceM = v; }
    public double getTotalEnergyKwh() { return totalEnergyKwh; }
    public void setTotalEnergyKwh(double v) { this.totalEnergyKwh = v; }
}
