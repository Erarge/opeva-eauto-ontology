package com.erarge.opevaontology.dto.demo3;

import java.util.List;

public class Demo3FilterOptionsResponse {
    private List<String> batteries;
    private List<Double> socValues;

    public Demo3FilterOptionsResponse() {}

    public Demo3FilterOptionsResponse(List<String> batteries, List<Double> socValues) {
        this.batteries = batteries;
        this.socValues = socValues;
    }

    public List<String> getBatteries() {
        return batteries;
    }

    public void setBatteries(List<String> batteries) {
        this.batteries = batteries;
    }

    public List<Double> getSocValues() {
        return socValues;
    }

    public void setSocValues(List<Double> socValues) {
        this.socValues = socValues;
    }
}
