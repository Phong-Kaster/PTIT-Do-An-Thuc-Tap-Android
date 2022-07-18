package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductClient {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("remaining")
    @Expose
    private int remaining;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("screen_size")
    @Expose
    private double screenSize;
    @SerializedName("cpu")
    @Expose
    private String cpu;
    @SerializedName("ram")
    @Expose
    private String ram;
    @SerializedName("graphic_card")
    @Expose
    private String graphicCard;
    @SerializedName("rom")
    @Expose
    private String rom;
    @SerializedName("demand")
    @Expose
    private String demand;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRemaining() {
        return remaining;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getGraphicCard() {
        return graphicCard;
    }

    public String getRom() {
        return rom;
    }

    public String getDemand() {
        return demand;
    }

    public String getContent() {
        return content;
    }

    public String getAvatar() {
        return avatar;
    }
}
