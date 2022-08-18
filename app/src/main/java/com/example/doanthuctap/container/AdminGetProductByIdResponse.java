package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Photo;
import com.example.doanthuctap.model.ProductClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminGetProductByIdResponse {

    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private ProductClient data;

    @SerializedName("photos")
    @Expose
    private List<Photo> photos;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public ProductClient getData() {
        return data;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
