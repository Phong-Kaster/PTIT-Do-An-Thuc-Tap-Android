package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ProductsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientOrderRepository {
    private static ClientOrderRepository instance;
    private MutableLiveData<GetLatestOrderResponse> objects;
    private static MutableLiveData<Boolean> animation;

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
    public static ClientOrderRepository getInstance(){
        if( instance == null){
            instance = new ClientOrderRepository();
        }

        return instance;
    }

    public MutableLiveData<Boolean> getAnimation(){
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
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
}
