package com.erarge.opevaontology.dto.demo4;

public class Demo4WeatherSessionDTO {
    private String weather;
    private double energyEffKwh100km;
    private double totalDistanceM;
    private double totalEnergyKwh;
    private double totalTimeS;

    public Demo4WeatherSessionDTO() {}

    public Demo4WeatherSessionDTO(String weather, double energyEffKwh100km,
            double totalDistanceM, double totalEnergyKwh, double totalTimeS) {
        this.weather = weather;
        this.energyEffKwh100km = energyEffKwh100km;
        this.totalDistanceM = totalDistanceM;
        this.totalEnergyKwh = totalEnergyKwh;
        this.totalTimeS = totalTimeS;
    }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public double getEnergyEffKwh100km() { return energyEffKwh100km; }
    public void setEnergyEffKwh100km(double v) { this.energyEffKwh100km = v; }
    public double getTotalDistanceM() { return totalDistanceM; }
    public void setTotalDistanceM(double v) { this.totalDistanceM = v; }
    public double getTotalEnergyKwh() { return totalEnergyKwh; }
    public void setTotalEnergyKwh(double v) { this.totalEnergyKwh = v; }
    public double getTotalTimeS() { return totalTimeS; }
    public void setTotalTimeS(double v) { this.totalTimeS = v; }
}
