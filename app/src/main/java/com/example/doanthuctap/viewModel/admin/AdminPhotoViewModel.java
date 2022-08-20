package com.example.doanthuctap.viewModel.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.container.PhotoResponse;
import com.example.doanthuctap.container.PhotosResponse;
import com.example.doanthuctap.repository.AdminOrderRepository;
import com.example.doanthuctap.repository.AdminPhotoRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import java.util.Map;

import retrofit2.http.Header;

public class AdminPhotoViewModel extends ViewModel {

    /*animation*/
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }



    private AdminPhotoRepository repository;
    public void instantiate()
    {
        if( repository == null)
        {
            repository = new AdminPhotoRepository();
        }
    }

    /**
     * get photos response
     */
    private MutableLiveData<PhotosResponse> photos;
    public MutableLiveData<PhotosResponse> getPhotosResponse()
    {
        if( photos == null)
        {
            photos = new MutableLiveData<>();
        }
        return photos;
    }


    /**
     * get photo response
     */
    private MutableLiveData<PhotoResponse> photo;
    public MutableLiveData<PhotoResponse> getPhotoResponse()
    {
        if( photo == null)
        {
            photos = new MutableLiveData<>();
        }
        return photo;
    }


    /**
     * get photos
     */
    public void getPhotos(Map<String, String> headers, String productId)
    {
        animation = repository.getAnimation();
        photos = repository.getPhotos(headers, productId);
    }

    /**
    * delete photo of a product
    * */
    public void removePhoto(Map<String, String> headers, String productId, int photoId)
    {
        photo = repository.removePhoto(headers, productId, photoId);
    }

    /**
     * set avatar
     */
    public void setAvatar(Map<String, String> headers, String productId, int photoId)
    {
        photo = repository.setAvatar(headers, productId, photoId);
    }
}
