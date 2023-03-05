package com.example.doanthuctap.viewModel.authentication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AuthWithGoogleResponse;
import com.example.doanthuctap.container.LoginResponse;

import org.json.JSONObject;

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
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
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
//                animation.setValue(false);
//                object.setValue(null);
                System.out.println("Auth with Account - throwable: " + t.getMessage());
                System.out.println(t.getCause());
                animation.setValue(false);
            }
        });
    }

    /**
     * auth with google account
     * @param idToken is the id token from google API returns when
     * users authorize us to access their information from their google account
     */
    private MutableLiveData<AuthWithGoogleResponse> authWithGoogleResponse;
    public MutableLiveData<AuthWithGoogleResponse> getAuthWithGoogleResponse()
    {
        if(authWithGoogleResponse == null)
        {
            authWithGoogleResponse = new MutableLiveData<>();
        }
        return authWithGoogleResponse;
    }

    public void authWithGoogleAccount(String idToken)
    {
        if(authWithGoogleResponse == null)
        {
            authWithGoogleResponse = new MutableLiveData<>();
        }
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }

        animation.setValue(true);
        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 - auth with google account*/
        Call<AuthWithGoogleResponse> container = api.authWithGoogleAccount(idToken);
        container.enqueue(new Callback<AuthWithGoogleResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthWithGoogleResponse> call,
                                   @NonNull Response<AuthWithGoogleResponse> dataResponse) {
                if(dataResponse.isSuccessful())
                {
                    AuthWithGoogleResponse content = dataResponse.body();
                    assert content != null;
                    authWithGoogleResponse.setValue(content);
                    animation.setValue(false);
                }
                if(dataResponse.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(dataResponse.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }

                    authWithGoogleResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthWithGoogleResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("Auth with Google account - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });
    }
}
