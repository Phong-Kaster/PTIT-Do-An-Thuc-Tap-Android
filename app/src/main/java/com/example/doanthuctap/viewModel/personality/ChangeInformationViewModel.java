package com.example.doanthuctap.viewModel.personality;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanthuctap.api.HTTPRequest;
import com.example.doanthuctap.api.HTTPService;
import com.example.doanthuctap.container.AuthWithGoogleResponse;
import com.example.doanthuctap.container.ChangeInformationResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeInformationViewModel extends ViewModel {

    /*RESPONSE*/
    private MutableLiveData<ChangeInformationResponse> response;
    public MutableLiveData<ChangeInformationResponse> getResponse() {
        if( response == null)
        {
            response = new MutableLiveData<>();
        }
        return response;
    }


    /*ANIMATION*/
    private MutableLiveData<Boolean> animation;
    public MutableLiveData<Boolean> getAnimation() {
        if( animation == null)
        {
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    public void changeInformation(Map<String, String> headers,
                                  String email,
                                  String firstName,
                                  String lastName,
                                  String phone,
                                  String address)
    {
        if(response == null)
        {
            response = new MutableLiveData<>();
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
        Call<ChangeInformationResponse> container = api.changeInformation(headers, email, firstName, lastName, phone, address);
        container.enqueue(new Callback<ChangeInformationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChangeInformationResponse> call,
                                   @NonNull Response<ChangeInformationResponse> dataResponse) {
                if(dataResponse.isSuccessful())
                {
                    ChangeInformationResponse content = dataResponse.body();
                    assert content != null;
                    response.setValue(content);
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

                    response.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChangeInformationResponse> call,
                                  @NonNull Throwable t) {
                System.out.println("Change Information View Model - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });
    }
}
