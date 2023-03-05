package com.example.doanthuctap.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.recyclerviewadapter.CheckoutOrderContentRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.cart.CartCheckoutViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this activity shows order information before placing the order
 */
public class CartCheckoutActivity extends AppCompatActivity {


    private CheckoutOrderContentRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private CartCheckoutViewModel viewModel;

    private LoadingScreen loadingScreen;
    private Map<String, String> headers = new HashMap<>();


    private GlobalVariable globalVariable;
    private Dialog dialog;

    private ImageButton buttonBack;
    private TextView totalAmount;


    private int merchandiseTotal = 0;
    private int shippingFee = 0;
    private int total = 0;

    private TextView txtMerchandiseTotal;// 3 fields in Hoa don section
    private TextView txtShippingFee;
    private TextView txtTotal;
    private TextView txtShippingDate;

    private TextView txtReceiverName;
    private TextView txtReceiverAddress;
    private TextView txtReceiverPhone;

    private RadioGroup radioGroupShippingOptions;
    private LinearLayout receiverInformation;

    /*delivery address*/
    private String orderId = "";
    private String receiverName = "";
    private String receiverAddress = "";
    private String receiverPhone = "";
    private String description = "";
    private String orderStatus = "";


    /*button place order*/
    private AppCompatButton buttonPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        recyclerView = findViewById(R.id.checkoutOrderContent);
        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);


        globalVariable = (GlobalVariable)this.getApplication();
        buttonBack = findViewById(R.id.checkoutActivityButtonGoBack);
        totalAmount = findViewById(R.id.checkoutActivityTotalAmount);


        txtTotal = findViewById(R.id.checkoutTotal);
        txtMerchandiseTotal = findViewById(R.id.checkoutMerchandiseSubtotal);
        txtShippingFee = findViewById(R.id.checkoutShippingFee);


        radioGroupShippingOptions = findViewById(R.id.checkoutActivityShippingOptions);
        txtShippingDate = findViewById(R.id.checkoutShippingDate);
        receiverInformation = findViewById(R.id.checkoutReceiverInformation);

        txtReceiverName = findViewById(R.id.checkoutReceiverName);
        txtReceiverPhone = findViewById(R.id.checkoutReceiverPhone);
        txtReceiverAddress = findViewById(R.id.checkoutReceiverAddress);

        buttonPlaceOrder = findViewById(R.id.checkoutActivityButtonPlaceOrder);
    }

    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(CartCheckoutViewModel.class);
        viewModel.instanticate();


        /*Step 2 - get data*/
        headers = globalVariable.getHeaders();
        viewModel.getLatestOrderInformation(headers);


        /*Step 3 - recycler view*/
        viewModel.getLatestOrder().observe(this, response -> {
            int result = response.getResult();
            String msg = response.getMsg();
            if( result == 1)
            {
                List<GetLatestOrderResponseContent> contents = response.getContent();
                String totalAmountValue = Beautifier.formatNumber(response.getData().getTotal()) + "đ";
                orderId = response.getData().getId();

                /*set up delivery address*/
                /*
                 * there are 6 important fields in order information.
                 * However, receiverName | receiverPhone | receiverAddress | orderId | orderStatus are gotten in setupViewModel()
                 * now, there are description and total amount left
                 * we have to set up default value for them
                 */
                receiverAddress = response.getData().getReceiverAddress().toString().trim();
                receiverName = response.getData().getReceiverName().trim();
                receiverPhone = response.getData().getReceiverPhone().toString().trim();
                orderStatus = response.getData().getStatus();
                total = response.getData().getTotal();


                /*generate a default description*/
                String input = getString(R.string.economical);
                description = Beautifier.generateShippingDate(input, this);


                /*set value up to view*/
                txtReceiverAddress.setText( receiverAddress  );
                txtReceiverName.setText( receiverName );
                txtReceiverPhone.setText( receiverPhone );

                /*set up invoice - payment*/
                merchandiseTotal = response.getData().getTotal();
                txtMerchandiseTotal.setText(totalAmountValue);
                totalAmount.setText(totalAmountValue);
                txtTotal.setText(totalAmountValue);
                setupRecyclerView(contents);
            }
            else
            {
                dialog.announce();
                dialog.show(R.string.attention, msg, R.drawable.ic_close );
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });


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
    }




    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*RADIO GROUP*/
        /*RADIO GROUP - Step 1 - generate a default description*/
        String input = this.getString(R.string.economical);
        String deliveryMessage = Beautifier.generateShippingDate( input, this );
        txtShippingDate.setText( deliveryMessage );


        /*listen radio button clicked to generate corresponding message*/
        radioGroupShippingOptions.setOnCheckedChangeListener((radioGroup, position) -> {

            /*RADIO GROUP - Step 2 - we have to know which radio button is clicked !*/
            AppCompatRadioButton radioButton = radioGroup.findViewById(position);
            String radioButtonMessage = radioButton.getText().toString().trim();


            /*RADIO GROUP - Step 3 - generate description based on radio button clicked*/
            String content =  Beautifier.generateShippingDate(radioButtonMessage, this);
            txtShippingDate.setText(content);
            description = content;// description: Giao hàng trước 11h ngày mai


            /*RADIO GROUP - Step 4 - calculate shipping fee*/
            String economical = getString(R.string.economical);// tiết kiệm - economical
            String standard = getString(R.string.standard);// tiêu chuẩn - standard
            String rapid = getString(R.string.rapid);// siêu tốc - rapid

            if( radioButtonMessage.equals(economical) )
            {
                shippingFee = 0;
            }
            else if( radioButtonMessage.equals(standard))
            {
                shippingFee = 20000;
            }
            else if( radioButtonMessage.equals(rapid))
            {
                shippingFee = 50000;
            }
            else
            {
                shippingFee = 0;
            }


            /*Step 4 - show up*/
            total = merchandiseTotal + shippingFee;
            txtShippingFee.setText( Beautifier.formatNumber(shippingFee) + "đ" );
            totalAmount.setText( Beautifier.formatNumber(total) + "đ" );
            txtTotal.setText( Beautifier.formatNumber(total) + "đ" );
        });


        /*CLICK LINEAR LAYOUT DELIVERY ADDRESS TO CHANGE RECEIVER INFORMATION*/
        receiverInformation.setOnClickListener( view-> {
            Intent intent = new Intent(this, ModifyReceiverActivity.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("receiverAddress", receiverAddress );
            intent.putExtra("receiverPhone", receiverPhone );
            intent.putExtra("receiverName", receiverName );
            intent.putExtra("description", description );
            intent.putExtra("total", String.valueOf(total));
            startActivity(intent);
        });

        /*BUTTON PLACE ORDER*/
        dialog.confirm();
        buttonPlaceOrder.setOnClickListener(view -> {

            if( globalVariable.getAuthUser().getRole().equals("admin") )
            {
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.you_are_an_admin), R.drawable.ic_close);
                dialog.btnOK.setOnClickListener(view1->dialog.close());
                return;
            }

            boolean flag = authenticateReceiverInformation();
            if( !flag )
            {
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.invalid_receiver_information), R.drawable.ic_close);
                dialog.btnOK.setOnClickListener(view1->dialog.close());
                return;
            }

            dialog.show(R.string.attention, getString(R.string.are_you_sure_about_placing_order), R.drawable.ic_info);
            /*DIALOG BUTTON CANCEL*/
            dialog.btnCancel.setOnClickListener(view2-> dialog.close());
            /*DIALOG BUTTON OK*/
            dialog.btnOK.setOnClickListener(view1->
            {
                /*Step 1 - send a request to modify order information*/
                dialog.close();
                headers = globalVariable.getHeaders();
                viewModel.modifyOrderInformation(headers,orderId, receiverPhone, receiverAddress, receiverName, description,
                        String.valueOf(total) );
                viewModel.confirmOrder(headers, orderId, orderStatus);



                /*Step 2 - modify order information successfully then confirm order*/
                viewModel.getConfirmOrderResponse().observe(CartCheckoutActivity.this, response ->
                {
                    int result = response.getResult();
                    if( result == 0)
                    {
                        dialog.announce();
                        dialog.show(R.string.fail, response.getMsg(), R.drawable.ic_close);
                        dialog.btnOK.setOnClickListener(view2->dialog.close());
                    }
                    else
                    {
                        Intent intent = new Intent(CartCheckoutActivity.this,
                                CartSuccessActivity.class);
                        intent.putExtra("total", String.valueOf(total));
                        intent.putExtra("description", description);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                        finish();

                    }
                });
            });

        });


    }

    /**
     * check receiver information
     * @return boolean return true if information is valid
     * return false for the opposite result
     */
    private boolean authenticateReceiverInformation() {
        if( receiverAddress.isEmpty() || receiverPhone.isEmpty() || receiverName.isEmpty() )
        {
            return false;
        }
        return true;
    }

    private void setupRecyclerView(List<GetLatestOrderResponseContent> objects)
    {
        adapter = new CheckoutOrderContentRecyclerViewAdapter(this, objects);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void confirmOrder(Map<String, String> headers, String orderId, String orderStatus)
    {
        viewModel.confirmOrder(headers, orderId, orderStatus );

        viewModel.getConfirmOrderResponse().observe(CartCheckoutActivity.this, response ->
        {
            int result = response.getResult();
            if( result == 0)
            {
                dialog.announce();
                dialog.show(R.string.fail, response.getMsg(), R.drawable.ic_close);
                dialog.btnOK.setOnClickListener(view2->dialog.close());
            }
            else
            {
                Intent intent = new Intent(CartCheckoutActivity.this,
                        CartSuccessActivity.class);
                intent.putExtra("total", String.valueOf(total));
                intent.putExtra("description", description);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                finish();

            }
        });
        /*DIALOG BUTTON CANCEL*/
        dialog.btnCancel.setOnClickListener(view1-> dialog.close());
    }
}