package com.example.doanthuctap.activity.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private TextView txtForgotPassword;
    private TextView txtCreateAccount;


    private ImageButton btnGoogleLogin;
    private ImageButton btnFacebookLogin;
    private AppCompatButton btnLogin;

    private LoginViewModel viewModel;

    private LoadingScreen loadingScreen;
    private GlobalVariable globalVariable;
    private Dialog dialog;
    private SharedPreferences sharedPreferences;

    //login with google account
    private GoogleSignInOptions googleSignInOption;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //login with google account
        googleSignInOption = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("662484602449-j28h119j2a7i3gvh11sei5mndocitmid.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption);

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

//        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);

//        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);

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

        /*set up dialog*/
        dialog.announce();
        dialog.btnOK.setOnClickListener(view->dialog.close());

        viewModel.getObjects().observe(this, loginResponse -> {

            if (loginResponse == null) {
                dialog.show(getString(R.string.attention),
                        getString(R.string.oops_there_is_an_issue),
                        R.drawable.ic_close);
                return;
            }

            int result = loginResponse.getResult();
            String message = loginResponse.getMsg();

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
                dialog.show(getString(R.string.attention),
                        message,
                        R.drawable.ic_close);
            }

        });
    }

    /**
     * START SIGN UP ACTIVITY FOR RESULT
     */
    private final ActivityResultLauncher<Intent> startSignUpActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                int statusResult = result.getResultCode();
                if( statusResult == RESULT_OK)
                {
                    Intent data = result.getData();
                    assert data != null;


                    String email = data.getStringExtra("email");
                    String password = data.getStringExtra("password");
                    viewModel.login(email, password);
                }
                else
                {
                    dialog.announce();
                    dialog.show(R.string.attention,
                            getString(R.string.oops_there_is_an_issue),
                            R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view -> {
                        dialog.close();
                    });
                }
            });

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

        txtCreateAccount.setOnClickListener(view->{
            Intent intent = new Intent(this, SignUpActivity.class);
            startSignUpActivityForResult.launch(intent);
            startActivity(intent);
        });

//        txtForgotPassword.setOnClickListener(view-> {
//            Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show();
//        });

        btnGoogleLogin.setOnClickListener(view->{
            Intent intent = googleSignInClient.getSignInIntent();
            startGoogleSignInForResult.launch(intent);
        });

//        btnFacebookLogin.setOnClickListener(view->
//                Toast.makeText(this,"Tính năng đang được phát triển", Toast.LENGTH_SHORT).show());
    }


    /*
     * @author Phong-Kater
     * start google sign in for result
     * */
    private final ActivityResultLauncher<Intent> startGoogleSignInForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                int statusResult = result.getResultCode();
                if( statusResult == RESULT_OK)
                {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                    assert account != null;
                    createAccountWithGoogle(account);
                }
            });

    /**
     * @author Phong-Kaster
     * @param account is user's google account.
     */
    private void createAccountWithGoogle(GoogleSignInAccount account)
    {
        /*Step 1 - send id token to server to authentication | sign up*/
        String idToken = account.getIdToken();

        viewModel.authWithGoogleAccount(idToken);


        /*Step 2 - get animation*/


        /*Step 3 - receiver response*/
        dialog.announce();
        viewModel.getAuthWithGoogleResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {
                /*Lay du lieu tu API ra*/
                String accessToken = response.getAccessToken();
                User user = response.getData();


                /*Lay du lieu vao Global Variable*/
                globalVariable.setAccessToken( "JWT " + accessToken );
                globalVariable.setAuthUser(user);

                /*luu accessToken vao Shared Reference*/
                sharedPreferences.edit().putString("accessToken", "JWT " + accessToken.trim()).apply();

                /*hien thi thong bao la dang nhap thanh cong*/
                Toast.makeText(this, getString(R.string.login_with_google_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                String msg = response.getMsg();
                dialog.show(R.string.attention, msg, R.drawable.ic_info);
            }
        });
        dialog.btnOK.setOnClickListener(view->dialog.close());
    }
}