package com.example.doanthuctap.viewModel.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ProductByIdResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.repository.ClientOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class ProductInformationViewModel extends ViewModel {
    private MutableLiveData<ProductByIdResponse> objects;
    private MutableLiveData<Boolean> animation;

    private ClientProductsRepository productRepository;
    private MutableLiveData<ProductsResponse> similarProducts;

    private MutableLiveData<GetLatestOrderResponse> latestOrder;
    private ClientOrderRepository orderRepository;

    public MutableLiveData<ProductByIdResponse> getObjects() {
        if( objects == null)
        {
            objects = new MutableLiveData<>();
        }

        return objects;
    }

    public MutableLiveData<Boolean> getAnimation(){
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }

        return animation;
    }

    public MutableLiveData<ProductsResponse> getSimilarProducts()
    {
        if( similarProducts == null)
        {
            similarProducts = new MutableLiveData<>();
        }

        return similarProducts;
    }


    public MutableLiveData<GetLatestOrderResponse> getLatestOrderWithAuthUser(){
        if( latestOrder == null)
        {
            latestOrder = new MutableLiveData<>();
        }

        return latestOrder;
    }

    public void instantiate(){
        if( productRepository == null ){
            productRepository = ClientProductsRepository.getInstance();
        }
        if( orderRepository == null){
            orderRepository = ClientOrderRepository.getInstance();
        }
    }

    public void getProductById(String id){
        objects = productRepository.getProductById(id);
        animation = productRepository.getAnimation();
    }

    public void getProducts(Map<String, String> parameters)
    {
        similarProducts = productRepository.getProducts(parameters);
    }

    public void getLatestOrder(Map<String, String> headers){
        latestOrder = orderRepository.getLatestOrder(headers);
        animation = orderRepository.getAnimation();
    }
}
