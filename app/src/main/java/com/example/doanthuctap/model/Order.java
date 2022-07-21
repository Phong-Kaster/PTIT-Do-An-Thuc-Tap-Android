package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("receiver_phone")
    @Expose
    private Object receiverPhone;

    @SerializedName("receiver_address")
    @Expose
    private Object receiverAddress;

    @SerializedName("receiver_name")
    @Expose
    private String receiverName;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("update_at")
    @Expose
    private String updateAt;

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Object getReceiverPhone() {
        return receiverPhone;
    }

    public Object getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }
}
