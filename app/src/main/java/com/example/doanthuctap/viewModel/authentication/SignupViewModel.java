package com.example.doanthuctap.viewModel.authentication;



import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AuthWithGoogleResponse;
import com.example.doanthuctap.container.GetOrderByIdResponse;
import com.example.doanthuctap.container.LoginResponse;
import com.example.doanthuctap.container.SignUpResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;

public class SignupViewModel extends ViewModel {

    private MutableLiveData<SignUpResponse> response;
    private MutableLiveData<Boolean> animation;

    public MutableLiveData<SignUpResponse> getResponse() {
        if(response == null)
        {
            response = new MutableLiveData<>();
        }
        return response;
    }

    public MutableLiveData<Boolean> getAnimation() {
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /**
     * sign up with email & password
     * @param email
     * @param password
     * @param passwordConfirm
     * @param firstName
     * @param lastName
     */
    public void signup(String email,
                       String password,
                       String passwordConfirm,
                        String firstName,
                       String lastName)
    {
        if(response == null)
        {
            response = new MutableLiveData<>();
        }
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }
        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<SignUpResponse> container = api.signup(email, password, passwordConfirm, firstName, lastName);
        container.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call,
                                   @NonNull Response<SignUpResponse> dataResponse) {
                if(dataResponse.isSuccessful())
                {
                    SignUpResponse content = dataResponse.body();
                    assert content != null;
                    response.setValue(content);
                    animation.setValue(false);
                }
                if(dataResponse.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(dataResponse.errorBody().string());
                        System.out.println( jObjError );
                    } catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }

                    response.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("SignupViewModel - throwable: " + t.getMessage());
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
