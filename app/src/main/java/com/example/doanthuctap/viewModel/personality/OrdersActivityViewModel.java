package com.example.doanthuctap.viewModel.personality;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.GetAllOrdersResponse;
import com.example.doanthuctap.repository.ClientOrderRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class OrdersActivityViewModel extends ViewModel {

    /* animation */
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    private MutableLiveData<GetAllOrdersResponse> allOrdersResponse;
    public MutableLiveData<GetAllOrdersResponse> getAllOrdersResponse()
    {
        if( allOrdersResponse == null )
        {
            allOrdersResponse = new MutableLiveData<>();
        }
        return allOrdersResponse;
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
        allOrdersResponse = orderRepository.getAllOrders(headers, parameters);
        animation = orderRepository.getAnimation();
    }
}
