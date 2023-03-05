package com.example.doanthuctap.viewModel.admin.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.AdminGetProductByIdResponse;
import com.example.doanthuctap.container.AdminUpdateProductResponse;
import com.example.doanthuctap.repository.AdminProductRepository;

import java.util.Map;

public class AdminProductEditViewModel extends ViewModel {


    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }

        return animation;
    }


    private MutableLiveData<AdminGetProductByIdResponse> getProduct;
    public MutableLiveData<AdminGetProductByIdResponse> getProductByIdResponse()
    {
        if( getProduct == null)
        {
            getProduct = new MutableLiveData<>();
        }
        return getProduct;
    }


    private AdminProductRepository productRepository;
    public void instantiate()
    {
        if( productRepository == null)
        {
            productRepository = new AdminProductRepository();
        }
    }

    /**
     * get product by id
     * @param headers is the header
     * @param orderId is the id of the order
     */
    public void getProductByID(Map<String, String> headers, String orderId)
    {
//        animation = productRepository.getAnimation();
        getProduct = productRepository.getProductById(headers, orderId);
    }


    public MutableLiveData<AdminUpdateProductResponse> updateProduct;
    public MutableLiveData<AdminUpdateProductResponse> getUpdateProductByIDResponse()
    {
        if( updateProduct == null )
        {
            updateProduct = new MutableLiveData<>();
        }
        return updateProduct;
    }


    private MutableLiveData<Boolean> updateProductAnimation;
    public MutableLiveData<Boolean> getUpdateProductAnimationAnimation()
    {
        if( updateProductAnimation == null )
        {
            updateProductAnimation = new MutableLiveData<>();
        }

        return updateProductAnimation;
    }
    public void updateProductByID(Map<String, String> headers, Map<String, String> body)
    {
        updateProductAnimation = productRepository.getAnimation();
        updateProduct = productRepository.updateProductById(headers, body);
    }
}
