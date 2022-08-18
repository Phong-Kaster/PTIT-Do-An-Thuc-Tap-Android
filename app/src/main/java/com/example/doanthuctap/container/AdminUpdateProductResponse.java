package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Order;
import com.example.doanthuctap.model.ProductClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminUpdateProductResponse {
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private ProductClient data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public ProductClient getData() {
        return data;
    }
}
