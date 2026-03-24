package com.erarge.opevaontology.dto.demo1;

public class Demo1BalancingPointDTO {
    private int vdiffBin;
    private double avgBalTimeSec;

    public Demo1BalancingPointDTO() {
    }

    public Demo1BalancingPointDTO(int vdiffBin, double avgBalTimeSec) {
        this.vdiffBin = vdiffBin;
        this.avgBalTimeSec = avgBalTimeSec;
    }

    public int getVdiffBin() {
        return vdiffBin;
    }

    public void setVdiffBin(int vdiffBin) {
        this.vdiffBin = vdiffBin;
    }

    public double getAvgBalTimeSec() {
        return avgBalTimeSec;
    }

    public void setAvgBalTimeSec(double avgBalTimeSec) {
        this.avgBalTimeSec = avgBalTimeSec;
    }
}
