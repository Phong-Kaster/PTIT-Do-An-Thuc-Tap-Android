package com.example.doanthuctap.viewModel;

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

public class MainViewModel extends ViewModel {
    private MutableLiveData<ProfileResponse> profileObject;

    public MutableLiveData<ProfileResponse> profileObject() {
        if(profileObject == null)
        {
            profileObject = new MutableLiveData<>();
        }
        return profileObject;
    }

    /**
     * @author Phong-Kaster
     * @param token is the JWT Token which is attached to HTTPS headers
     */
    public void getProfile(String token){
        /*Step 1*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 2*/
        Call<ProfileResponse> container = api.profile(token);


        /*Step 3*/
        container.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                if(response.isSuccessful() )
                {
                    ProfileResponse content = response.body();
                    profileObject.setValue(content);
                }
                else
                {
                    profileObject.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                profileObject.setValue(null);
            }
        });
    }
}
