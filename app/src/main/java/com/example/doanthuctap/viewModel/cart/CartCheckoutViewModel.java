package com.example.doanthuctap.viewModel.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.ConfirmOrderResponse;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.container.ModifyReceiverResponse;
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
        animation = orderRepository.getAnimation();
    }

    private MutableLiveData<ConfirmOrderResponse> confirmOrderResponse;
    public MutableLiveData<ConfirmOrderResponse> getConfirmOrderResponse()
    {
        if( confirmOrderResponse == null)
        {
            confirmOrderResponse = new MutableLiveData<>();
        }
        return confirmOrderResponse;
    }

    public void confirmOrder(Map<String, String> header,String orderId, String orderStatus)
    {
        confirmOrderResponse = orderRepository.confirmOrder(header,orderId, orderStatus);
        animation = orderRepository.getAnimation();
    }

    private MutableLiveData<ModifyReceiverResponse> modifyOrderInformation;

    public MutableLiveData<ModifyReceiverResponse> getModifyOrderInformationResponse()
    {
        if( modifyOrderInformation == null)
        {
            modifyOrderInformation = new MutableLiveData<>();
        }
        return modifyOrderInformation;
    }

    public void modifyOrderInformation(Map<String, String> headers,String orderId,
                                    String receiverPhone,
                                    String receiverAddress,
                                    String receiverName,
                                    String description,
                                    String total)
    {
        if( modifyOrderInformation == null )
        {
            modifyOrderInformation = new MutableLiveData<>();
        }
        modifyOrderInformation = orderRepository.modifyOrderInformation(headers,orderId, receiverPhone,
                receiverAddress, receiverName, description, total);
        animation = orderRepository.getAnimation();
    }
}
