package com.example.doanthuctap.viewModel.admin.order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminModifyOrderResponse;
import com.example.doanthuctap.model.AdminGetOrderByIdResponse;
import com.example.doanthuctap.repository.AdminOrderRepository;

import java.util.Map;

public class AdminOrderChangeInformationViewModel extends ViewModel {

    private AdminOrderRepository orderRepository;
    public void instantiate()
    {
        orderRepository = new AdminOrderRepository();
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

    /**
     * get order by id
     */
    private MutableLiveData<AdminGetOrderByIdResponse> orderById;
    public MutableLiveData<AdminGetOrderByIdResponse> getOrderByIdResponse()
    {
        if( orderById == null)
        {
            orderById = new MutableLiveData<>();
        }

        return orderById;
    }

    public void getOrderById(Map<String, String> headers, String orderId)
    {
        animation = orderRepository.getAnimation();
        orderById = orderRepository.getOrderById(headers, orderId);
    }


    /**
     * modify order by id
     */
    private MutableLiveData<AdminModifyOrderResponse> modifyOrder;
    public MutableLiveData<AdminModifyOrderResponse> getModifyOrderResponse()
    {
        if( modifyOrder == null )
        {
            modifyOrder = new MutableLiveData<>();
        }
        return modifyOrder;
    }
    public void modifyOrderById(Map<String, String> headers,
                                String orderId,
                                String receiverPhone,
                                String receiverAddress,
                                String receiverName,
                                String status,
                                String description)
    {
        animation = orderRepository.getAnimation();
        modifyOrder = orderRepository.modifyOrderById(headers, orderId, receiverPhone, receiverAddress, receiverName, status, description);
    }
}
