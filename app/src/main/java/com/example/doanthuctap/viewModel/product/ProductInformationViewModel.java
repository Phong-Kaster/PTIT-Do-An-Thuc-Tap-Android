package com.example.doanthuctap.viewModel.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ProductByIdResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.repository.ClientOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class ProductInformationViewModel extends ViewModel {

    private MutableLiveData<ModifyOrderContentResponse> orderContent;
    public MutableLiveData<ModifyOrderContentResponse> getOrderContent() {
        if( orderContent == null )
        {
            orderContent = new MutableLiveData<>();
        }
        return orderContent;
    }



    private MutableLiveData<ProductByIdResponse> objects;
    public MutableLiveData<ProductByIdResponse> getObjects() {
        if( objects == null)
        {
            objects = new MutableLiveData<>();
        }

        return objects;
    }


    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation(){
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }

        return animation;
    }


    private MutableLiveData<ProductsResponse> similarProducts;
    public MutableLiveData<ProductsResponse> getSimilarProducts()
    {
        if( similarProducts == null)
        {
            similarProducts = new MutableLiveData<>();
        }

        return similarProducts;
    }


    private MutableLiveData<GetLatestOrderResponse> latestOrder;
    public MutableLiveData<GetLatestOrderResponse> getLatestOrderWithAuthUser(){
        if( latestOrder == null)
        {
            latestOrder = new MutableLiveData<>();
        }

        return latestOrder;
    }


    private ClientOrderRepository orderRepository;
    private ClientProductsRepository productRepository;
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

    private MutableLiveData<ModifyOrderContentResponse> modifyOrderContent;
    public MutableLiveData<ModifyOrderContentResponse> getModifyOrderContentResponse()
    {
        if( modifyOrderContent == null)
        {
            modifyOrderContent = new MutableLiveData<>();
        }
        return modifyOrderContent;
    }
    public void modifyOrderContent(Map<String, String> headers,String orderId, String productId, String quantity)
    {
        modifyOrderContent = orderRepository.modifyOrderContent(headers, orderId, productId, quantity);
        animation = orderRepository.getAnimation();
    }
}
