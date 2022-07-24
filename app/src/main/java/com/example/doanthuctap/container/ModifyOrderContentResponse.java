package com.example.doanthuctap.container;

import com.example.doanthuctap.model.ModifyOrderContent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyOrderContentResponse {
    @SerializedName("result")
    @Expose
    private int result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("update_at")
    @Expose
    private String updateAt;

    @SerializedName("data")
    @Expose
    private ModifyOrderContent data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public int getTotal() {
        return total;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public ModifyOrderContent getData() {
        return data;
    }
}
