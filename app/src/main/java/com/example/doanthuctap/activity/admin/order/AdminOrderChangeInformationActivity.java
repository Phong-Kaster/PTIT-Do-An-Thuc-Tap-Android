package com.example.doanthuctap.activity.admin.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.doanthuctap.R;
import com.example.doanthuctap.arrayadapter.OrderStatusArrayAdapter;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Option;
import com.example.doanthuctap.model.Order;
import com.example.doanthuctap.viewModel.admin.order.AdminOrderChangeInformationViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * this activity is used to change order information
 */
public class AdminOrderChangeInformationActivity extends AppCompatActivity {

    /*RECEIVER*/
    private EditText txtReceiverName;
    private EditText txtReceiverPhone;
    private EditText txtReceiverAddress;

    /*DESCRIPTION*/
    private EditText txtDescription;

    /*STATUS*/
    private Spinner spinnerStatus;

    /*TOTAL AMOUNT*/
    private EditText txtTotalAmount;

    /*CREATE AT*/
    private  EditText txtCreateAt;

    /*UPDATE AT*/
    private EditText txtUpdateAt;

    /*BUTTONS*/
    private AppCompatButton buttonSave;
    private AppCompatButton buttonOrderContent;
    private ImageButton buttonBack;

    /*spinner order status*/
    private List<Option> orderStatusOptions = new ArrayList<>();
    private Map<String, String> orderStatusMap = new HashMap<>();
    private String orderStatus;

    private LoadingScreen loadingScreen;
    private Dialog dialog;
    /*view model*/
    private String orderId;
    private AdminOrderChangeInformationViewModel viewModel;

    private Map<String, String> header = new HashMap<>();
    private GlobalVariable globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_change_information);

        orderId = getIntent().getStringExtra("orderId");

        setupComponent();
        setupViewModel();
        setupEvent();
        setupOrderStatusSpinner();
    }

    private void setupComponent()
    {
        /*RECEIVER*/
        txtReceiverName = findViewById(R.id.adminOrderChangeInforReceiverName);
        txtReceiverPhone = findViewById(R.id.adminOrderChangeInforReceiverPhone);
        txtReceiverAddress = findViewById(R.id.adminOrderChangeInforReceiverAddress);

        /*DESCRIPTION*/
        txtDescription = findViewById(R.id.adminOrderChangeInforDescription);

        /*STATUS*/
        spinnerStatus = findViewById(R.id.adminOrderChangeInforStatus);

        /*TOTAL AMOUNT*/
        txtTotalAmount = findViewById(R.id.adminOrderChangeInforTotalAmount);

        /*CREATE AT*/
        txtCreateAt = findViewById(R.id.adminOrderChangeInforCreateAt);

        /*UPDATE*/
        txtUpdateAt = findViewById(R.id.adminOrderChangeInforUpdateAt);

        buttonSave = findViewById(R.id.adminOrderChangeInforButtonSave);
        buttonOrderContent = findViewById(R.id.adminOrderChangeInforButtonOrderContent);
        buttonBack = findViewById(R.id.adminOrderChangeInforButtonBack);

        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);
        globalVariable = (GlobalVariable) this.getApplication();
    }

    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(AdminOrderChangeInformationViewModel.class);
        viewModel.instantiate();

        /*Step 2 - query*/
        header = globalVariable.getHeaders();
        viewModel.getOrderById(header, orderId);

        /*Step 3 - listen response*/
        /*get animation*/
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


        viewModel.getOrderByIdResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {
                Order order = response.getData();
                setupScreen(order);
            }
        });


    }

    private void setupOrderStatusSpinner()
    {
        /*Step 1 - create map<String, String>*/
        orderStatusMap.put(getString(R.string.to_pay), "processing");
        orderStatusMap.put(getString(R.string.verified), "verified");
        orderStatusMap.put(getString(R.string.packed), "packed");
        orderStatusMap.put(getString(R.string.being_transported), "being transported");
        orderStatusMap.put(getString(R.string.done), "delivered");
        orderStatusMap.put(getString(R.string.cancel), "cancel");


        /*Step 2 - find a corresponding value bases on map<String, String>*/
        orderStatusOptions = Option.getOrderStatus(this);
        OrderStatusArrayAdapter adapter = new OrderStatusArrayAdapter(this, orderStatusOptions);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                /*get order status from spinner*/
                String name = orderStatusOptions.get(position).getName();
                orderStatus = orderStatusMap.get(name);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent)
            {

            }
        });
    }

    private void setupScreen(Order order)
    {
        /*Step 1 - declare*/
        String receiverName = order.getReceiverName();
        String receiverPhone = order.getReceiverPhone();
        String receiverAddress = order.getReceiverAddress();

        String description = order.getDescription();
        String status = order.getStatus().length()>0 ? order.getStatus() : "processing" ;
        String createAt = order.getCreateAt();

        String updateAt = order.getUpdateAt();
        String total = Beautifier.formatNumber(order.getTotal()) + "đ";


        /*Step 2 - set up value*/
        Map<String, Integer> statusPosition = new HashMap<>();
        statusPosition.put("processing", 0);
        statusPosition.put("verified", 1);
        statusPosition.put("packed", 2);
        statusPosition.put("being transported", 3);
        statusPosition.put("delivered", 4);
        statusPosition.put("cancel", 5);

        int position = statusPosition.get(status);
        spinnerStatus.setSelection(position);

        txtReceiverAddress.setText(receiverAddress);
        txtReceiverPhone.setText(receiverPhone);
        txtReceiverName.setText(receiverName);

        txtDescription.setText(description);

        txtTotalAmount.setText(total);
        txtCreateAt.setText(createAt);
        txtUpdateAt.setText(updateAt);
    }

    private void setupEvent()
    {
        /*BUTTON SAVE*/
        buttonSave.setOnClickListener(view->{
            /*Step 1 - get value*/
            String receiverName     = txtReceiverName.getText().toString();
            String receiverPhone    = txtReceiverPhone.getText().toString();
            String receiverAddress  = txtReceiverAddress.getText().toString();

            String description      = txtDescription.getText().toString();
            orderStatus = orderStatus.toLowerCase().trim();// get from spinner
            orderId = orderId.toLowerCase().trim();


            /*Step 2 - send request*/
            header = globalVariable.getHeaders();
            viewModel.modifyOrderById(header, orderId,
                    receiverPhone, receiverAddress, receiverName,
                    orderStatus, description);

            /*Step 3 - listen for responding data*/
            dialog.announce();
            viewModel.getModifyOrderResponse().observe(this, response->{

                int result = response.getResult();

                if( result == 1)
                {
                    dialog.show(R.string.success, getString(R.string.change_order_information_successfully),
                            R.drawable.ic_check);
                }
                else
                {
                    String msg = response.getMsg();
                    dialog.show(R.string.fail, msg,
                            R.drawable.ic_close);
                }
            });
            dialog.btnOK.setOnClickListener(view1->dialog.close());
        });


        /*BUTTON ORDER CONTENT*/
        buttonOrderContent.setOnClickListener(view->{
            Intent intent = new Intent(this, AdminOrderChangeContentActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        });


        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());
    }
}