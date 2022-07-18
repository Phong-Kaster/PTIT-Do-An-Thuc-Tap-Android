package com.example.doanthuctap.activity.search;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.search.SearchViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private ImageButton buttonBack;
    private ImageButton buttonCart;
    private ImageButton buttonFilter;

    private RecyclerView recyclerView;
    private SearchViewModel viewModel;
    private ProductsRecyclerViewAdapter adapter;

    private LoadingScreen loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupComponent();
        setupViewModel();
        setupEvent();
    }

    /**
     * @author Phong-Kaster
     * mapping components with XML layout
     */
    private void setupComponent(){
        buttonBack = findViewById(R.id.searchActivityButtonGoBack);
        buttonCart = findViewById(R.id.searchActivityButtonCart);
        buttonFilter = findViewById(R.id.searchActivityButtonFilter);

        recyclerView = findViewById(R.id.searchActivityRecyclerView);
        loadingScreen = new LoadingScreen(this);
    }

    /**
     * @author Phong-Kaster
     * set up view model
     */
    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.instantiate();

        Map<String, String> parameters =new HashMap<>();
        viewModel.getProducts(parameters);
        /*get products*/
        viewModel.getObjects().observe(this, productsResponse -> {
            int result = productsResponse.getResult();
            if(result == 1){
                List<ProductClient> list = productsResponse.getData();
                setupRecyclerView(list);
            }
            else{
                Toast.makeText(SearchActivity.this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
            }
        });

        /*trigger animation*/
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

    /**
     * @author Phong-Kaster
     * set up recycler view
     * @param objects
     */
    private void setupRecyclerView(List<ProductClient> objects)
    {
        adapter = new ProductsRecyclerViewAdapter(SearchActivity.this, objects);
        GridLayoutManager manager = new GridLayoutManager(SearchActivity.this,2,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private void setupEvent(){
        /************** BUTTON GO BACK  **************/
        buttonBack.setOnClickListener(view->{
            finish();
        });


        /************** BUTTON CART  **************/
        buttonCart.setOnClickListener(view->{
            Toast.makeText(this, "Open Cart", Toast.LENGTH_SHORT ).show();
        });


        /************** BUTTON FILTER  **************/
        buttonFilter.setOnClickListener(view -> {

        });
    }
}