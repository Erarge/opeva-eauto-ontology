package com.erarge.opevaontology.dto.demo3;

public class Demo3EisPointDTO {
    private String battery;
    private double soc;
    private double frequency;
    private double impedanceReal;
    private double impedanceImag;
    private double impedanceMagnitude;
    private double impedancePhase;

    public Demo3EisPointDTO() {
    }

    public Demo3EisPointDTO(
            String battery,
            double soc,
            double frequency,
            double impedanceReal,
            double impedanceImag,
            double impedanceMagnitude,
            double impedancePhase) {
        this.battery = battery;
        this.soc = soc;
        this.frequency = frequency;
        this.impedanceReal = impedanceReal;
        this.impedanceImag = impedanceImag;
        this.impedanceMagnitude = impedanceMagnitude;
        this.impedancePhase = impedancePhase;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public double getSoc() {
        return soc;
    }

    public void setSoc(double soc) {
        this.soc = soc;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getImpedanceReal() {
        return impedanceReal;
    }

    public void setImpedanceReal(double impedanceReal) {
        this.impedanceReal = impedanceReal;
    }

    public double getImpedanceImag() {
        return impedanceImag;
    }

    public void setImpedanceImag(double impedanceImag) {
        this.impedanceImag = impedanceImag;
    }

    public double getImpedanceMagnitude() {
        return impedanceMagnitude;
    }

    public void setImpedanceMagnitude(double impedanceMagnitude) {
        this.impedanceMagnitude = impedanceMagnitude;
    }

    public double getImpedancePhase() {
        return impedancePhase;
    }

    public void setImpedancePhase(double impedancePhase) {
        this.impedancePhase = impedancePhase;
    }
}
