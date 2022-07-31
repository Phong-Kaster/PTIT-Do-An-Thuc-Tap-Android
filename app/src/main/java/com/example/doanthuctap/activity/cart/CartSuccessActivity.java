package com.example.doanthuctap.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Beautifier;

/**
 * this activity shows one order placed successfully
 */
public class CartSuccessActivity extends AppCompatActivity {

    private String total = "";
    private String description = "";

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
            Toast.makeText(this, "Xem đơn hàng", Toast.LENGTH_SHORT).show();
        });

        txtDeliveryDate.setText(description);
        txtTotal.setText(total);
    }
}