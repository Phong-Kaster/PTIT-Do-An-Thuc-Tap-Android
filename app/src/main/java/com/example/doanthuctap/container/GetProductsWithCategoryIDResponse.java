package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Category;
import com.example.doanthuctap.model.ProductClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductsWithCategoryIDResponse {
    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("category")
    @Expose
    private Category category;


    @SerializedName("quantity")
    @Expose
    private int quantity;


    @SerializedName("products")
    @Expose
    private List<ProductClient> products;

    public int getResult() {
        return result;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<ProductClient> getProducts() {
        return products;
    }
}
