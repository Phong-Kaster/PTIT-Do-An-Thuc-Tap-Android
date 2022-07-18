package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.ProductsResponse;

import java.sql.SQLOutput;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientProductsRepository {
    private static ClientProductsRepository instance;
    private MutableLiveData<ProductsResponse> objects;
    private static MutableLiveData<Boolean> animation;

    /**
     * public static getInstance() method.
     * @return instance of Clients Products Repository
     */
    public static ClientProductsRepository getInstance(){
        if( instance == null){
            instance = new ClientProductsRepository();
        }

        return instance;
    }

    public MutableLiveData<Boolean> getAnimation(){
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /**
     * get products' information from database
     * @return MutableLiveData<ProductsResponse> objects
     */
    public MutableLiveData<ProductsResponse> getProducts(Map<String, String> parameters){
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

        /*Step 2*/
        Call<ProductsResponse> container = api.getProducts(parameters);

        /*Step 3 - query & get data */
        container.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductsResponse> call, @NonNull Response<ProductsResponse> response) {
                if(response.isSuccessful())
                {
                    ProductsResponse content = response.body();
                    assert content != null;
                    objects.setValue(content);
                    animation.setValue(false);
                }
                else
                {
                    objects.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductsResponse> call, @NonNull Throwable t) {
                //objects.setValue(null);
                System.out.println("ClientProductsRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return objects;
    }
}
