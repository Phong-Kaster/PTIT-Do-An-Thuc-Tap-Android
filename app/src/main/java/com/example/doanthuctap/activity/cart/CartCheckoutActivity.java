package com.example.doanthuctap.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.recyclerviewadapter.CheckoutOrderContentRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.cart.CartCheckoutViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartCheckoutActivity extends AppCompatActivity {

    final static int shippingEconomical = 2131231333;
    final static int shippingStandard = 2131231334;
    final static int shippingRapid = 2131231335;

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


    private RadioGroup radioGroupShippingOptions;
    private LinearLayout receiverInformation;
    private String orderId = "";
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
        viewModel.getLatestOrder().observe(this, getLatestOrderResponse -> {
            int result = getLatestOrderResponse.getResult();
            String msg = getLatestOrderResponse.getMsg();
            if( result == 1)
            {
                List<GetLatestOrderResponseContent> contents = getLatestOrderResponse.getContent();
                String totalAmountValue = Beautifier.formatNumber(getLatestOrderResponse.getData().getTotal()) + "đ";
                orderId = getLatestOrderResponse.getData().getId();


                merchandiseTotal = getLatestOrderResponse.getData().getTotal();
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


    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*RADIO GROUP*/
        radioGroupShippingOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                String content =  Beautifier.generateShippingDate(position);
                txtShippingDate.setText(content);

                /*depend on position to determine shipping fee*/
                switch (position) {
                    case shippingEconomical:
                        shippingFee = 0;
                        break;
                    case shippingStandard:
                        shippingFee = 20000;
                        break;
                    case shippingRapid:
                        shippingFee = 50000;
                        break;
                    default:
                        shippingFee = 0;
                        break;
                }
                total = merchandiseTotal + shippingFee;
                txtShippingFee.setText( Beautifier.formatNumber(shippingFee) + "đ" );
                totalAmount.setText( Beautifier.formatNumber(total) + "đ" );
                txtTotal.setText( Beautifier.formatNumber(total) + "đ" );
            }
        });


        /*CLICK LINEAR LAYOUT DELIVERY ADDRESS TO CHANGE RECEIVER INFORMATION*/
        receiverInformation.setOnClickListener( view-> {
            Intent intent = new Intent(this, ModifyReceiverActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        });
    }

    private void setupRecyclerView(List<GetLatestOrderResponseContent> objects)
    {
        adapter = new CheckoutOrderContentRecyclerViewAdapter(this, objects);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}