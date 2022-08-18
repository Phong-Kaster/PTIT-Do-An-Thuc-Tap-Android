package com.example.doanthuctap.container;

import com.example.doanthuctap.model.ProductClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminProductsResponse {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("quantity")
    @Expose
    private int quantity;


    @SerializedName("data")
    @Expose
    private List<ProductClient> data = null;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<ProductClient> getData() {
        return data;
    }

    public void setData(List<ProductClient> data) {
        this.data = data;
    }
}
