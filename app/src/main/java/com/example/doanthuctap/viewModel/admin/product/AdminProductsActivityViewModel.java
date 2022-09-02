package com.example.doanthuctap.viewModel.admin.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminProductsResponse;
import com.example.doanthuctap.repository.AdminProductsRepository;

import java.util.Map;

public class AdminProductsActivityViewModel extends ViewModel {


    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    private MutableLiveData<AdminProductsResponse> productsResponse;
    public MutableLiveData<AdminProductsResponse> getProductsResponse()
    {
        if( productsResponse == null)
        {
            productsResponse = new MutableLiveData<>();
        }
        return productsResponse;
    }


    private AdminProductsRepository repository;
    public void instantiate()
    {
        if( repository == null)
        {
            repository = new AdminProductsRepository();
        }
    }


    public void getProducts(Map<String, String> headers, Map<String, String> parameters)
    {
        animation = repository.getAnimation();
        productsResponse = repository.getProducts(headers, parameters);
    }
}
