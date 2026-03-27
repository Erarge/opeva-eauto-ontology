package com.erarge.opevaontology.dto.demo9;

public class Demo9ActivePowerPointDTO {
    private Long timestamp;
    private double activePowerW;
    private boolean shiftable;

    public Demo9ActivePowerPointDTO() {
    }

    public Demo9ActivePowerPointDTO(Long timestamp, double activePowerW, boolean shiftable) {
        this.timestamp = timestamp;
        this.activePowerW = activePowerW;
        this.shiftable = shiftable;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public double getActivePowerW() {
        return activePowerW;
    }

    public void setActivePowerW(double activePowerW) {
        this.activePowerW = activePowerW;
    }

    public boolean isShiftable() {
        return shiftable;
    }

    public void setShiftable(boolean shiftable) {
        this.shiftable = shiftable;
    }
}
