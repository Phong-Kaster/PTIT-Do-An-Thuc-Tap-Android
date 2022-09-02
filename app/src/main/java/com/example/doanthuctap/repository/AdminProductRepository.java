package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AdminGetProductByIdResponse;
import com.example.doanthuctap.container.AdminUpdateProductResponse;

import org.json.JSONObject;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.Path;

public class AdminProductRepository {
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        animation.setValue(true);
        return animation;
    }


    /*GET PRODUCT BY ID*/
    private MutableLiveData<AdminGetProductByIdResponse> getProduct;
    public MutableLiveData<AdminGetProductByIdResponse> getProductById(Map<String, String> headers, String orderId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( getProduct == null )
        {
            getProduct = new MutableLiveData<>();
        }


        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<AdminGetProductByIdResponse> container = api.adminGetProductById(headers, orderId);
        container.enqueue(new Callback<AdminGetProductByIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminGetProductByIdResponse> call, @NonNull Response<AdminGetProductByIdResponse> response) {
                if(response.isSuccessful())
                {
                    AdminGetProductByIdResponse content = response.body();
                    assert content != null;
                    getProduct.setValue(content);
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
                    getProduct.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminGetProductByIdResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Get Product By Id Repository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });


        return getProduct;
    }


    /*PUT UPDATE PRODUCT BY ID*/
    private MutableLiveData<AdminUpdateProductResponse> updateProduct;
    public MutableLiveData<AdminUpdateProductResponse> updateProductById(Map<String, String> headers, Map<String, String> body)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( updateProduct == null )
        {
            updateProduct = new MutableLiveData<>();
        }


        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        String productId = body.get("productId");
        String name = body.get("name");
        String remaining = body.get("remaining");
        String manufacturer = body.get("manufacturer");
        String price = body.get("price");
        String screenSize = body.get("screenSize");
        String cpu = body.get("cpu");
        String ram = body.get("ram");
        String graphicCard = body.get("graphicCard");
        String rom = body.get("rom");
        String demand = body.get("demand");
        String content = body.get("content");


        /*Step 3*/
        Call<AdminUpdateProductResponse> container = api.adminUpdateProductById(
                headers,
                productId,
                name,
                remaining,
                manufacturer,
                price,
                screenSize,
                cpu,
                ram,
                graphicCard,
                rom,
                demand,
                content);
        container.enqueue(new Callback<AdminUpdateProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminUpdateProductResponse> call, @NonNull Response<AdminUpdateProductResponse> response) {
                if(response.isSuccessful())
                {
                    AdminUpdateProductResponse content = response.body();
                    assert content != null;
                    updateProduct.setValue(content);
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
                    updateProduct.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminUpdateProductResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Product Repository - update product by id - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });


        return updateProduct;
    }


    /*POST CREATE PRODUCT*/
    private MutableLiveData<AdminUpdateProductResponse> createProduct;
    public MutableLiveData<AdminUpdateProductResponse> createProductWithBody(Map<String, String> headers, Map<String, String> body)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        if( createProduct == null )
        {
            createProduct = new MutableLiveData<>();
        }


        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        String name = body.get("name");
        String remaining = body.get("remaining");
        String manufacturer = body.get("manufacturer");
        String price = body.get("price");
        String screenSize = body.get("screenSize");
        String cpu = body.get("cpu");
        String ram = body.get("ram");
        String graphicCard = body.get("graphicCard");
        String rom = body.get("rom");
        String demand = body.get("demand");
        String content = body.get("content");


        /*Step 3*/
        Call<AdminUpdateProductResponse> container = api.adminCreateProduct(
                headers,
                name,
                remaining,
                manufacturer,
                price,
                screenSize,
                cpu,
                ram,
                graphicCard,
                rom,
                demand,
                content);
        container.enqueue(new Callback<AdminUpdateProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminUpdateProductResponse> call, @NonNull Response<AdminUpdateProductResponse> response) {
                if(response.isSuccessful())
                {
                    AdminUpdateProductResponse content = response.body();
                    assert content != null;
                    createProduct.setValue(content);
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
                    createProduct.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminUpdateProductResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Product Repository - create product - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });


        return createProduct;
    }
}
