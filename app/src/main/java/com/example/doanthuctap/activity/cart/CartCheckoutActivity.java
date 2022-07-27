package com.example.doanthuctap.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.GetLatestOrderResponse;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.recyclerviewadapter.CheckoutOrderContentRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.cart.CartCheckoutViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartCheckoutActivity extends AppCompatActivity {

    private CheckoutOrderContentRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private CartCheckoutViewModel viewModel;

    private LoadingScreen loadingScreen;
    private Map<String, String> headers = new HashMap<>();
    private GlobalVariable globalVariable;

    private ImageButton buttonBack;
    private TextView totalAmount;


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

        globalVariable = (GlobalVariable)this.getApplication();
        buttonBack = findViewById(R.id.checkoutActivityButtonGoBack);

        totalAmount = findViewById(R.id.checkoutActivityTotalAmount);
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
        viewModel.getLatestOrder().observe(this, new Observer<GetLatestOrderResponse>() {
            @Override
            public void onChanged(GetLatestOrderResponse getLatestOrderResponse) {
                int result = getLatestOrderResponse.getResult();
                if( result == 1)
                {
                    List<GetLatestOrderResponseContent> contents = getLatestOrderResponse.getContent();
                    String totalAmountValue = Beautifier.formatNumber(getLatestOrderResponse.getData().getTotal()) + "đ";


                    totalAmount.setText(totalAmountValue);
                    setupRecyclerView(contents);
                }
            }
        });
    }


    private void setupEvent()
    {
        buttonBack.setOnClickListener(view->finish());
    }

    private void setupRecyclerView(List<GetLatestOrderResponseContent> objects)
    {
        adapter = new CheckoutOrderContentRecyclerViewAdapter(this, objects);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}