package com.erarge.opevaontology.dto.demo2;

public class Demo2PowerSummaryDTO {
    private long sampleCount;
    private String startTime;
    private String endTime;
    private double minVoltage;
    private double maxVoltage;
    private double minCurrent;
    private double maxCurrent;
    private double minPowerW;
    private double maxPowerW;
    private double averagePowerW;

    public Demo2PowerSummaryDTO() {
    }

    public Demo2PowerSummaryDTO(
            long sampleCount,
            String startTime,
            String endTime,
            double minVoltage,
            double maxVoltage,
            double minCurrent,
            double maxCurrent,
            double minPowerW,
            double maxPowerW,
            double averagePowerW) {
        this.sampleCount = sampleCount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.minCurrent = minCurrent;
        this.maxCurrent = maxCurrent;
        this.minPowerW = minPowerW;
        this.maxPowerW = maxPowerW;
        this.averagePowerW = averagePowerW;
    }

    public long getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(long sampleCount) {
        this.sampleCount = sampleCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(double minVoltage) {
        this.minVoltage = minVoltage;
    }

    public double getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(double maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public double getMinCurrent() {
        return minCurrent;
    }

    public void setMinCurrent(double minCurrent) {
        this.minCurrent = minCurrent;
    }

    public double getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public double getMinPowerW() {
        return minPowerW;
    }

    public void setMinPowerW(double minPowerW) {
        this.minPowerW = minPowerW;
    }

    public double getMaxPowerW() {
        return maxPowerW;
    }

    public void setMaxPowerW(double maxPowerW) {
        this.maxPowerW = maxPowerW;
    }

    public double getAveragePowerW() {
        return averagePowerW;
    }

    public void setAveragePowerW(double averagePowerW) {
        this.averagePowerW = averagePowerW;
    }
}