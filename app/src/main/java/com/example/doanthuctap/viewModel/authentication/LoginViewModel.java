package com.example.doanthuctap.viewModel.authentication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> object;
    private MutableLiveData<Boolean> animation;

    /*************** GETTER ******************/
    public MutableLiveData<LoginResponse> getObjects() {
        if( object == null ){
            object = new MutableLiveData<>();
        }
        return object;
    }

    public MutableLiveData<Boolean> getAnimation() {
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /*************** FUNCTION ******************/
    public void login(String username, String password){
        animation.setValue(true);
        /*Step 1*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 2*/
        Call<LoginResponse> container = api.login( username, password);

        /*Step 3*/
        container.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse( Call<LoginResponse> call, Response<LoginResponse> response) {
                animation.setValue(false);
                if(response.isSuccessful())
                {
                    LoginResponse content = response.body();
                    assert content != null;
                    object.setValue(content);
                }else
                {
                    object.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                animation.setValue(false);
                object.setValue(null);
            }
        });
    }
}
