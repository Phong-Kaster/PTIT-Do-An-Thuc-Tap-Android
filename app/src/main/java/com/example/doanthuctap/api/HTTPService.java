package com.example.doanthuctap.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HTTPService {

    /*Use this APP_PATH if testing device is the Android emulator*/
    //public static final String APP_PATH = "http://10.0.2.2:8080/PTIT-Do-An-Thuc-Tap/";

    /*Use this APP_PATH if tesing device is a real hardware device*/
    public static final String APP_PATH = "http://192.168.1.2:8080/PTIT-Do-An-Thuc-Tap/";

    private static Retrofit retrofit;

    public static Retrofit getInstance()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(APP_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
