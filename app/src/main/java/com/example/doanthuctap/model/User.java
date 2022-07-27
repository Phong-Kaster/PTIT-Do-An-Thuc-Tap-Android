package com.example.doanthuctap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("first_name")
    @Expose
    private String firstName;


    @SerializedName("last_name")
    @Expose
    private String lastName;


    @SerializedName("phone")
    @Expose
    private String phone;


    @SerializedName("address")
    @Expose
    private String address;


    @SerializedName("role")
    @Expose
    private String role;


    @SerializedName("active")
    @Expose
    private int active;


    @SerializedName("create_at")
    @Expose
    private String createAt;


    @SerializedName("update_at")
    @Expose
    private String updateAt;

    /**********************************/
    /*************CONSTRUCTOR**********/
    /**********************************/
    public User() {
    }


    /**********************************/
    /*************GETTER***************/
    /**********************************/
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public int getActive() {
        return active;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
