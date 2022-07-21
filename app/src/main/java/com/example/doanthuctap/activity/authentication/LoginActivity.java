package com.example.doanthuctap.activity.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.home.HomeActivity;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.User;
import com.example.doanthuctap.viewModel.authentication.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private TextView txtForgotPassword;
    private ImageButton btnGoogleLogin;
    private ImageButton btnFacebookLogin;
    private AppCompatButton btnLogin;

    private LoginViewModel viewModel;

    private LoadingScreen loadingScreen;
    private GlobalVariable globalVariable;
    private Dialog dialog;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupComponent();
        setupViewModel();
        setupEvent();

    }

    /**
     * @author Phong-Kaster
     * mapping components with XML layout
     */
    private void setupComponent() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnLogin = findViewById(R.id.btnLogin);


        loadingScreen = new LoadingScreen(this);
        globalVariable = (GlobalVariable) this.getApplication();
        dialog = new Dialog(this);
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
    }


    /**
     * @author Phong-Kaster
     * set up view model
     */
    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean ){
                loadingScreen.start();
            }
            else{
                loadingScreen.stop();
            }
        });
        viewModel.getObjects().observe(this, loginResponse -> {
            int result = loginResponse.getResult();
            if( result == 1)
            {
                /*Lay du lieu tu API ra*/
                String token = loginResponse.getAccessToken();
                User user = loginResponse.getData();

                /*Lay du lieu vao Global Variable*/
                globalVariable.setAccessToken( "JWT " + token );
                globalVariable.setAuthUser(user);

                /*luu accessToken vao Shared Reference*/
                sharedPreferences.edit().putString("accessToken", "JWT " + token.trim()).apply();

                /*hien thi thong bao la dang nhap thanh cong*/
                Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            else{
                dialog.announce();
                dialog.show(getString(R.string.attention),
                        getString(R.string.oops_there_is_an_issue),
                        R.drawable.ic_close);
                dialog.btnOK.setOnClickListener(view->dialog.close());
            }

        });
    }

    /**
     * @author Phong-Kaster
     * handle event when users click on button
     */
    private void setupEvent(){
        btnLogin.setOnClickListener(view->{
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();


            viewModel.login(username, password);
        });

        txtForgotPassword.setOnClickListener(view-> {
            Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show();
        });

        btnGoogleLogin.setOnClickListener(view->
                Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show());

        btnFacebookLogin.setOnClickListener(view->
                Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show());
    }

}