package com.example.doanthuctap.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.CategoriesResponse;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.GetProductsWithCategoryIDResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientCategoriesRepository {

    private static ClientCategoriesRepository instance;
    public static ClientCategoriesRepository getInstance()
    {
        if( instance == null)
        {
            instance = new ClientCategoriesRepository();
        }
        return instance;
    }


    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    private MutableLiveData<CategoriesResponse> categories;
    public MutableLiveData<CategoriesResponse> getCategories()
    {
        if( categories == null)
        {
            categories = new MutableLiveData<>();
        }
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }

        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 */
        Call<CategoriesResponse> container = api.getCategories();


        /*Step 3*/
        container.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
                if(response.isSuccessful())
                {
                    CategoriesResponse content = response.body();
                    assert content != null;
                    categories.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    categories.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {
                //objects.setValue(null);
                System.out.println("ClientCategoriesRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return categories;
    }


    /**
     * @author Phong-Kaster
     * get products' information with category id
     *
     * @params categoryId is the id of the category we wanna find products
     */
    private MutableLiveData<GetProductsWithCategoryIDResponse> productsWithCategoryID;
    public MutableLiveData<GetProductsWithCategoryIDResponse> getProductsWithCategoryID(int categoryId){
        if( productsWithCategoryID == null)
        {
            productsWithCategoryID = new MutableLiveData<>();
        }
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }

        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 */
        if( categoryId < 1)
        {
            System.out.println("ClientCategoriesRepository - categoryId < 1");
            return null;
        }
        Call<GetProductsWithCategoryIDResponse> container = api.getProductsWithCategoryID(categoryId);


        /*Step 3 */
        container.enqueue(new Callback<GetProductsWithCategoryIDResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetProductsWithCategoryIDResponse> call,
                                   @NonNull Response<GetProductsWithCategoryIDResponse> response) {
                if(response.isSuccessful())
                {
                    GetProductsWithCategoryIDResponse content = response.body();
                    assert content != null;
                    productsWithCategoryID.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    productsWithCategoryID.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetProductsWithCategoryIDResponse> call, @NonNull Throwable t) {
                //objects.setValue(null);
                System.out.println("ClientCategoriesRepository - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });
        return productsWithCategoryID;
    }
}
