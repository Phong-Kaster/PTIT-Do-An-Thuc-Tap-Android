package com.example.doanthuctap.container;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminDeleteOrderResponse {
    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }


}
