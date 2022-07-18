package com.example.doanthuctap.api;

import com.example.doanthuctap.container.LoginResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.container.ProfileResponse;
import com.example.doanthuctap.container.TestResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

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


    /**
     * @author Phong-Kaster
     * get AuthUser's account
     */
    @GET("profile")
    Call<ProfileResponse> profile(@Header("Authorization") String authorization);


    /**
     * get products' information from database
     */
    @GET("products")
    Call<ProductsResponse> getProducts(@QueryMap Map<String, String> parameters);

    @GET("test")
    Call<TestResponse> getTest();

}
