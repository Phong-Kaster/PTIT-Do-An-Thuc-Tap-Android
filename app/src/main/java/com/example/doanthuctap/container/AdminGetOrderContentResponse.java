package com.example.doanthuctap.container;

import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminGetOrderContentResponse {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("order")
    @Expose
    private Order order;

    @SerializedName("data")
    @Expose
    private List<GetLatestOrderResponseContent> data;

    public int getResult() {
        return result;
    }

    public Order getOrder() {
        return order;
    }

    public List<GetLatestOrderResponseContent> getData() {
        return data;
    }
}
