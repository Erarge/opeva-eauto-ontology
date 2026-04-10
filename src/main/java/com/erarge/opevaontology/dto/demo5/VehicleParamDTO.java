package com.erarge.opevaontology.dto.demo5;

public class VehicleParamDTO {
    private String key;   // property local name, e.g. "vehicle_avg_velocity"
    private String value; // literal value as string

    public VehicleParamDTO() {}

    public VehicleParamDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey()   { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue()   { return value; }
    public void setValue(String value) { this.value = value; }
}
