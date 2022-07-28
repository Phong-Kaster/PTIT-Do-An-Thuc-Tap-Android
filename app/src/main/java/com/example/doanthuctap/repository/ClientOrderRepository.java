package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ModifyReceiverResponse;
import com.example.doanthuctap.container.ProductsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
     * @return instance of Clients Products Repository
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
    public MutableLiveData<ModifyOrderContentResponse> getContentObjects()
    {
        if( contentObjects == null)
        {
            contentObjects = new MutableLiveData<>();
        }
        return  contentObjects;
    }

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

    public MutableLiveData<ModifyOrderContentResponse> modifyOrderContent(String orderId, String productId, String quantity)
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


        Call<ModifyOrderContentResponse> container = api.modifyOrderContent(orderId, productId, quantity);


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

    public MutableLiveData<ModifyReceiverResponse> modifyReceiverOrder(String orderId,
                                                                       String receiverPhone,
                                                                       String receiverAddress,
                                                                       String receiverName,
                                                                       String description)
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
        if( orderId.length() < 15)
        {
            System.out.println("Client Order Repository - modifyReceiverOrder - orderId < 15 characters ");
            return null;
        }

        if( receiverPhone.length() < 1 )
        {
            System.out.println("Client Order Repository - modifyReceiverOrder - receiverPhone.length < 1");
            return null;
        }

        if( receiverAddress.length() < 1)
        {
            System.out.println("Client Order Repository - modifyReceiverOrder - receiverAddress.length < 1");
            return null;
        }

        if( receiverName.length() < 1 )
        {
            System.out.println("Client Order Repository - modifyReceiverOrder - receiverAddress.length < 1");
            return null;
        }


        /*Step 3*/
        Call<ModifyReceiverResponse> container = api.modifyReceiverInformation(orderId, receiverPhone, receiverAddress, receiverName, description);
        container.enqueue(new Callback<ModifyReceiverResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModifyReceiverResponse> call,
                                   @NonNull Response<ModifyReceiverResponse> response) {
                if(response.isSuccessful())
                {
                    ModifyReceiverResponse content = response.body();
                    assert content != null;
                    System.out.println("ClientOrderRepository-modifyOrderContent-result: " + content.getResult());
                    System.out.println("ClientOrderRepository-modifyOrderContent-msg: " + content.getMsg());
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
}
