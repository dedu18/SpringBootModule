package com.dedu.zuulmodule.enums;

public enum FilterType {
    PRE("pre"),
    ROUTING("routing"),
    POST("post"),
    ERROR("error");


    private String value;

    FilterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
