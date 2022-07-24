package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {
    @SerializedName("result")
    @Expose
    private int result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("data")
    @Expose
    private List<Category> data = null;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Category> getData() {
        return data;
    }
}
