package com.example.doanthuctap.repository;

import android.net.Uri;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AdminGetAllOrdersResponse;
import com.example.doanthuctap.container.PhotoResponse;
import com.example.doanthuctap.container.PhotosResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.HeaderMap;
import retrofit2.http.Part;

public class AdminPhotoRepository {

    /*ANIMATION*/
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation()
    {
        if( animation ==null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /*
    * get photo of a product
    * */
    private MutableLiveData<PhotosResponse> photos;
    public MutableLiveData<PhotosResponse> getPhotos(Map<String, String> headers, String productId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        else
        {
            photos = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<PhotosResponse> container = api.adminGetPhotos(headers, productId);

        container.enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotosResponse> call, @NonNull Response<PhotosResponse> response) {
                if(response.isSuccessful())
                {
                    PhotosResponse content = response.body();
                    assert content != null;
                    photos.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    photos.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotosResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Photo Repository - get photos - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return photos;
    }


    /**
     * remove photo of a product
     */
    private MutableLiveData<PhotoResponse> photo;
    public MutableLiveData<PhotoResponse> removePhoto(Map<String, String> headers, String productId, int photoId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        else
        {
            photo = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<PhotoResponse> container = api.adminRemovePhoto(headers, productId, photoId);

        container.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotoResponse> call, @NonNull Response<PhotoResponse> response) {
                if(response.isSuccessful())
                {
                    PhotoResponse content = response.body();
                    assert content != null;
                    photo.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    photos.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Photo Repository - remove photo - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return photo;
    }

    /**
     * set default avatar
     * @param headers
     * @param productId
     * @param photoId
     * @return
     */
    public MutableLiveData<PhotoResponse> setAvatar(Map<String, String> headers, String productId, int photoId)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        else
        {
            photo = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        Call<PhotoResponse> container = api.adminSetAvatar(headers, productId, photoId);

        container.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotoResponse> call, @NonNull Response<PhotoResponse> response) {
                if(response.isSuccessful())
                {
                    PhotoResponse content = response.body();
                    assert content != null;
                    photo.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    photos.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Photo Repository - set avatar - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });

        return photo;
    }


    /**
     * upload photo
     */
    public MutableLiveData<PhotoResponse> uploadPhoto(String authorization, String productId, String filePath)
    {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        else
        {
            photo = new MutableLiveData<>();
        }

        /*Step 1*/
        animation.setValue(true);

        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        RequestBody requestBodyProductId = RequestBody.create(MediaType.parse("text/plain"), productId);

        File file = new File(Uri.parse(filePath).toString());
        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part actualFile = MultipartBody.Part.createFormData("file", file.getName(), requestBodyFile);

        Call<PhotoResponse> container = api.adminUploadPhoto(authorization, requestBodyProductId, actualFile);
        container.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotoResponse> call, @NonNull Response<PhotoResponse> response) {
                if(response.isSuccessful())
                {
                    PhotoResponse content = response.body();
                    assert content != null;
                    photo.setValue(content);
                    animation.setValue(false);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }


                    photos.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoResponse> call, @NonNull Throwable t) {
                System.out.println("Admin Photo Repository - upload photo - throwable: " + t.getMessage());
                animation.setValue(false);
                Log.d("failure", "message = " + t.getMessage());
                Log.d("failure", "cause = " + t.getCause());
            }
        });

        return photo;
    }
}
