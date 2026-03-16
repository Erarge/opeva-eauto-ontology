package com.erarge.opevaontology.dto.demo2;

import java.util.List;

public class Demo2HeatmapResponseDTO {
    private Demo2SummaryDTO summary;
    private List<Demo2HeatmapPointDTO> points;

    public Demo2HeatmapResponseDTO() {}

    public Demo2HeatmapResponseDTO(Demo2SummaryDTO summary, List<Demo2HeatmapPointDTO> points) {
        this.summary = summary;
        this.points = points;
    }

    public Demo2SummaryDTO getSummary() { return summary; }
    public void setSummary(Demo2SummaryDTO summary) { this.summary = summary; }
    public List<Demo2HeatmapPointDTO> getPoints() { return points; }
    public void setPoints(List<Demo2HeatmapPointDTO> points) { this.points = points; }
}
