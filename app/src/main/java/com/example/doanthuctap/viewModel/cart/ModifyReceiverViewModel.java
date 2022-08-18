package com.example.doanthuctap.viewModel.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.ModifyReceiverResponse;
import com.example.doanthuctap.repository.ClientOrderRepository;

import java.util.Map;

public class ModifyReceiverViewModel extends ViewModel {

    private MutableLiveData<ModifyReceiverResponse> response;
    public MutableLiveData<ModifyReceiverResponse> getResponse()
    {
        if( response == null)
        {
            response = new MutableLiveData<>();
        }
        return response;
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

    /**
     * @author Phong-Kaster
     * modify receiver information of order
     * @param orderId
     * @param receiverPhone
     * @param receiverAddress
     * @param receiverName
     * @param description
     */
    public void modifyReceiverOrder(Map<String, String> headers, String orderId,
                                    String receiverPhone,
                                    String receiverAddress,
                                    String receiverName,
                                    String description,
                                    String total)
    {
        response = orderRepository.modifyOrderInformation(headers,orderId, receiverPhone,
                receiverAddress, receiverName, description, total);
        animation = orderRepository.getAnimation();
    }
}
