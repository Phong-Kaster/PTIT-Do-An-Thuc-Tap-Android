package com.example.doanthuctap.activity.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.recyclerviewadapter.ProductsRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.search.SearchViewModel;

import java.util.ArrayList;
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
    private SearchView searchView;


    private final Map<String, String> parameters = new HashMap<>();
    private final List<ProductClient> objects = new ArrayList<>();
    private String demand = "";// this "demand" keyword maybe send from homeFragment to this activity.
    private String search = "";


    /*this private final variable is used to start another activity for result*/
    private final ActivityResultLauncher<Intent> startAdvancedSearchActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;

                    parameters.put("manufacturer",data.getStringExtra("manufacturer") );
                    parameters.put("priceFrom",data.getStringExtra("priceFrom") );
                    parameters.put("priceTo",data.getStringExtra("priceTo") );
                    parameters.put("screenSize",data.getStringExtra("screenSize") );
                    parameters.put("cpu",data.getStringExtra("cpu") );
                    parameters.put("graphicCard", data.getStringExtra("graphicCard") );
                    parameters.put("demand",data.getStringExtra("demand") );

                    viewModel.getProducts(parameters);

                    //Toast.makeText(this, data.getStringExtra("hallo"), Toast.LENGTH_SHORT).show();
                }
                else if( result.getResultCode() == Activity.RESULT_CANCELED){
                    Toast.makeText(this, R.string.complete, Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*this "demand" keyword maybe send from homeFragment to this activity.*/
        demand = getIntent().getStringExtra("demand").trim();
        search = getIntent().getStringExtra("search").trim();

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
        searchView = findViewById(R.id.searchActivitySearchView);
    }

    /**
     * @author Phong-Kaster
     * set up view model
     */
    @SuppressLint("NotifyDataSetChanged")
    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.instantiate();

        /*setup parameter to query*/
        Map<String, String> parameters =new HashMap<>();
        parameters.put("demand",demand);
        parameters.put("search", search);
        viewModel.getProducts(parameters);


        /*get products*/
        viewModel.getObjects().observe(this, productsResponse -> {
            int result = productsResponse.getResult();
            if(result == 1){
                objects.clear();
                objects.addAll( productsResponse.getData() );
                setupRecyclerView(objects);
            }
            else{
                Toast.makeText(SearchActivity.this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
            }
        });

        /*trigger animation*/
//        viewModel.getAnimation().observe(this, aBoolean -> {
//            if(aBoolean && objects.size() == 0)
//            {
//                loadingScreen.start();
//            }
//            else
//            {
//                loadingScreen.stop();
//            }
//        });
    }

    /**
     * @author Phong-Kaster
     * set up recycler view
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
        /* BUTTON GO BACK */
        buttonBack.setOnClickListener(view-> finish());


        /*BUTTON CART */
        buttonCart.setOnClickListener(view-> Toast.makeText(this, "Open Cart", Toast.LENGTH_SHORT ).show());


        /* BUTTON FILTER */
        /*launch advancedSearchActivity for getting more conditions to filter data*/
        buttonFilter.setOnClickListener(view -> {
            Intent intent = new Intent(SearchActivity.this, AdvancedSearchActivity.class);
            startAdvancedSearchActivityForResult.launch(intent);
        });


        /* SEARCH VIEW */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*get text users write down to query*/
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {

                parameters.put("search", query);
                viewModel.getProducts(parameters);
                adapter.notifyDataSetChanged();

                return false;
            }

            /*if users click on close button - newText = "" then query again */
            @Override
            public boolean onQueryTextChange(String newText) {
                if( newText.equals(""))
                {
                    parameters.put("search", newText);
                    viewModel.getProducts(parameters);
                    searchView.clearFocus();
                }
                return false;
            }
        });
    }
}