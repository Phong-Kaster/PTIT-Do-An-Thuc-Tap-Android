package com.example.doanthuctap.activity.admin.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.viewModel.admin.order.AdminOrderCreateActivityViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * this activity is used to create a new order
 */
public class AdminOrderCreateActivity extends AppCompatActivity {

    /*RECEIVER*/
    private EditText txtReceiverName;
    private EditText txtReceiverPhone;
    private EditText txtReceiverAddress;

    /*DESCRIPTION*/
    private EditText txtDescription;


    /*BUTTONS*/
    private AppCompatButton buttonSave;
    private AppCompatButton buttonOrderContent;
    private ImageButton buttonBack;

    private LoadingScreen loadingScreen;
    private Dialog dialog;
    private GlobalVariable globalVariable;

    private AdminOrderCreateActivityViewModel viewModel;
    private Map<String, String> headers = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_create);

        setupComponent();
        setupViewModel();
        setupEvent();
    }


    private void setupComponent()
    {
        /*RECEIVER*/
        txtReceiverName = findViewById(R.id.adminOrderCreateInforReceiverName);
        txtReceiverPhone = findViewById(R.id.adminOrderCreateInforReceiverPhone);
        txtReceiverAddress = findViewById(R.id.adminOrderCreateInforReceiverAddress);


        /*DESCRIPTION*/
        txtDescription = findViewById(R.id.adminOrderCreateInforDescription);


        /*BUTTONS*/
        buttonSave = findViewById(R.id.adminOrderCreateInforButtonSave);
        buttonBack = findViewById(R.id.adminOrderCreateInforButtonBack);

        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);
        globalVariable = (GlobalVariable) this.getApplication();
        headers = globalVariable.getHeaders();
    }


    private void setupViewModel()
    {
        /*Step 1 - declare local variable*/
        viewModel = new ViewModelProvider(this).get(AdminOrderCreateActivityViewModel.class);
        viewModel.instantiate();
    }


    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());
        
        /*BUTTON SAVE*/
        buttonSave.setOnClickListener(view -> {
            /*Step 1 - get value*/
            String receiverName = txtReceiverName.getText().toString();
            String receiverPhone = txtReceiverPhone.getText().toString();
            String receiverAddress = txtReceiverAddress.getText().toString();
            String description = txtDescription.getText().toString();


            /*Step 2 - send request*/
            viewModel.createOrder(headers, receiverPhone, receiverAddress, receiverName, description );


            /*Step 3 - listen for responding*/
            dialog.announce();
            viewModel.getCreateOrderResponse().observe(this, response->{
                int result = response.getResult();
                if( result == 1)
                {
                    dialog.show(R.string.success, getString(R.string.create_order_successfully), R.drawable.ic_check);
                }
                else
                {
                    String msg = response.getMsg();
                    dialog.show(R.string.fail, msg, R.drawable.ic_close);
                }
            });
            dialog.btnOK.setOnClickListener(view1->dialog.close());

            /*Step 4 - get animation*/
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
        });
    }
}