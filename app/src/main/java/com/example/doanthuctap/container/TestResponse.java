package com.example.doanthuctap.container;

import com.example.doanthuctap.model.ProductClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Phong-Kaster
 * this is a test container to check if we map field correct or not?
 */
public class TestResponse {
    @SerializedName("result")
    @Expose
    private int result;
    @SerializedName("data")
    @Expose
    private List<ProductClient> data = null;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ProductClient> getData() {
        return data;
    }

    public void setData(List<ProductClient> data) {
        this.data = data;
    }
}
