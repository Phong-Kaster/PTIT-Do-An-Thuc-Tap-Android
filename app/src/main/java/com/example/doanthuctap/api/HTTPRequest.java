package com.example.doanthuctap.api;

import com.example.doanthuctap.container.AdminCreateOrder;
import com.example.doanthuctap.container.AdminDeleteOrderResponse;
import com.example.doanthuctap.container.AdminGetAllOrdersResponse;
import com.example.doanthuctap.container.AdminGetOrderContentResponse;
import com.example.doanthuctap.container.AdminGetProductByIdResponse;
import com.example.doanthuctap.container.AdminModifyOrderContentResponse;
import com.example.doanthuctap.container.AdminModifyOrderResponse;
import com.example.doanthuctap.container.AdminProductsResponse;
import com.example.doanthuctap.container.AdminUpdateProductResponse;
import com.example.doanthuctap.container.AuthWithGoogleResponse;
import com.example.doanthuctap.container.CategoriesResponse;
import com.example.doanthuctap.container.ChangeInformationResponse;
import com.example.doanthuctap.container.ConfirmOrderResponse;
import com.example.doanthuctap.container.GetAllOrdersResponse;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.GetOrderByIdResponse;
import com.example.doanthuctap.container.GetProductsWithCategoryIDResponse;
import com.example.doanthuctap.container.LoginResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ModifyReceiverResponse;
import com.example.doanthuctap.container.PhotoResponse;
import com.example.doanthuctap.container.PhotosResponse;
import com.example.doanthuctap.container.ProductByIdResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.container.ProfileResponse;
import com.example.doanthuctap.container.SignUpResponse;
import com.example.doanthuctap.container.TestResponse;
import com.example.doanthuctap.model.AdminGetOrderByIdResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
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

    /*-------------------------------------------------------------------*/
    /*--------------------------- AUTHENTICATION ------------------------*/
    /*-------------------------------------------------------------------*/
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


    @FormUrlEncoded
    @POST("profile")
    Call<ChangeInformationResponse> changeInformation(@HeaderMap Map<String, String> headers,
                                                      @Field("email") String email,
                                                      @Field("first_name") String firstName,
                                                      @Field("last_name") String lastName,
                                                      @Field("phone") String phone,
                                                      @Field("address") String address);

    /**
     * @author Phong-Kaster
     * get AuthUser's account
     */
    @GET("profile")
    Call<ProfileResponse> profile(@Header("Authorization") String authorization);

    /*-------------------------------------------------------------------*/
    /*--------------------------- PRODUCTS ------------------------------*/
    /*-------------------------------------------------------------------*/

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
    Call<ModifyOrderContentResponse> modifyOrderContent( @HeaderMap Map<String, String> headers,
                                                        @Path("id") String orderId,
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
    Call<ModifyReceiverResponse> modifyOrderInformation(@HeaderMap Map<String, String> headers,
                                                        @Path("id") String id,
                                                        @Field("receiver_phone") String receiverPhone,
                                                        @Field("receiver_address") String receiverAddress,
                                                        @Field("receiver_name") String receiverName,
                                                        @Field("description") String description,
                                                        @Field("total") String total,
                                                        @Field("action") String action);

    /**
     * get all orders of a user
     * @return get all orders response
     */
    @GET("orders")
    Call<GetAllOrdersResponse> getAllOrders(@HeaderMap Map<String, String> headers,
                                            @QueryMap Map<String, String> parameters);

    @FormUrlEncoded
    @PUT("order/{id}")
    Call<ConfirmOrderResponse> confirmOrder(@HeaderMap Map<String, String> headers,
                                            @Path("id") String id,
                                            @Field("status") String status);


    @GET("order/{id}")
    Call<GetOrderByIdResponse> getOrderByID(@Path("id") String id,
                                            @HeaderMap Map<String, String> headers);

    /*-------------------------------------------------------------------*/
    /*--------------------------- CATEGORY ------------------------------*/
    /*-------------------------------------------------------------------*/

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



    /*-------------------------------------------------------------------*/
    /*--------------------------- ADMIN ORDER----------------------------*/
    /*-------------------------------------------------------------------*/
    @GET("admin/orders")
    Call<AdminGetAllOrdersResponse> adminGetAllOrders(@HeaderMap Map<String, String> headers,
                                                      @QueryMap Map<String, String> parameters);

    @DELETE("admin/orders/{id}")
    Call<AdminDeleteOrderResponse> adminDeleteOrder(@HeaderMap Map<String, String> headers,
                                                    @Path("id") String orderId);

    @GET("admin/orders/{id}")
    Call<AdminGetOrderByIdResponse> adminGetOrderByID(@HeaderMap Map<String, String> headers,
                                                      @Path("id") String orderId);

    @FormUrlEncoded
    @PUT("admin/orders/{id}")
    Call<AdminModifyOrderResponse> adminModifyOrder(@HeaderMap Map<String, String> headers,
                                                    @Path("id") String orderId,
                                                    @Field("receiver_phone") String receiverPhone,
                                                    @Field("receiver_address") String receiverAddress,
                                                    @Field("receiver_name") String receiverName,
                                                    @Field("status") String status,
                                                    @Field("description") String description);

    @FormUrlEncoded
    @POST("admin/orders")
    Call<AdminCreateOrder> adminCreateOrder(@HeaderMap Map<String, String> headers,
                                            @Field("receiver_phone") String receiverPhone,
                                            @Field("receiver_address") String receiverAddress,
                                            @Field("receiver_name") String receiverName,
                                            @Field("description") String description);

    @GET("admin/orders-content/{id}")
    Call<AdminGetOrderContentResponse> adminGetOrderContentById(@HeaderMap Map<String, String> headers,
                                                                @Path("id") String orderId);

    @FormUrlEncoded
    @POST("admin/orders-content/{id}")
    Call<AdminModifyOrderContentResponse> adminModifyContent(@HeaderMap Map<String, String> headers,
                                                             @Path("id") String orderId,
                                                             @Field("product_id") String productId,
                                                             @Field("quantity") String quantity);

    /*-------------------------------------------------------------------*/
    /*--------------------------- ADMIN PRODUCTS-------------------------*/
    /*-------------------------------------------------------------------*/

    @GET("admin/products")
    Call<AdminProductsResponse> adminGetProducts(@HeaderMap Map<String, String> headers,
                                                 @QueryMap Map<String, String> parameters );

    @GET("admin/products/{id}")
    Call<AdminGetProductByIdResponse> adminGetProductById(@HeaderMap Map<String, String> headers, @Path("id") String orderId);

    @FormUrlEncoded
    @PUT("admin/products/{id}")
    Call<AdminUpdateProductResponse> adminUpdateProductById(@HeaderMap Map<String, String> headers,
                                                            @Path("id") String productId,
                                                            @Field("name") String name,
                                                            @Field("remaining") String remaining,
                                                            @Field("manufacturer") String manufacturer,
                                                            @Field("price") String price,
                                                            @Field("screen_size") String screenSize,
                                                            @Field("cpu") String cpu,
                                                            @Field("ram") String ram,
                                                            @Field("graphic_card") String graphicCard,
                                                            @Field("rom") String rom,
                                                            @Field("demand") String demand,
                                                            @Field("content") String content);

    @FormUrlEncoded
    @POST("admin/products")
    Call<AdminUpdateProductResponse> adminCreateProduct(@HeaderMap Map<String, String> headers,
                                                        @Field("name") String name,
                                                        @Field("remaining") String remaining,
                                                        @Field("manufacturer") String manufacturer,
                                                        @Field("price") String price,
                                                        @Field("screen_size") String screenSize,
                                                        @Field("cpu") String cpu,
                                                        @Field("ram") String ram,
                                                        @Field("graphic_card") String graphicCard,
                                                        @Field("rom") String rom,
                                                        @Field("demand") String demand,
                                                        @Field("content") String content);


    /*-------------------------------------------------------------------*/
    /*--------------------------- ADMIN PHOTOS-------------------------*/
    /*-------------------------------------------------------------------*/

    @GET("admin/products/photos/{id}")
    Call<PhotosResponse> adminGetPhotos(@HeaderMap Map<String, String> headers,
                                        @Path("id") String productId);

    @DELETE("admin/products/photos/{productId}/{photoId}")
    Call<PhotoResponse> adminRemovePhoto(@HeaderMap Map<String, String> headers,
                                         @Path("productId") String productId,
                                         @Path("photoId") int photoId);

    @PUT("admin/products/photos/{productId}/{photoId}")
    Call<PhotoResponse> adminSetAvatar(@HeaderMap Map<String, String> headers,
                                       @Path("productId") String productId,
                                       @Path("photoId") int photoId);
}
