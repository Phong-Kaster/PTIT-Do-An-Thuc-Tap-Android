package com.example.doanthuctap.activity.personality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.User;
import com.example.doanthuctap.viewModel.personality.ChangeInformationViewModel;

import java.util.Map;

public class ChangeInformationActivity extends AppCompatActivity {

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtPhone;
    private EditText txtAddress;
    private EditText txtEmail;

    private AppCompatButton buttonSave;
    private ChangeInformationViewModel viewModel;

    private Map<String, String> headers;
    private GlobalVariable globalVariable;

    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private User authUser;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);

        setupComponent();
        setupViewModel();
        setupScreen();
        setupEvent();
    }

    private void setupComponent()
    {
        txtFirstName = findViewById(R.id.changeInforFirstName);
        txtLastName = findViewById(R.id.changeInforLastName);
        txtPhone = findViewById(R.id.changeInforPhone);
        txtAddress = findViewById(R.id.changeInforAddress);
        txtEmail = findViewById(R.id.changeInforEmail);

        buttonSave = findViewById(R.id.changeInforButtonSave);
        globalVariable = (GlobalVariable) this.getApplication();

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        authUser = globalVariable.getAuthUser();
        buttonBack = findViewById(R.id.changeInforButtonGoBack);
    }

    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(ChangeInformationViewModel.class);

        viewModel.getAnimation().observe(this, aBoolean -> {
            if(aBoolean)
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });

        dialog.announce();
        viewModel.getResponse().observe(this, response->{
            int result = response.getResult();

            if( result == 1)
            {
                dialog.show(R.string.success,
                        getString(R.string.change_information_successfully),
                        R.drawable.ic_check);
            }
            else
            {
                String message = response.getMsg();
                dialog.show(R.string.fail, message, R.drawable.ic_close);
            }
        });
        dialog.btnOK.setOnClickListener(view->dialog.close());
    }

    private void setupScreen()
    {
        String email = authUser.getEmail();
        String firstName = authUser.getFirstName();
        String lastName = authUser.getLastName();
        String phone = authUser.getPhone();
        String address = authUser.getAddress();

        txtEmail.setText(email);
        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtPhone.setText(phone);
        txtAddress.setText(address);
    }

    private void setupEvent()
    {
        buttonSave.setOnClickListener(view->{
            String email = txtEmail.getText().toString();
            String firstName = txtFirstName.getText().toString();
            String lastName = txtLastName.getText().toString();
            String phone =txtPhone.getText().toString();
            String address = txtAddress.getText().toString();


            headers = globalVariable.getHeaders();
            viewModel.changeInformation(headers, email, firstName, lastName, phone, address);
        });

        buttonBack.setOnClickListener(view->{
            finish();
        });
    }
}