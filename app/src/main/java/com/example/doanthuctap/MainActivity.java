package com.example.doanthuctap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.doanthuctap.activity.authentication.LoginActivity;
import com.example.doanthuctap.activity.home.HomeActivity;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.model.User;
import com.example.doanthuctap.viewModel.MainViewModel;

/**
 * @author Phong-Kaster
 * no matter users have account or doesn't have, they are able to use this application
 */
public class MainActivity extends AppCompatActivity {

    private GlobalVariable globalVariable;
    private SharedPreferences sharedPreferences;
    private MainViewModel viewModel;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupVariable();


        String accessToken = sharedPreferences.getString("accessToken", null);
        /*Neu da dang nhap tu lan truoc thi lay ra accessToken va thong tin nguoi dung*/
        if(accessToken != null)
        {
            /*global variable chi hoat dong trong phien lam viec nen phai gan lai accessToken cho no*/
            globalVariable.setAccessToken(accessToken);
            viewModel.getProfile(accessToken);
        }

        /*observe viewModel.getProfile() responses */
        viewModel.profileObject().observe(this, profileResponse -> {
            int result = profileResponse.getResult();
            /*store user's information if accessToken is valid*/
            if( result == 1)
            {
                User user = profileResponse.getData();
                globalVariable.setAuthUser( user );
            }
            /*show message if get profile fails*/
            else
            {
                dialog.announce();
                dialog.show( getString(R.string.attention),
                        getString(R.string.oops_there_is_an_issue),
                        R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->dialog.close());
            }
        });

        /*delay 1s before starting HomeActivity*/
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);


    }


    /**
     * @author Phong-Kaster
     * instantiate local variable in MainActivity
     */
    private void setupVariable(){
        globalVariable = ((GlobalVariable)this.getApplication());

        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);

        viewModel = new  ViewModelProvider(this).get(MainViewModel.class);

        dialog = new Dialog(this);
    }
}