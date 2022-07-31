package com.example.doanthuctap.viewModel.personality;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.GetOrderByIdResponse;
import com.example.doanthuctap.repository.ClientOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class OrderInformationViewModel extends ViewModel {

    /* animation*/
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
    public void instanciate()
    {
        if( orderRepository == null )
        {
            orderRepository = new ClientOrderRepository();
        }
    }

    private MutableLiveData<GetOrderByIdResponse> orderByIdResponse;
    public MutableLiveData<GetOrderByIdResponse> getOrderByIdResponse()
    {
        if( orderByIdResponse == null)
        {
            orderByIdResponse = new MutableLiveData<>();
        }
        return orderByIdResponse;
    }
    public void getOrderByID(String orderId, Map<String, String> headers)
    {
        orderByIdResponse = orderRepository.getOrderByID(orderId, headers);
        animation = orderRepository.getAnimation();
    }

}
