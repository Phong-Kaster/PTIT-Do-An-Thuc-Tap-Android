package com.example.doanthuctap.container;

import com.example.doanthuctap.model.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * {
 *     "result": 1,
 *     "product_id": "1",
 *     "data": [
 *         {
 *             "id": "12",
 *             "path": "D:\\xampp\\htdocs\\PTIT-Do-An-Thuc-Tap/assets/uploads/product_1_1657030442.png",
 *             "is_avatar": "1"
 *         },
 *         {
 *             "id": "13",
 *             "path": "D:\\xampp\\htdocs\\PTIT-Do-An-Thuc-Tap/assets/uploads/product_1_1657037632.png",
 *             "is_avatar": "0"
 *         },
 *         {
 *             "id": "17",
 *             "path": "D:\\xampp\\htdocs\\PTIT-Do-An-Thuc-Tap/assets/uploads/product_1_1657037730.png",
 *             "is_avatar": "0"
 *         }
 *     ]
 * }
 */
public class PhotosResponse {

    @SerializedName("result")
    @Expose
    private int result;


    @SerializedName("product_id")
    @Expose
    private int productId;

    @SerializedName("data")
    @Expose
    private List<Photo> data;

    public int getResult() {
        return result;
    }

    public int getProductId() {
        return productId;
    }

    public List<Photo> getData() {
        return data;
    }
}
