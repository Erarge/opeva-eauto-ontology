package com.erarge.opevaontology.dto.demo3;

public class Demo3SummaryResponse {
    private long batteryCount;
    private long socCount;
    private long pointCount;
    private Double minFrequency;
    private Double maxFrequency;
    private Double maxImpedanceMagnitude;
    private Double averagePhase;

    public Demo3SummaryResponse() {}

    public Demo3SummaryResponse(
        long batteryCount,
        long socCount,
        long pointCount,
        Double minFrequency,
        Double maxFrequency,
        Double maxImpedanceMagnitude,
        Double averagePhase
    ) {
        this.batteryCount = batteryCount;
        this.socCount = socCount;
        this.pointCount = pointCount;
        this.minFrequency = minFrequency;
        this.maxFrequency = maxFrequency;
        this.maxImpedanceMagnitude = maxImpedanceMagnitude;
        this.averagePhase = averagePhase;
    }

    public long getBatteryCount() { return batteryCount; }
    public void setBatteryCount(long batteryCount) { this.batteryCount = batteryCount; }
    public long getSocCount() { return socCount; }
    public void setSocCount(long socCount) { this.socCount = socCount; }
    public long getPointCount() { return pointCount; }
    public void setPointCount(long pointCount) { this.pointCount = pointCount; }
    public Double getMinFrequency() { return minFrequency; }
    public void setMinFrequency(Double minFrequency) { this.minFrequency = minFrequency; }
    public Double getMaxFrequency() { return maxFrequency; }
    public void setMaxFrequency(Double maxFrequency) { this.maxFrequency = maxFrequency; }
    public Double getMaxImpedanceMagnitude() { return maxImpedanceMagnitude; }
    public void setMaxImpedanceMagnitude(Double maxImpedanceMagnitude) { this.maxImpedanceMagnitude = maxImpedanceMagnitude; }
    public Double getAveragePhase() { return averagePhase; }
    public void setAveragePhase(Double averagePhase) { this.averagePhase = averagePhase; }
}
