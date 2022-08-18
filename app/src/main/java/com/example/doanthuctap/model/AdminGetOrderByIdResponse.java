package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * admin - get order by id
 */
public class AdminGetOrderByIdResponse {
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
