package com.example.doanthuctap.container;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoResponse {

    @SerializedName("result")
    @Expose
    private int result;

    @Nullable
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
