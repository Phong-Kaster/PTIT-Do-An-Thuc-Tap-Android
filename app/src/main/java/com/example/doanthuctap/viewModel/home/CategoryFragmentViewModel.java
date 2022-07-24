package com.example.doanthuctap.viewModel.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.CategoriesResponse;
import com.example.doanthuctap.container.GetProductsWithCategoryIDResponse;
import com.example.doanthuctap.container.ProductsResponse;
import com.example.doanthuctap.repository.ClientCategoriesRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

public class CategoryFragmentViewModel extends ViewModel {
    private MutableLiveData<CategoriesResponse> categoriesData;
    public MutableLiveData<CategoriesResponse> getCategoriesData() {
        if( categoriesData == null)
        {
            categoriesData = new MutableLiveData<>();
        }
        return categoriesData;
    }


    private MutableLiveData<Boolean> animation;
    public  MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    private ClientCategoriesRepository categoriesRepository;
    private ClientProductsRepository productsRepository;
    public void instantiate()
    {
        if( categoriesRepository == null )
        {
            categoriesRepository = ClientCategoriesRepository.getInstance();// categories repository
        }
        if( productsRepository == null)
        {
            productsRepository = ClientProductsRepository.getInstance();// products repository
        }
    }


    private MutableLiveData<ProductsResponse> productsData;
    public MutableLiveData<ProductsResponse> getProductsData()
    {
        if( productsData == null)
        {
            productsData = new MutableLiveData<>();
        }
        return productsData;
    }

    /*
    * get all category and products' information relate with the first category
    * */
    public void getCategoriesAndProducts(Map<String, String> parameters)
    {
        categoriesData = categoriesRepository.getCategories();
        animation = productsRepository.getAnimation();
        productsData = productsRepository.getProducts(parameters);
    }

    /**
     * get products' information with category id
     */
    private MutableLiveData<GetProductsWithCategoryIDResponse> productsWithCategoryID;
    public MutableLiveData<GetProductsWithCategoryIDResponse> getProductsWithCategoryID()
    {
        if(productsWithCategoryID == null)
        {
            productsWithCategoryID = new MutableLiveData<>();
        }
        return productsWithCategoryID;
    }
    public void getProductsWithCategoryId(int id)
    {
        productsWithCategoryID = categoriesRepository.getProductsWithCategoryID(id);
    }
}
