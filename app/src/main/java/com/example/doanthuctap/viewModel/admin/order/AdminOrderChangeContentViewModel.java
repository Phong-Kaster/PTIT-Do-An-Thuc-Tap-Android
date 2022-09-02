package com.example.doanthuctap.viewModel.admin.order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminGetOrderContentResponse;
import com.example.doanthuctap.container.AdminModifyOrderContentResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.model.AdminGetOrderByIdResponse;
import com.example.doanthuctap.repository.AdminOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class AdminOrderChangeContentViewModel extends ViewModel {

    /*animation*/
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    private AdminOrderRepository adminOrderRepository;
    private ClientProductsRepository clientOrderRepository;
    public void instantiate()
    {
        adminOrderRepository = new AdminOrderRepository();
        clientOrderRepository = new ClientProductsRepository();
    }


    /**
     * get order by id
     */
    private MutableLiveData<AdminGetOrderContentResponse> orderContent;
    public MutableLiveData<AdminGetOrderContentResponse> getOrderContentResponse()
    {
        if( orderContent == null)
        {
            orderContent = new MutableLiveData<>();
        }

        return orderContent;
    }


    public void getOrderContentById(Map<String, String> headers, String orderId)
    {
        animation = adminOrderRepository.getAnimation();
        orderContent = adminOrderRepository.getOrderContentById(headers, orderId);
    }

    /**
     * MODIFY ORDER CONTENT
     * */
    private MutableLiveData<AdminModifyOrderContentResponse> modifyOrderContent;
    public MutableLiveData<AdminModifyOrderContentResponse> getModifyOrderContentResponse()
    {
        if( modifyOrderContent == null)
        {
            modifyOrderContent = new MutableLiveData<>();
        }
        return modifyOrderContent;
    }

    public void modifyOrderContent(Map<String, String> headers, String orderId, String productId, String quantity)
    {
        animation = adminOrderRepository.getAnimation();
        modifyOrderContent = adminOrderRepository.modifyOrderContent(headers, orderId, productId, quantity);
    }

    /*GET ALL PRODUCTS*/
    private MutableLiveData<ProductsResponse> productsResponse;
    public MutableLiveData<ProductsResponse> getProductsResponse()
    {
        if( productsResponse == null )
        {
            productsResponse = new MutableLiveData<>();
        }
        return productsResponse;
    }
    public void getProducts(Map<String, String> parameters)
    {
        animation = clientOrderRepository.getAnimation();
        productsResponse = clientOrderRepository.getProducts(parameters);
    }
}
