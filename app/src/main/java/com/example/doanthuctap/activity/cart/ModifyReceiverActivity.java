package com.example.doanthuctap.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.ModifyReceiverResponse;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.viewModel.cart.ModifyReceiverViewModel;

import java.util.HashMap;
import java.util.Map;

public class ModifyReceiverActivity extends AppCompatActivity {

    private ImageButton buttonBack;
    private AppCompatButton buttonSave;

    private EditText txtReceiverName;
    private EditText txtReceiverPhone;
    private EditText txtReceiverAddress;

    private ModifyReceiverViewModel viewModel;
    /*delivery address*/
    private String orderId = "";
    private String receiverName = "";
    private String receiverAddress = "";
    private String receiverPhone = "";
    private String description = "";
    private String total = "";
    private LoadingScreen loadingScreen;
    private Dialog dialog;
    private Map<String, String> headers = new HashMap<>();
    private GlobalVariable globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_receiver);

        orderId = getIntent().getStringExtra("orderId");
        receiverAddress = getIntent().getStringExtra("receiverAddress");
        receiverPhone = getIntent().getStringExtra("receiverPhone");
        receiverName = getIntent().getStringExtra("receiverName");
        description = getIntent().getStringExtra("description");
        total = getIntent().getStringExtra("total");

        setupComponent();
        setupViewModel();
        setupScreen();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonBack = findViewById(R.id.modifyReceiverButtonGoBack);
        buttonSave = findViewById(R.id.modifyReceiverButtonSave);

        txtReceiverAddress = findViewById(R.id.modifyReceiverAddress);
        txtReceiverName = findViewById(R.id.modifyReceiverName);
        txtReceiverPhone = findViewById(R.id.modifyReceiverPhone);

        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);

        globalVariable = (GlobalVariable) this.getApplication();
        headers = globalVariable.getHeaders();
    }


    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(ModifyReceiverViewModel.class);
        viewModel.instanticate();

        /* get response*/
        dialog.announce();
        viewModel.getResponse().observe(this, response -> {
            int result = response.getResult();
            String msg = response.getMsg();
            if( result == 1)
            {

                dialog.show(R.string.success, msg, R.drawable.ic_info );

            }
            else
            {
                dialog.show(R.string.fail, msg, R.drawable.ic_info);
            }
        });
        dialog.btnOK.setOnClickListener(view->dialog.close());

        /* get animation*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean)
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });
    }


    private void setupScreen()
    {
        txtReceiverAddress.setText( receiverAddress );
        txtReceiverPhone.setText( receiverPhone );
        txtReceiverName.setText( receiverName );
    }



    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*BUTTON SAVE*/
        buttonSave.setOnClickListener(view->{
            String receiverName = txtReceiverName.getText().toString();
            String receiverAddress = txtReceiverAddress.getText().toString();
            String receiverPhone = txtReceiverPhone.getText().toString();


            viewModel.modifyReceiverOrder(headers,orderId, receiverPhone, receiverAddress,
                    receiverName, description, total);


            /* get response*/
            dialog.announce();
            viewModel.getResponse().observe(this, response -> {
                int result = response.getResult();
                String msg = response.getMsg();
                if( result == 1)
                {

                    dialog.show(R.string.success, msg, R.drawable.ic_check );

                }
                else
                {
                    dialog.show(R.string.fail, msg, R.drawable.ic_close);
                }
            });
            dialog.btnOK.setOnClickListener(view1->dialog.close());

//            /* get animation*/
//            viewModel.getAnimation().observe(this, aBoolean -> {
//                if( aBoolean)
//                {
//                    loadingScreen.start();
//                }
//                else
//                {
//                    loadingScreen.stop();
//                }
//            });
        });

    }
}