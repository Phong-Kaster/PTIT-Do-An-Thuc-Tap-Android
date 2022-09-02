package com.example.doanthuctap.activity.admin.product;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.AdminProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.admin.product.AdminProductsActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminProductsActivity extends AppCompatActivity {

    private ImageButton buttonBack;
    private ImageButton buttonFilter;

    private RecyclerView recyclerView;
    private androidx.appcompat.widget.SearchView searchView;

    private AdminProductsActivityViewModel viewModel;
    private AdminProductsRecyclerViewAdapter adapter;

    /*auth*/
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private GlobalVariable globalVariable;
    private LoadingScreen loadingScreen;

    private TextView txtCanNotFindMatchProducts;
    private TextView txtGoBack;
    private FloatingActionButton buttonCreate;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonBack = findViewById(R.id.adminProductsButtonGoBack);
        buttonFilter = findViewById(R.id.adminProductsButtonFilter);
        recyclerView = findViewById(R.id.adminProductsRecyclerView);
        searchView = findViewById(R.id.adminProductsSearchView);

        globalVariable = (GlobalVariable) this.getApplication();
        loadingScreen = new LoadingScreen(this);

        txtCanNotFindMatchProducts = findViewById(R.id.txtCanNotFindMatchProducts);
        txtGoBack = findViewById(R.id.txtGoBack);

        buttonCreate = findViewById(R.id.adminProductsButtonAdd);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void setupViewModel()
    {
        /*Step 1 - declare variable global*/
        viewModel = new ViewModelProvider(this).get(AdminProductsActivityViewModel.class);
        viewModel.instantiate();

        /*Step 2 - view model*/
        headers = globalVariable.getHeaders();
        viewModel.getProducts(headers, parameters);

        /*Step 3 - animation*/
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

        /*Step 4 - products*/
        viewModel.getProductsResponse().observe(this,  response->{
            int result = response.getResult();
            List<ProductClient> products = response.getData();
            if( result == 1 && products.size() > 0)
            {
                txtCanNotFindMatchProducts.setVisibility(View.GONE);
                txtGoBack.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                setupRecyclerView(products);
            }
            else
            {
                txtCanNotFindMatchProducts.setVisibility(View.VISIBLE);
                txtGoBack.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

        ActivityResultLauncher<Intent> startSearchActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int status = result.getResultCode();
            if( status == RESULT_OK)
            {
                Intent data = result.getData();
                assert data != null;

                parameters.put("search", data.getStringExtra("search"));
                parameters.put("manufacturer",data.getStringExtra("manufacturer") );
                parameters.put("priceFrom",data.getStringExtra("priceFrom") );
                parameters.put("priceTo",data.getStringExtra("priceTo") );
                parameters.put("screenSize",data.getStringExtra("screenSize") );
                parameters.put("cpu",data.getStringExtra("cpu") );
                parameters.put("graphicCard", data.getStringExtra("graphicCard") );
                parameters.put("demand",data.getStringExtra("demand") );
            }
            viewModel.getProducts(headers, parameters);
        }
    });

    @SuppressLint("NotifyDataSetChanged")
    private  void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*BUTTON FILTER*/
        buttonFilter.setOnClickListener(view->{
            Intent intent = new Intent(this, AdminProductsSearchActivity.class);
            startSearchActivityForResult.launch(intent);
        });

        /* SEARCH VIEW */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*get text users write down to query*/
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {

                parameters.put("search", query);
                viewModel.getProducts(headers, parameters);
                adapter.notifyDataSetChanged();

                return false;
            }

            /*if users click on close button - newText = "" then query again */
            @Override
            public boolean onQueryTextChange(String newText) {
                if( newText.equals(""))
                {
                    parameters.put("search", newText);
                    viewModel.getProducts(headers, parameters);
                    searchView.clearFocus();
                }
                return false;
            }
        });

        /*TEXT GO BACK*/
        txtGoBack.setOnClickListener(view->{
            parameters.clear();
            viewModel.getProducts(headers, parameters);
        });

        /*BUTTON CREATE*/
        buttonCreate.setOnClickListener(view->{
            Intent intent = new Intent(this, AdminProductCreateActivity.class);
            startActivity(intent);
        });

        /*SWIPE REFRESH LAYOUT*/
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getProducts(headers, parameters);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void setupRecyclerView(List<ProductClient> productClients)
    {
        adapter = new AdminProductsRecyclerViewAdapter(this, productClients);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
}