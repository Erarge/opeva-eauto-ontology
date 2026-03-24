package com.erarge.opevaontology.dto.demo2;

public class Demo2PowerPointDTO {
    private String time;
    private double voltage;
    private double current;
    private double powerW;

    public Demo2PowerPointDTO() {
    }

    public Demo2PowerPointDTO(String time, double voltage, double current, double powerW) {
        this.time = time;
        this.voltage = voltage;
        this.current = current;
        this.powerW = powerW;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getPowerW() {
        return powerW;
    }

    public void setPowerW(double powerW) {
        this.powerW = powerW;
    }
}