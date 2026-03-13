package com.erarge.opevaontology.dto.demo3;

import java.util.List;

public class Demo3DatasetResponse {
    private Demo3SummaryResponse summary;
    private List<Demo3EisPointDTO> points;

    public Demo3DatasetResponse() {}

    public Demo3DatasetResponse(Demo3SummaryResponse summary, List<Demo3EisPointDTO> points) {
        this.summary = summary;
        this.points = points;
    }

    public Demo3SummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(Demo3SummaryResponse summary) {
        this.summary = summary;
    }

    public List<Demo3EisPointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<Demo3EisPointDTO> points) {
        this.points = points;
    }
}
