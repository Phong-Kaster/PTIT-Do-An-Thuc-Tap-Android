package com.example.doanthuctap.container;

import com.example.doanthuctap.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeInformationResponse {

    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private User data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public User getData() {
        return data;
    }
}
