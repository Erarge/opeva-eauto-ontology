package com.erarge.opevaontology.dto.demo2;

public class Demo2HeatmapPointDTO {
    private String battery;
    private double vBinStart;
    private double vBinEnd;
    private double aBinStart;
    private double aBinEnd;
    private double vBinCenter;
    private double aBinCenter;
    private double averageEfficiency;
    private long sampleCount;

    public Demo2HeatmapPointDTO() {}

    public Demo2HeatmapPointDTO(String battery, double vBinStart, double vBinEnd, double aBinStart, double aBinEnd,
            double vBinCenter, double aBinCenter, double averageEfficiency, long sampleCount) {
        this.battery = battery;
        this.vBinStart = vBinStart;
        this.vBinEnd = vBinEnd;
        this.aBinStart = aBinStart;
        this.aBinEnd = aBinEnd;
        this.vBinCenter = vBinCenter;
        this.aBinCenter = aBinCenter;
        this.averageEfficiency = averageEfficiency;
        this.sampleCount = sampleCount;
    }

    public String getBattery() { return battery; }
    public void setBattery(String battery) { this.battery = battery; }
    public double getvBinStart() { return vBinStart; }
    public void setvBinStart(double vBinStart) { this.vBinStart = vBinStart; }
    public double getvBinEnd() { return vBinEnd; }
    public void setvBinEnd(double vBinEnd) { this.vBinEnd = vBinEnd; }
    public double getaBinStart() { return aBinStart; }
    public void setaBinStart(double aBinStart) { this.aBinStart = aBinStart; }
    public double getaBinEnd() { return aBinEnd; }
    public void setaBinEnd(double aBinEnd) { this.aBinEnd = aBinEnd; }
    public double getvBinCenter() { return vBinCenter; }
    public void setvBinCenter(double vBinCenter) { this.vBinCenter = vBinCenter; }
    public double getaBinCenter() { return aBinCenter; }
    public void setaBinCenter(double aBinCenter) { this.aBinCenter = aBinCenter; }
    public double getAverageEfficiency() { return averageEfficiency; }
    public void setAverageEfficiency(double averageEfficiency) { this.averageEfficiency = averageEfficiency; }
    public long getSampleCount() { return sampleCount; }
    public void setSampleCount(long sampleCount) { this.sampleCount = sampleCount; }
}
