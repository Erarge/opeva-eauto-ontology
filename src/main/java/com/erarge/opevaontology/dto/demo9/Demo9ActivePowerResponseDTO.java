package com.erarge.opevaontology.dto.demo9;

import java.util.List;

public class Demo9ActivePowerResponseDTO {
    private Demo9ActivePowerSummaryDTO summary;
    private List<Demo9ActivePowerPointDTO> points;

    public Demo9ActivePowerResponseDTO() {
    }

    public Demo9ActivePowerResponseDTO(Demo9ActivePowerSummaryDTO summary, List<Demo9ActivePowerPointDTO> points) {
        this.summary = summary;
        this.points = points;
    }

    public Demo9ActivePowerSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(Demo9ActivePowerSummaryDTO summary) {
        this.summary = summary;
    }

    public List<Demo9ActivePowerPointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<Demo9ActivePowerPointDTO> points) {
        this.points = points;
    }
}
