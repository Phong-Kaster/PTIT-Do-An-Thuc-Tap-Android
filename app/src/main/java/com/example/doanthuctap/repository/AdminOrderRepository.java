package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AdminCreateOrder;
import com.example.doanthuctap.container.AdminDeleteOrderResponse;
import com.example.doanthuctap.container.AdminGetAllOrdersResponse;
import com.example.doanthuctap.container.AdminGetOrderContentResponse;
import com.example.doanthuctap.container.AdminModifyOrderContentResponse;
import com.example.doanthuctap.container.AdminModifyOrderResponse;
import com.example.doanthuctap.container.CategoriesResponse;
import com.example.doanthuctap.model.AdminGetOrderByIdResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.Path;

public class AdminOrderRepository {
    /*ANIMATION*/
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation ==null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /* GET ALL ORDERS*/
    private MutableLiveData<AdminGetAllOrdersResponse> allOrders;
    public MutableLiveData<AdminGetAllOrdersResponse> getAllOrdersResponse(Map<String, String> headers,
                                                                           Map<String,String> parameters)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( allOrders == null)
        {
            allOrders = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminGetAllOrdersResponse> container = api.adminGetAllOrders(headers, parameters);

        container.enqueue(new Callback<AdminGetAllOrdersResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminGetAllOrdersResponse> call, @NonNull Response<AdminGetAllOrdersResponse> response) {
                if(response.isSuccessful())
                {
                    AdminGetAllOrdersResponse content = response.body();
                    assert content != null;
                    allOrders.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    allOrders.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminGetAllOrdersResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Order Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return allOrders;
    }

    /*DELETE ONE ORDER*/
    private MutableLiveData<AdminDeleteOrderResponse> deleteOrder;
    public MutableLiveData<AdminDeleteOrderResponse> deleteOrder(Map<String, String> headers,
                                                                  String orderId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( deleteOrder == null )
        {
            deleteOrder = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminDeleteOrderResponse> container = api.adminDeleteOrder(headers, orderId);
        container.enqueue(new Callback<AdminDeleteOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminDeleteOrderResponse> call, @NonNull Response<AdminDeleteOrderResponse> response) {
                if(response.isSuccessful())
                {
                    AdminDeleteOrderResponse content = response.body();
                    assert content != null;
                    deleteOrder.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }
                    deleteOrder.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminDeleteOrderResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Delete Order Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return deleteOrder;
    }


    /*GET ORDER BY ID*/
    private MutableLiveData<AdminGetOrderByIdResponse> orderById;
    public MutableLiveData<AdminGetOrderByIdResponse> getOrderById(Map<String, String> headers, String orderId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( orderById == null )
        {
            orderById = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminGetOrderByIdResponse> container = api.adminGetOrderByID(headers, orderId);
        container.enqueue(new Callback<AdminGetOrderByIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminGetOrderByIdResponse> call, @NonNull Response<AdminGetOrderByIdResponse> response) {
                if(response.isSuccessful())
                {
                    AdminGetOrderByIdResponse content = response.body();
                    assert content != null;
                    orderById.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }
                    orderById.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminGetOrderByIdResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Get Order By Id Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return orderById;
    }


    /*PUT MODIFY ORDER BY ID*/
    private MutableLiveData<AdminModifyOrderResponse> modifyOrder;
    public MutableLiveData<AdminModifyOrderResponse> modifyOrderById(Map<String, String> headers,
                                                                    String orderId,
                                                                    String receiverPhone,
                                                                    String receiverAddress,
                                                                    String receiverName,
                                                                    String status,
                                                                    String description)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( modifyOrder == null )
        {
            modifyOrder = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminModifyOrderResponse> container = api.adminModifyOrder(headers,
                                                    orderId,
                                                    receiverPhone,
                                                    receiverAddress,
                                                    receiverName,
                                                    status,
                                                    description);
        container.enqueue(new Callback<AdminModifyOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminModifyOrderResponse> call, @NonNull Response<AdminModifyOrderResponse> response) {
                if(response.isSuccessful())
                {
                    AdminModifyOrderResponse content = response.body();
                    assert content != null;
                    modifyOrder.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }
                    modifyOrder.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminModifyOrderResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Put Modify Order By Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return modifyOrder;
    }


    /*GET ORDER CONTENT BY ID*/
    private MutableLiveData<AdminGetOrderContentResponse> orderContent;
    public MutableLiveData<AdminGetOrderContentResponse> getOrderContentById(Map<String, String> headers,
                                                                       String orderId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( orderContent == null )
        {
            orderContent = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminGetOrderContentResponse> container = api.adminGetOrderContentById(headers, orderId);
        container.enqueue(new Callback<AdminGetOrderContentResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminGetOrderContentResponse> call, @NonNull Response<AdminGetOrderContentResponse> response) {
                if(response.isSuccessful())
                {
                    AdminGetOrderContentResponse content = response.body();
                    assert content != null;
                    orderContent.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }
                    orderContent.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminGetOrderContentResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Get Order Content By Id Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });


        return orderContent;
    }

    /*POST MODIFY ORDER CONTENT BY ID*/
    private MutableLiveData<AdminModifyOrderContentResponse> modifyOrderContent;
    public MutableLiveData<AdminModifyOrderContentResponse> modifyOrderContent(Map<String, String> headers, String orderId, String productId, String quantity)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( modifyOrderContent == null )
        {
            modifyOrderContent = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminModifyOrderContentResponse> container = api.adminModifyContent(headers, orderId, productId, quantity);
        container.enqueue(new Callback<AdminModifyOrderContentResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminModifyOrderContentResponse> call, @NonNull Response<AdminModifyOrderContentResponse> response) {
                if(response.isSuccessful())
                {
                    AdminModifyOrderContentResponse content = response.body();
                    assert content != null;
                    modifyOrderContent.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }
                    modifyOrderContent.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminModifyOrderContentResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Modify Order Content Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });


        return modifyOrderContent;
    }


    /*POST CREATE A NEW ORDER*/
    private MutableLiveData<AdminCreateOrder> createOrder;
    public MutableLiveData<AdminCreateOrder> createOrder(Map<String, String> headers,
                                                         String receiverPhone,
                                                         String receiverAddress,
                                                         String receiverName,
                                                         String description)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( createOrder == null )
        {
            createOrder = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminCreateOrder> container = api.adminCreateOrder(headers, receiverPhone, receiverAddress, receiverName, description);
        container.enqueue(new Callback<AdminCreateOrder>() {
            @Override
            public void onResponse(@NonNull Call<AdminCreateOrder> call, @NonNull Response<AdminCreateOrder> response) {
                if(response.isSuccessful())
                {
                    AdminCreateOrder content = response.body();
                    assert content != null;
                    createOrder.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }
                    createOrder.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminCreateOrder> call, @NonNull Throwable t) {
                System.out.println("Admin Create Order Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });


        return createOrder;
    }
}
