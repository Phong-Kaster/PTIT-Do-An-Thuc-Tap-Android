package com.example.doanthuctap.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HTTPService {

    public static final String APP_PATH = "http://10.0.2.2:8080/PTIT-Do-An-Thuc-Tap/";


    private static Retrofit retrofit;

    public static Retrofit getInstance()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(APP_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
