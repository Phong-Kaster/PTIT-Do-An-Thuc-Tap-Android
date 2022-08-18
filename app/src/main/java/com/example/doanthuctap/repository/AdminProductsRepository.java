package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AdminCreateOrder;
import com.example.doanthuctap.container.AdminProductsResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminProductsRepository {

    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    private MutableLiveData<AdminProductsResponse> productsResponse;
    public MutableLiveData<AdminProductsResponse> getProducts(Map<String, String> headers,
                                                               Map<String, String> parameters)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( productsResponse == null )
        {
            productsResponse = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminProductsResponse> container = api.adminGetProducts(headers, parameters);
        container.enqueue(new Callback<AdminProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminProductsResponse> call,
                                   @NonNull Response<AdminProductsResponse> response) {
                if(response.isSuccessful())
                {
                    AdminProductsResponse content = response.body();
                    assert content != null;
                    productsResponse.setValue(content);
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
                    productsResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminProductsResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Products Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return productsResponse;
    }

}
