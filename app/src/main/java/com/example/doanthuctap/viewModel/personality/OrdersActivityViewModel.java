package com.example.doanthuctap.viewModel.personality;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.ConfirmOrderResponse;
import com.example.doanthuctap.container.GetAllOrdersResponse;
import com.example.doanthuctap.container.ModifyOrderContentResponse;
import com.example.doanthuctap.repository.ClientOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class OrdersActivityViewModel extends ViewModel {

    /* animation */
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    private MutableLiveData<GetAllOrdersResponse> allOrders;
    public MutableLiveData<GetAllOrdersResponse> getAllOrdersResponse()
    {
        if( allOrders == null )
        {
            allOrders = new MutableLiveData<>();
        }
        return allOrders;
    }


    private MutableLiveData<ConfirmOrderResponse> cancelOrder;
    public MutableLiveData<ConfirmOrderResponse> getCancelOrderResponse()
    {
        if( cancelOrder == null)
        {
            cancelOrder = new MutableLiveData<>();
        }
        return cancelOrder;
    }


    /**/
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

    public void getAllOrder(Map<String, String> headers, Map<String,String> parameters)
    {
        allOrders = orderRepository.getAllOrders(headers, parameters);
        animation = orderRepository.getAnimation();
    }

    public void cancelOrder(Map<String, String> headers, String orderId)
    {
        animation = orderRepository.getAnimation();
        cancelOrder = orderRepository.confirmOrder(headers, orderId, "cancel");
    }
}
