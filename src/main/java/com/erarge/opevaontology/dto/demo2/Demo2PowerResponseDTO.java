package com.erarge.opevaontology.dto.demo2;

import java.util.List;

public class Demo2PowerResponseDTO {
    private Demo2PowerSummaryDTO summary;
    private List<Demo2PowerPointDTO> points;

    public Demo2PowerResponseDTO() {
    }

    public Demo2PowerResponseDTO(Demo2PowerSummaryDTO summary, List<Demo2PowerPointDTO> points) {
        this.summary = summary;
        this.points = points;
    }

    public Demo2PowerSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(Demo2PowerSummaryDTO summary) {
        this.summary = summary;
    }

    public List<Demo2PowerPointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<Demo2PowerPointDTO> points) {
        this.points = points;
    }
}