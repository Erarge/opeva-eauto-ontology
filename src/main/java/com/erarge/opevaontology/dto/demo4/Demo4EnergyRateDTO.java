package com.erarge.opevaontology.dto.demo4;

public class Demo4EnergyRateDTO {
    private String weather;
    private int frame;
    private double speedKmh;
    private double energyConsumedKwh;
    private double sensorPowerW;
    private double socPct;

    public Demo4EnergyRateDTO() {}

    public Demo4EnergyRateDTO(String weather, int frame, double speedKmh,
            double energyConsumedKwh, double sensorPowerW, double socPct) {
        this.weather = weather;
        this.frame = frame;
        this.speedKmh = speedKmh;
        this.energyConsumedKwh = energyConsumedKwh;
        this.sensorPowerW = sensorPowerW;
        this.socPct = socPct;
    }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public int getFrame() { return frame; }
    public void setFrame(int frame) { this.frame = frame; }
    public double getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(double speedKmh) { this.speedKmh = speedKmh; }
    public double getEnergyConsumedKwh() { return energyConsumedKwh; }
    public void setEnergyConsumedKwh(double energyConsumedKwh) { this.energyConsumedKwh = energyConsumedKwh; }
    public double getSensorPowerW() { return sensorPowerW; }
    public void setSensorPowerW(double sensorPowerW) { this.sensorPowerW = sensorPowerW; }
    public double getSocPct() { return socPct; }
    public void setSocPct(double socPct) { this.socPct = socPct; }
}