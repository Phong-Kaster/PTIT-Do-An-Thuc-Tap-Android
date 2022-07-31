package com.example.doanthuctap.model;

public class OrderStatus {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OrderStatus() {
    }

    public OrderStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
