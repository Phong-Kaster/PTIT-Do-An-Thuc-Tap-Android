package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * this is the field content when GET /orders/ - Client
 * elementally, it has the following as:
 * "id": 32,
 * "product_id": 2,
 * "product_name": "Laptop Lenovo IdeaPad Slim 5 15ITL05 i5 1135G7/8GB",
 * "product_price": 14999000,
 * "product_avatar": "D:\\xampp\\htdocs\\PTIT-Do-An-Thuc-Tap/assets/uploads/default.png",
 * "quantity": 1,
 * "price": 14999000
 */
public class GetLatestOrderResponseContent {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("product_id")
    @Expose
    private int productId;

    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("product_price")
    @Expose
    private int productPrice;

    @SerializedName("product_avatar")
    @Expose
    private String productAvatar;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("price")
    @Expose
    private int price;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductAvatar() {
        return productAvatar;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
