package com.example.doanthuctap.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.LoginResponse;
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
    }


    /**
     * @author Phong-Kaster
     * set up login view model
     */
    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getAnimation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if( aBoolean ){
                    loadingScreen.start();
                }
                else{
                    loadingScreen.stop();
                }
            }
        });
        viewModel.getObjects().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                User user = loginResponse.getData();
                Toast.makeText(LoginActivity.this,"Ciao " + user.getFirstName(), Toast.LENGTH_SHORT).show();
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

        txtForgotPassword.setOnClickListener(view-> Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show());

        btnGoogleLogin.setOnClickListener(view-> Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show());

        btnFacebookLogin.setOnClickListener(view-> Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show());
    }
}