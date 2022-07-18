package com.example.doanthuctap.viewModel.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class SearchViewModel extends ViewModel {
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

    public void getProducts(Map<String, String> parameters){
        objects = repository.getProducts(parameters);
        animation = repository.getAnimation();
    }
}
