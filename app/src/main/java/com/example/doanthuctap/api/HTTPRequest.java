package com.example.doanthuctap.api;

import com.example.doanthuctap.container.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HTTPRequest {

    /**
     * Attention: we should utilize String data type, even parameters are passed to
     * function in API maybe as Integer, Double, Long, .. and so forth
     *
     * Using String data type helps us to reduce errors when Retrofit return result equals oh but
     * doesn't return error message
     * */

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String username, @Field("password") String password);
}
