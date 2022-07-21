package com.example.doanthuctap.container;

import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Get latest order | Auth User
 * GET {{ENDPOINT_URL}}/orders/
 */
public class GetLatestOrderResponse {
    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private Order data;


    @SerializedName("content")
    @Expose
    private List<GetLatestOrderResponseContent> content;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Order getData() {
        return data;
    }

    public List<GetLatestOrderResponseContent> getContent() {
        return content;
    }
}
