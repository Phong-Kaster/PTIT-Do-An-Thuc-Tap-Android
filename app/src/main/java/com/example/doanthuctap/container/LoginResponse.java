package com.example.doanthuctap.container;

import com.example.doanthuctap.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("data")
    @Expose
    private User data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getData() {
        return data;
    }
}
