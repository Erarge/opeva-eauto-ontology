package com.erarge.opevaontology.dto.demo4;

import java.util.List;

public class Demo4WeatherResponseDTO {
    private Demo4WeatherSummaryDTO summary;
    private List<Demo4WeatherGroupDTO> groups;
    private List<Demo4WeatherSessionDTO> sessions;

    public Demo4WeatherResponseDTO() {}

    public Demo4WeatherResponseDTO(Demo4WeatherSummaryDTO summary,
            List<Demo4WeatherGroupDTO> groups, List<Demo4WeatherSessionDTO> sessions) {
        this.summary = summary;
        this.groups = groups;
        this.sessions = sessions;
    }

    public Demo4WeatherSummaryDTO getSummary() { return summary; }
    public void setSummary(Demo4WeatherSummaryDTO v) { this.summary = v; }
    public List<Demo4WeatherGroupDTO> getGroups() { return groups; }
    public void setGroups(List<Demo4WeatherGroupDTO> v) { this.groups = v; }
    public List<Demo4WeatherSessionDTO> getSessions() { return sessions; }
    public void setSessions(List<Demo4WeatherSessionDTO> v) { this.sessions = v; }
}
