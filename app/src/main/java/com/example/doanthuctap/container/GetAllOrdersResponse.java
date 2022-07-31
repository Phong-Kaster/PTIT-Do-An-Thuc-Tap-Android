package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * get all orders response
 *{
 *     "result": 1,
 *     "quantity": 1,
 *     "data": [
 *         {
 *             "id": "e9c5cc56-d591-47",
 *             "user_id": 1,
 *             "receiver_phone": "0366253623",
 *             "receiver_address": "TP Hochiminh, Vietnam",
 *             "receiver_name": "phong kaster",
 *             "description": "",
 *             "status": "processing",
 *             "total": 21499000,
 *             "create_at": "2022-07-31 03:35:19",
 *             "update_at": "2022-07-31 03:35:23"
 *         }
 *     ]
 * }
 */
public class GetAllOrdersResponse {
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
