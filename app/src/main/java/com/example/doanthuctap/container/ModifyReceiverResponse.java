package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * {
 *     "result": 1,
 *     "msg": "Order's information have been updated successfully !",
 *     "data": {
 *         "id": "94298d1e-20c1-4d",
 *         "user_id": 1,
 *         "receiver_phone": "0977777777",
 *         "receiver_address": "TP Hải Phòng, Việt Nam",
 *         "receiver_name": "Nguyễn Thành Phong",
 *         "description": "Chào chiến thắng !",
 *         "status": "processing",
 *         "total": 16999000,
 *         "create_at": "2022-07-24 02:22:58",
 *         "update_at": "2022-07-28 15:56:36"
 *     }
 * }
 */
public class ModifyReceiverResponse {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private Order data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Order getData() {
        return data;
    }
}
