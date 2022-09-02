package com.example.doanthuctap.activity.admin.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.AdminProductsModifyOrderContentRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.admin.order.AdminOrderChangeContentViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddProductToCartActivity extends AppCompatActivity
        implements AdminProductsModifyOrderContentRecyclerViewAdapter.callbacks  {

    private RecyclerView recyclerView;
    private AdminOrderChangeContentViewModel viewModel;
    private ImageButton buttonBack;

    private AdminProductsModifyOrderContentRecyclerViewAdapter adapter;
    private String orderId = "";
    private Map<String, String> headers = new HashMap<>();
    private GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product_to_cart);

        orderId = getIntent().getStringExtra("orderId").trim();

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        recyclerView = findViewById(R.id.adminAddProductToCartRecyclerView);
        globalVariable = (GlobalVariable) this.getApplication();
        buttonBack = findViewById(R.id.adminAddProductToCartButtonBack);
    }

    private void setupEvent()
    {
        buttonBack.setOnClickListener(view->finish());
    }

    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(AdminOrderChangeContentViewModel.class);
        viewModel.instantiate();

        Map<String, String> parameter = new HashMap<>();
        viewModel.getProducts(parameter);


        viewModel.getProductsResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {
                List<ProductClient> products = response.getData();
                setupRecyclerView(products);
            }
        });
    }

    private void setupRecyclerView(List<ProductClient> products)
    {
        adapter = new AdminProductsModifyOrderContentRecyclerViewAdapter(this, products, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this,2,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void addProductIntoOrder(int productId, String quantity) {
        String productID = String.valueOf(productId);
        headers = globalVariable.getHeaders();
        viewModel.modifyOrderContent(headers, orderId, productID, quantity);

        Intent intent = new Intent();
        viewModel.getModifyOrderContentResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {

                setResult(RESULT_OK, intent);
            }
            else
            {
                setResult(RESULT_CANCELED, intent);
            }
            finish();
        });

    }
}