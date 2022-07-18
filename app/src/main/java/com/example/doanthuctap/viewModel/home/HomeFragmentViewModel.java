package com.example.doanthuctap.viewModel.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.container.TestResponse;
import com.example.doanthuctap.repository.ClientProductsRepository;
import com.google.android.material.circularreveal.CircularRevealLinearLayout;

import java.sql.SQLOutput;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragmentViewModel extends ViewModel {
    private MutableLiveData<ProductsResponse> objects;
    private MutableLiveData<Boolean> animation;
    private ClientProductsRepository repository;


    public MutableLiveData<ProductsResponse> getObjects() {
        if( objects == null ){
            objects = new MutableLiveData<>();
        }
        return objects;
    }

    public MutableLiveData<Boolean> getAnimation(){
        if( animation == null ){
           animation = new MutableLiveData<>();
        }

        return animation;
    }

    public void instantiate(){
        if( repository == null ){
            repository = ClientProductsRepository.getInstance();
        }
    }

    /**
     * get products' information from database
     * @return MutableLiveData<ProductsResponse> objects
     */
    public void getProducts(Map<String, String> parameters){
        objects = repository.getProducts(parameters);
        animation = repository.getAnimation();
    }
}
