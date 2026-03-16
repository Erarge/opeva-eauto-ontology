package com.erarge.opevaontology.dto.demo2;

public class Demo2SummaryDTO {
    private long batteryCount;
    private long pointCount;
    private double minVoltage;
    private double maxVoltage;
    private double minCurrent;
    private double maxCurrent;
    private double minEfficiency;
    private double maxEfficiency;
    private double averageEfficiency;
    private long totalSamples;

    public Demo2SummaryDTO() {}

    public Demo2SummaryDTO(long batteryCount, long pointCount, double minVoltage, double maxVoltage, double minCurrent,
            double maxCurrent, double minEfficiency, double maxEfficiency, double averageEfficiency, long totalSamples) {
        this.batteryCount = batteryCount;
        this.pointCount = pointCount;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.minCurrent = minCurrent;
        this.maxCurrent = maxCurrent;
        this.minEfficiency = minEfficiency;
        this.maxEfficiency = maxEfficiency;
        this.averageEfficiency = averageEfficiency;
        this.totalSamples = totalSamples;
    }

    public long getBatteryCount() { return batteryCount; }
    public void setBatteryCount(long batteryCount) { this.batteryCount = batteryCount; }
    public long getPointCount() { return pointCount; }
    public void setPointCount(long pointCount) { this.pointCount = pointCount; }
    public double getMinVoltage() { return minVoltage; }
    public void setMinVoltage(double minVoltage) { this.minVoltage = minVoltage; }
    public double getMaxVoltage() { return maxVoltage; }
    public void setMaxVoltage(double maxVoltage) { this.maxVoltage = maxVoltage; }
    public double getMinCurrent() { return minCurrent; }
    public void setMinCurrent(double minCurrent) { this.minCurrent = minCurrent; }
    public double getMaxCurrent() { return maxCurrent; }
    public void setMaxCurrent(double maxCurrent) { this.maxCurrent = maxCurrent; }
    public double getMinEfficiency() { return minEfficiency; }
    public void setMinEfficiency(double minEfficiency) { this.minEfficiency = minEfficiency; }
    public double getMaxEfficiency() { return maxEfficiency; }
    public void setMaxEfficiency(double maxEfficiency) { this.maxEfficiency = maxEfficiency; }
    public double getAverageEfficiency() { return averageEfficiency; }
    public void setAverageEfficiency(double averageEfficiency) { this.averageEfficiency = averageEfficiency; }
    public long getTotalSamples() { return totalSamples; }
    public void setTotalSamples(long totalSamples) { this.totalSamples = totalSamples; }
}
