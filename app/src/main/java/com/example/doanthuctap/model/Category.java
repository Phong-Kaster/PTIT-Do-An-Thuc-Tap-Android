package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("slug")
    @Expose
    private String slug;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }

    public int getParentId() {
        return parentId;
    }

    public String getSlug() {
        return slug;
    }
}
