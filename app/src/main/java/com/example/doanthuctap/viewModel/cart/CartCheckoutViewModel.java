package com.example.doanthuctap.viewModel.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.repository.ClientOrderRepository;

import java.util.Map;

public class CartCheckoutViewModel extends ViewModel {


    /**
     * latest order is the current cart that users are modifying
     */
    private MutableLiveData<GetLatestOrderResponse> latestOrder;
    public MutableLiveData<GetLatestOrderResponse> getLatestOrder()
    {
        if( latestOrder == null)
        {
            latestOrder = new MutableLiveData<>();
        }
        return latestOrder;
    }


    private ClientOrderRepository orderRepository;
    public void instanticate()
    {
        if( orderRepository == null )
        {
            orderRepository = new ClientOrderRepository();
        }
    }

    public void getLatestOrderInformation(Map<String, String> headers)
    {
        latestOrder = orderRepository.getLatestOrder(headers);
    }
}
