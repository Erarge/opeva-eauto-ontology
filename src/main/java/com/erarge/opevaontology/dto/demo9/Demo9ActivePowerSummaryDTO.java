package com.erarge.opevaontology.dto.demo9;

public class Demo9ActivePowerSummaryDTO {
    private long sampleCount;
    private Long startTimestamp;
    private Long endTimestamp;
    private double minActivePowerW;
    private double maxActivePowerW;
    private double averageActivePowerW;
    private long shiftableSampleCount;
    private long nonShiftableSampleCount;

    public Demo9ActivePowerSummaryDTO() {
    }

    public Demo9ActivePowerSummaryDTO(
            long sampleCount,
            Long startTimestamp,
            Long endTimestamp,
            double minActivePowerW,
            double maxActivePowerW,
            double averageActivePowerW,
            long shiftableSampleCount,
            long nonShiftableSampleCount) {
        this.sampleCount = sampleCount;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.minActivePowerW = minActivePowerW;
        this.maxActivePowerW = maxActivePowerW;
        this.averageActivePowerW = averageActivePowerW;
        this.shiftableSampleCount = shiftableSampleCount;
        this.nonShiftableSampleCount = nonShiftableSampleCount;
    }

    public long getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(long sampleCount) {
        this.sampleCount = sampleCount;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public double getMinActivePowerW() {
        return minActivePowerW;
    }

    public void setMinActivePowerW(double minActivePowerW) {
        this.minActivePowerW = minActivePowerW;
    }

    public double getMaxActivePowerW() {
        return maxActivePowerW;
    }

    public void setMaxActivePowerW(double maxActivePowerW) {
        this.maxActivePowerW = maxActivePowerW;
    }

    public double getAverageActivePowerW() {
        return averageActivePowerW;
    }

    public void setAverageActivePowerW(double averageActivePowerW) {
        this.averageActivePowerW = averageActivePowerW;
    }

    public long getShiftableSampleCount() {
        return shiftableSampleCount;
    }

    public void setShiftableSampleCount(long shiftableSampleCount) {
        this.shiftableSampleCount = shiftableSampleCount;
    }

    public long getNonShiftableSampleCount() {
        return nonShiftableSampleCount;
    }

    public void setNonShiftableSampleCount(long nonShiftableSampleCount) {
        this.nonShiftableSampleCount = nonShiftableSampleCount;
    }
}
