package com.example.doanthuctap.viewModel.admin;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.PhotoResponse;
import com.example.doanthuctap.container.PhotosResponse;
import com.example.doanthuctap.repository.AdminOrderRepository;
import com.example.doanthuctap.repository.AdminPhotoRepository;
import com.example.doanthuctap.repository.ClientProductsRepository;

import org.json.JSONObject;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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

    /**
     * upload photo
     */
    public void updatePhoto(String authorization, String productId, String filePath)
    {
        photo = repository.uploadPhoto(authorization, productId, filePath);
        animation = repository.getAnimation();
    }
}
