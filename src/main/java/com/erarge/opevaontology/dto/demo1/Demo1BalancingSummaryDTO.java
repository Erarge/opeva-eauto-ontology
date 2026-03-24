package com.erarge.opevaontology.dto.demo1;

public class Demo1BalancingSummaryDTO {
    private long binCount;
    private Integer minVdiffBin;
    private Integer maxVdiffBin;
    private Double minAvgBalTimeSec;
    private Double maxAvgBalTimeSec;
    private Double overallAverageBalTimeSec;

    public Demo1BalancingSummaryDTO() {
    }

    public Demo1BalancingSummaryDTO(
            long binCount,
            Integer minVdiffBin,
            Integer maxVdiffBin,
            Double minAvgBalTimeSec,
            Double maxAvgBalTimeSec,
            Double overallAverageBalTimeSec) {
        this.binCount = binCount;
        this.minVdiffBin = minVdiffBin;
        this.maxVdiffBin = maxVdiffBin;
        this.minAvgBalTimeSec = minAvgBalTimeSec;
        this.maxAvgBalTimeSec = maxAvgBalTimeSec;
        this.overallAverageBalTimeSec = overallAverageBalTimeSec;
    }

    public long getBinCount() {
        return binCount;
    }

    public void setBinCount(long binCount) {
        this.binCount = binCount;
    }

    public Integer getMinVdiffBin() {
        return minVdiffBin;
    }

    public void setMinVdiffBin(Integer minVdiffBin) {
        this.minVdiffBin = minVdiffBin;
    }

    public Integer getMaxVdiffBin() {
        return maxVdiffBin;
    }

    public void setMaxVdiffBin(Integer maxVdiffBin) {
        this.maxVdiffBin = maxVdiffBin;
    }

    public Double getMinAvgBalTimeSec() {
        return minAvgBalTimeSec;
    }

    public void setMinAvgBalTimeSec(Double minAvgBalTimeSec) {
        this.minAvgBalTimeSec = minAvgBalTimeSec;
    }

    public Double getMaxAvgBalTimeSec() {
        return maxAvgBalTimeSec;
    }

    public void setMaxAvgBalTimeSec(Double maxAvgBalTimeSec) {
        this.maxAvgBalTimeSec = maxAvgBalTimeSec;
    }

    public Double getOverallAverageBalTimeSec() {
        return overallAverageBalTimeSec;
    }

    public void setOverallAverageBalTimeSec(Double overallAverageBalTimeSec) {
        this.overallAverageBalTimeSec = overallAverageBalTimeSec;
    }
}
