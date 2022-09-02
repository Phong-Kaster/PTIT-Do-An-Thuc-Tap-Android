package com.example.doanthuctap.viewModel.admin.order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminCreateOrder;
import com.example.doanthuctap.repository.AdminOrderRepository;

import java.util.Map;

public class AdminOrderCreateActivityViewModel extends ViewModel {
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

    private MutableLiveData<AdminCreateOrder> createOrder;
    public MutableLiveData<AdminCreateOrder> getCreateOrderResponse()
    {
        if( createOrder == null)
        {
            createOrder = new MutableLiveData<>();
        }
        return createOrder;
    }

    public void createOrder(Map<String, String> headers, String receiverPhone, String receiverAddress, String receiverName, String description)
    {
        animation = orderRepository.getAnimation();
        createOrder = orderRepository.createOrder(headers, receiverPhone, receiverAddress, receiverName, description);
    }
}
