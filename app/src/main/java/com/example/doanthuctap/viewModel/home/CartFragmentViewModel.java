package com.example.doanthuctap.viewModel.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.repository.ClientOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class CartFragmentViewModel extends ViewModel {

    private MutableLiveData<GetLatestOrderResponse> lastOrder;
    public MutableLiveData<GetLatestOrderResponse> getLastOrder() {
        if( lastOrder == null)
        {
            lastOrder = new MutableLiveData<>();
        }
        return lastOrder;
    }


    private MutableLiveData<ProductsResponse> products;
    public MutableLiveData<ProductsResponse> getProducts(){
        if( products == null )
        {
            products = new MutableLiveData<>();
        }
        return products;
    }


    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    private ClientOrderRepository orderRepository;
    private ClientProductsRepository productsRepository;
    public void instantiate()
    {
        if( orderRepository == null)
        {
            orderRepository = new ClientOrderRepository();
        }
        if( productsRepository == null )
        {
            productsRepository = new ClientProductsRepository();
        }
    }

    public void getOrderContents(Map<String, String> headers)
    {
        lastOrder = orderRepository.getLatestOrder(headers);
        animation = orderRepository.getAnimation();
    }

    public void getSimilarProducts(Map<String, String> parameters)
    {
        products = productsRepository.getProducts(parameters);
    }


    /**
     * modify order response data;
     */
    private MutableLiveData<ModifyOrderContentResponse> modifyOrderContentData;
    public MutableLiveData<ModifyOrderContentResponse> getModifyOrderContentData()
    {
        if( modifyOrderContentData == null )
        {
            modifyOrderContentData = new MutableLiveData<>();
        }
        return modifyOrderContentData;
    }

    public void modifyOrderContent(Map<String, String> headers, String orderId, String productId, String quantity )
    {
        modifyOrderContentData = orderRepository.modifyOrderContent(headers, orderId, productId, quantity);
    }
}
