package com.erarge.opevaontology.dto.demo1;

import java.util.List;

public class Demo1BalancingResponseDTO {
    private Demo1BalancingSummaryDTO summary;
    private List<Demo1BalancingPointDTO> points;

    public Demo1BalancingResponseDTO() {
    }

    public Demo1BalancingResponseDTO(Demo1BalancingSummaryDTO summary, List<Demo1BalancingPointDTO> points) {
        this.summary = summary;
        this.points = points;
    }

    public Demo1BalancingSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(Demo1BalancingSummaryDTO summary) {
        this.summary = summary;
    }

    public List<Demo1BalancingPointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<Demo1BalancingPointDTO> points) {
        this.points = points;
    }
}
