package com.example.doanthuctap.container;

import com.example.doanthuctap.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("data")
    @Expose
    private User data;

    public int getResult() {
        return result;
    }

    public User getData() {
        return data;
    }
}
