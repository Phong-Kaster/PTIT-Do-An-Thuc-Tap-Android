package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminGetAllOrdersResponse {

    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("quantity")
    @Expose
    private int quantity;


    @SerializedName("data")
    @Expose
    private List<Order> data;

    public int getResult() {
        return result;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Order> getData() {
        return data;
    }
}
