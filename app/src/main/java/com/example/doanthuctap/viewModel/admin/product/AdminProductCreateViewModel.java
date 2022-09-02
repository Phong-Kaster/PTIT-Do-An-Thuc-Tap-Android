package com.example.doanthuctap.viewModel.admin.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminUpdateProductResponse;
import com.example.doanthuctap.repository.AdminProductRepository;

import java.util.Map;

public class AdminProductCreateViewModel extends ViewModel {
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }

        return animation;
    }



    private AdminProductRepository productRepository;
    public void instantiate()
    {
        if( productRepository == null)
        {
            productRepository = new AdminProductRepository();
        }
    }

    public MutableLiveData<AdminUpdateProductResponse> response;
    public MutableLiveData<AdminUpdateProductResponse> getResponse()
    {
        if( response == null )
        {
            response = new MutableLiveData<>();
        }
        return response;
    }
    public void createProduct(Map<String, String> headers, Map<String, String> body)
    {
        animation = productRepository.getAnimation();
        response = productRepository.createProductWithBody(headers, body);
    }
}
