package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminCreateOrder {
    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private Order data;


    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Order getData() {
        return data;
    }
}
