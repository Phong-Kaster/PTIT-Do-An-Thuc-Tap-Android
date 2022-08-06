package com.example.doanthuctap.viewModel.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonalityFragmentViewModel extends ViewModel {
    private MutableLiveData<ProfileResponse> response;
    public MutableLiveData<ProfileResponse> getResponse() {
        if(response == null)
        {
            response = new MutableLiveData<>();
        }
        return response;
    }


    private MutableLiveData<ProfileResponse> animation;
    public MutableLiveData<ProfileResponse> getAnimation() {
        if(animation == null)
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }


    /**
     * @author Phong-Kaster
     * @param token is the JWT Token which is attached to HTTPS headers
     */
    public void getProfile(String token){
        if(response == null)
        {
            response = new MutableLiveData<>();
        }

        if(animation == null)
        {
            animation = new MutableLiveData<>();
        }

        /*Step 1*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 2*/
        Call<ProfileResponse> container = api.profile(token);


        /*Step 3*/
        container.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> dataResponse) {
                if(dataResponse.isSuccessful() )
                {
                    ProfileResponse content = dataResponse.body();
                    response.setValue(content);
                }
                else
                {
                    response.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                response.setValue(null);
            }
        });
    }
}
