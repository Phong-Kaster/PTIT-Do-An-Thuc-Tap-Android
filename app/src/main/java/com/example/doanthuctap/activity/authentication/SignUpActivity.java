package com.example.doanthuctap.activity.authentication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.home.HomeActivity;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.User;
import com.example.doanthuctap.viewModel.authentication.LoginViewModel;
import com.example.doanthuctap.viewModel.authentication.SignupViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtPasswordConfirm;

    private AppCompatButton buttonSignup;
    private SignupViewModel viewModel;
    private LoadingScreen loadingScreen;
    private Dialog dialog;

    private GlobalVariable globalVariable;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        setupComponent();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        txtFirstName = findViewById(R.id.signUpFirstname);
        txtLastName = findViewById(R.id.signUpLastname);
        txtEmail = findViewById(R.id.signUpEmail);
        txtPassword = findViewById(R.id.signUpPassword);
        txtPasswordConfirm = findViewById(R.id.signUpPasswordConfirm);

        buttonSignup = findViewById(R.id.signUpButtonSignUp);
        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);
    }

    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);


        dialog.announce();


        /*Step 3 - declare*/
        viewModel.getResponse().observe(this, response->{
            int result = response.getResult();
            String msg = response.getMsg();
            if( result == 1)
            {
                Toast.makeText(this, getString(R.string.sign_up_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("email", txtEmail.getText().toString());
                intent.putExtra("password", txtPassword.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
            else
            {

                dialog.show(R.string.attention, msg, R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view1->{dialog.close();});
            }
        });


        /*Step 4 - animation*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });


    }



    private void setupEvent()
    {
        buttonSignup.setOnClickListener(view->{
            /*Step 1 - declare*/
            String email = txtEmail.getText().toString();
            String password =txtPassword.getText().toString();
            String passwordConfirm = txtPasswordConfirm.getText().toString();
            String firstName = txtFirstName.getText().toString();
            String lastName = txtLastName.getText().toString();


            /*Step 2 - declare*/
            viewModel.signup(email, password, passwordConfirm, firstName, lastName);


            /*Step 3 - animation*/
            viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean.equals(true) ){
                loadingScreen.start();
            }
            loadingScreen.stop();
        });
        });
    }
}