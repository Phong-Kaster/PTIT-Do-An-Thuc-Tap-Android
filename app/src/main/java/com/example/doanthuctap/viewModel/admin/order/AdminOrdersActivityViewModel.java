package com.example.doanthuctap.viewModel.admin.order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminDeleteOrderResponse;
import com.example.doanthuctap.container.AdminGetAllOrdersResponse;
import com.example.doanthuctap.repository.AdminOrderRepository;

import java.util.Map;

public class AdminOrdersActivityViewModel extends ViewModel {

    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    private MutableLiveData<AdminGetAllOrdersResponse> allOrders;
    public MutableLiveData<AdminGetAllOrdersResponse> getAllOrders()
    {
        if( allOrders == null)
        {
            allOrders = new MutableLiveData<>();
        }
        return allOrders;
    }


    private AdminOrderRepository repository;
    public void instantiate()
    {
        if( repository == null)
        {
            repository = new AdminOrderRepository();
        }
    }

    public void adminGetAllOrders(Map<String, String> headers, Map<String, String> parameters)
    {
        allOrders = repository.getAllOrdersResponse(headers, parameters);
        animation = repository.getAnimation();
    }

    private MutableLiveData<AdminDeleteOrderResponse> deleteOrder;
    public MutableLiveData<AdminDeleteOrderResponse> getDeleteOrderResponse()
    {
        if( deleteOrder == null )
        {
            deleteOrder = new MutableLiveData<>();
        }
        return deleteOrder;
    }
    public void adminDeleteOrder(Map<String, String> headers, String orderId)
    {
        animation = repository.getAnimation();
        deleteOrder = repository.deleteOrder(headers, orderId);
    }
}
