package com.example.doanthuctap.api;

import com.example.doanthuctap.container.AuthWithGoogleResponse;
import com.example.doanthuctap.container.CategoriesResponse;
import com.example.doanthuctap.container.ConfirmOrderResponse;
import com.example.doanthuctap.container.GetAllOrdersResponse;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.GetOrderByIdResponse;
import com.example.doanthuctap.container.GetProductsWithCategoryIDResponse;
import com.example.doanthuctap.container.LoginResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ModifyReceiverResponse;
import com.example.doanthuctap.container.ProductByIdResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.container.ProfileResponse;
import com.example.doanthuctap.container.SignUpResponse;
import com.example.doanthuctap.container.TestResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @FormUrlEncoded
    @POST("login/google")
    Call<AuthWithGoogleResponse> authWithGoogleAccount(@Field("id_token") String idToken);


    @FormUrlEncoded
    @POST("signup")
    Call<SignUpResponse> signup(@Field("email") String email,
                                @Field("password") String password,
                                @Field("password-confirm") String passwordConfirm,
                                @Field("first_name") String firstName,
                                @Field("last_name") String lastName);


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

    @GET("products/{id}")
    Call<ProductByIdResponse> getProductById(@Path("id") String id);

    @GET("test")
    Call<TestResponse> getTest();

    /*-------------------------------------------------------------------*/
    /*--------------------------- ORDER ---------------------------------*/
    /*-------------------------------------------------------------------*/
    /**
     * get latest order with headers
     */
    @GET("latest-order/")
    Call<GetLatestOrderResponse> getLatestOrder(@HeaderMap Map<String, String> headers);

    /**
     * modify order content
     */
    @FormUrlEncoded
    @POST("order/{id}")
    Call<ModifyOrderContentResponse> modifyOrderContent(@Path("id") String orderId,
                                                        @Field("product_id") String productId,
                                                        @Field("quantity") String quantity,
                                                        @Field("action") String action);

    /**
     * modify receiver information
     * @param id is the order's id
     * @return ModifyReceiverResponse
     */
    @FormUrlEncoded
    @POST("order/{id}")
    Call<ModifyReceiverResponse> modifyOrderInformation(@Path("id") String id,
                                                        @Field("receiver_phone") String receiverPhone,
                                                        @Field("receiver_address") String receiverAddress,
                                                        @Field("receiver_name") String receiverName,
                                                        @Field("description") String description,
                                                        @Field("total") String total,
                                                        @Field("action") String action);


    /**
     * get all categories and their information
     */
    @GET("categories")
    Call<CategoriesResponse> getCategories();


    /**
     *
     * @param id is the cateogory's id
     * @return GetProductsWithCategoryIDResponse
     */
    @GET("categories/{id}")
    Call<GetProductsWithCategoryIDResponse> getProductsWithCategoryID(@Path("id") int id);




    /**
     * get all orders of a user
     * @return get all orders response
     */
    @GET("orders")
    Call<GetAllOrdersResponse> getAllOrders(@HeaderMap Map<String, String> headers,
                                            @QueryMap Map<String, String> parameters);

    @FormUrlEncoded
    @PUT("order/{id}")
    Call<ConfirmOrderResponse> confirmOrder(@Path("id") String id,
                                            @Field("status") String status);


    @GET("order/{id}")
    Call<GetOrderByIdResponse> getOrderByID(@Path("id") String id,
                                            @HeaderMap Map<String, String> headers);
}
