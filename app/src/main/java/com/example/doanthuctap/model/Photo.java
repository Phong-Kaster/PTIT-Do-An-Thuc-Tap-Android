package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("is_avatar")
    @Expose
    private int isAvatar;


    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public int getIsAvatar() {
        return isAvatar;
    }
}
