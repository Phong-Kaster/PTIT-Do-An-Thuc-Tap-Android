package com.example.doanthuctap.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.personality.OrderInformationActivity;
import com.example.doanthuctap.helper.Beautifier;

/**
 * this activity shows one order placed successfully
 */
public class CartSuccessActivity extends AppCompatActivity {

    private String total = "";
    private String description = "";
    private String orderId ="";
    private AppCompatButton buttonWatchOrder;
    private TextView txtDeliveryDate;
    private TextView txtTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_success);

        /*get data sent from Cart Checkout Activity*/
        total = getIntent().getStringExtra("total");
        total = Beautifier.formatNumber( Integer.parseInt(total) ) + "đ";
        orderId = getIntent().getStringExtra("orderId");
        description = getIntent().getStringExtra("description");

        setupComponent();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonWatchOrder = findViewById(R.id.buttonWatchOrder);
        txtDeliveryDate = findViewById(R.id.cartSuccessDeliveryDate);
        txtTotal = findViewById(R.id.cartSuccessTotal);
    }

    private void setupEvent()
    {
        buttonWatchOrder.setOnClickListener(view->{
            Intent intent = new Intent(this, OrderInformationActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        });

        txtDeliveryDate.setText(description);
        txtTotal.setText(total);
    }
}