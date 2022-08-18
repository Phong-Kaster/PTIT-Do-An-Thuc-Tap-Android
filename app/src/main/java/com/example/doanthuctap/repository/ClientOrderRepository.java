package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.ConfirmOrderResponse;
import com.example.doanthuctap.container.GetAllOrdersResponse;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.GetOrderByIdResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ModifyReceiverResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientOrderRepository {

    private MutableLiveData<ModifyReceiverResponse> receiverObjects;
    public MutableLiveData<ModifyReceiverResponse> getReceiverObjects()
    {
        if( receiverObjects == null )
        {
            receiverObjects = new MutableLiveData<>();
        }
        return receiverObjects;
    }

    private MutableLiveData<GetLatestOrderResponse> objects;
    public MutableLiveData<GetLatestOrderResponse> getObjects() {
        if( objects == null )
        {
            objects = new MutableLiveData<>();
        }
        return objects;
    }

    /**
     * public static getInstance() method.
     */
    private static ClientOrderRepository instance;
    public static ClientOrderRepository getInstance(){
        if( instance == null){
            instance = new ClientOrderRepository();
        }

        return instance;
    }

    private static MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation(){
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    private MutableLiveData<ModifyOrderContentResponse> contentObjects;
    /**
     * get information of the latest order
     */
    public MutableLiveData<GetLatestOrderResponse> getLatestOrder(Map<String, String> headers){
        if(objects == null){
            objects = new MutableLiveData<>();
        }
        if(animation == null )
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);


        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 */
        Call<GetLatestOrderResponse> container = api.getLatestOrder(headers);


        /*Step 3*/
        /*Step 3 - query & get data */
        container.enqueue(new Callback<GetLatestOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetLatestOrderResponse> call, @NonNull Response<GetLatestOrderResponse> response) {
                if(response.isSuccessful())
                {
                    GetLatestOrderResponse content = response.body();
                    assert content != null;
                    objects.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    objects.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetLatestOrderResponse> call, @NonNull Throwable t) {
                //objects.setValue(null);
                System.out.println("ClientOrderRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return objects;
    }

    /**
     * modify content of an order
     */
    public MutableLiveData<ModifyOrderContentResponse> modifyOrderContent(Map<String, String> headers, String orderId, String productId, String quantity)
    {
        if(contentObjects == null){
            contentObjects = new MutableLiveData<>();
        }
        if(animation == null )
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);


        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 */
        if(orderId.length() < 16)
        {
            System.out.println("ClientOrderRepository - modifyOrderContent - error: orderId < 16");
            return null;
        }
        if(productId.length() < 1)
        {
            System.out.println("ClientOrderRepository - modifyOrderContent - error: productId < 1");
            return null;
        }
        if(Integer.parseInt(quantity) < 0)
        {
            System.out.println("ClientOrderRepository - modifyOrderContent - error: quantity < 0");
            return null;
        }


        Call<ModifyOrderContentResponse> container = api.modifyOrderContent(headers, orderId, productId, quantity, "orderContent");


        /*Step 3*/
        container.enqueue(new Callback<ModifyOrderContentResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModifyOrderContentResponse> call,
                                   @NonNull Response<ModifyOrderContentResponse> response) {
                if(response.isSuccessful())
                {
                    ModifyOrderContentResponse content = response.body();
                    assert content != null;
//                    System.out.println("ClientOrderRepository-modifyOrderContent-result: " + content.getResult());
//                    System.out.println("ClientOrderRepository-modifyOrderContent-msg: " + content.getMsg());
//                    System.out.println("ClientOrderRepository-modifyOrderContent-total: " + content.getTotal());
                    contentObjects.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }

                    contentObjects.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModifyOrderContentResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("ClientOrderRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return contentObjects;
    }

    /**
     * modify information of an order
     */
    public MutableLiveData<ModifyReceiverResponse> modifyOrderInformation(Map<String, String> headers,String orderId,
                                                                       String receiverPhone,
                                                                       String receiverAddress,
                                                                       String receiverName,
                                                                       String description,
                                                                       String total)
    {
        if( receiverObjects == null )
        {
            receiverObjects = new MutableLiveData<>();
        }

        if(animation == null )
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);


        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 - check input*/
//        if( orderId.length() < 15)
//        {
//            System.out.println("Client Order Repository - modifyReceiverOrder - orderId < 15 characters ");
//            return null;
//        }
//
//        if( receiverPhone.length() < 1 )
//        {
//            System.out.println("Client Order Repository - modifyReceiverOrder - receiverPhone.length < 1");
//            return null;
//        }
//
//        if( receiverAddress.length() < 1)
//        {
//            System.out.println("Client Order Repository - modifyReceiverOrder - receiverAddress.length < 1");
//            return null;
//        }
//
//        if( receiverName.length() < 1 )
//        {
//            System.out.println("Client Order Repository - modifyReceiverOrder - receiverAddress.length < 1");
//            return null;
//        }


        /*Step 3*/
        Call<ModifyReceiverResponse> container = api.modifyOrderInformation(headers,orderId,
                                                                            receiverPhone,
                                                                            receiverAddress,
                                                                            receiverName,
                                                                            description,
                                                                            total,"orderInformation");
        container.enqueue(new Callback<ModifyReceiverResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModifyReceiverResponse> call,
                                   @NonNull Response<ModifyReceiverResponse> response) {
                if(response.isSuccessful())
                {
                    ModifyReceiverResponse content = response.body();
                    assert content != null;
                    System.out.println("ClientOrderRepository-modify order information-result: " + content.getResult());
                    System.out.println("ClientOrderRepository-modify order information-msg: " + content.getMsg());
//                    System.out.println("ClientOrderRepository-modifyOrderContent-total: " + content.getTotal());
                    receiverObjects.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }

                    contentObjects.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModifyReceiverResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("ClientOrderRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });



        return receiverObjects;
    }



    /**
     * get order confirm response
     * */
    private MutableLiveData<ConfirmOrderResponse> confirmOrderResponse;
    public MutableLiveData<ConfirmOrderResponse> confirmOrder(Map<String, String> header,String id, String orderStatus)
    {
        if( confirmOrderResponse == null )
        {
            confirmOrderResponse = new MutableLiveData<>();
        }

        if(animation == null )
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);

        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        String orderNextStatus = orderStatus.equals("processing") ? "verified" : "cancel";


        /*Step 2 - call api*/
        Call<ConfirmOrderResponse> container = api.confirmOrder(header,id, orderNextStatus);


        /*Step 3 - */
        container.enqueue(new Callback<ConfirmOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ConfirmOrderResponse> call,
                                   @NonNull Response<ConfirmOrderResponse> response) {
                if(response.isSuccessful())
                {
                    ConfirmOrderResponse content = response.body();
                    assert content != null;
                    System.out.println("ClientOrderRepository-confirmOrder-result: " + content.getResult());
                    System.out.println("ClientOrderRepository-confirmOrder-msg: " + content.getMsg());
//                    System.out.println("ClientOrderRepository-modifyOrderContent-total: " + content.getTotal());
                    confirmOrderResponse.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }

                    confirmOrderResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConfirmOrderResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("ClientOrderRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return confirmOrderResponse;
    }


    /**
     * get all orders of an user
     */
    private MutableLiveData<GetAllOrdersResponse> allOrdersResponse;
    public MutableLiveData<GetAllOrdersResponse> getAllOrders(Map<String, String> headers, Map<String, String> parameters)
    {
        if( allOrdersResponse == null )
        {
            allOrdersResponse = new MutableLiveData<>();
        }

        if(animation == null )
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);


        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<GetAllOrdersResponse> container = api.getAllOrders(headers, parameters);
        container.enqueue(new Callback<GetAllOrdersResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAllOrdersResponse> call,
                                   @NonNull Response<GetAllOrdersResponse> response) {
                if(response.isSuccessful())
                {
                    GetAllOrdersResponse content = response.body();
                    assert content != null;
                    allOrdersResponse.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }

                    contentObjects.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAllOrdersResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("ClientOrderRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });



        return allOrdersResponse;
    }

    /**
     * get order by id
     */
    private MutableLiveData<GetOrderByIdResponse> orderByIdResponse;
    public MutableLiveData<GetOrderByIdResponse> getOrderByID(String orderId, Map<String, String> headers)
    {
        if( orderByIdResponse == null )
        {
            orderByIdResponse = new MutableLiveData<>();
        }
        if(animation == null )
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);

        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<GetOrderByIdResponse> container = api.getOrderByID(orderId, headers);
        container.enqueue(new Callback<GetOrderByIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetOrderByIdResponse> call,
                                   @NonNull Response<GetOrderByIdResponse> response) {
                if(response.isSuccessful())
                {
                    GetOrderByIdResponse content = response.body();
                    assert content != null;
                    orderByIdResponse.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }

                    orderByIdResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetOrderByIdResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("ClientOrderRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return orderByIdResponse;
    }
}
