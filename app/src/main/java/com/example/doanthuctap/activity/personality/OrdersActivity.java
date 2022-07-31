package com.example.doanthuctap.activity.personality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.doanthuctap.R;
import com.example.doanthuctap.container.GetAllOrdersResponse;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Order;
import com.example.doanthuctap.model.OrderStatus;
import com.example.doanthuctap.recyclerviewadapter.CategoriesRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.OrderInformationRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.OrderStatusRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.personality.OrdersActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this activity is used to watch all orders
 */
public class OrdersActivity extends AppCompatActivity implements OrderStatusRecyclerViewAdapter.callbacks {

    private OrdersActivityViewModel viewModel;
    private ImageButton buttonBack;
    private androidx.appcompat.widget.SearchView searchView;


    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private GlobalVariable globalVariable;

    private RecyclerView ordersStatusRecyclerView;
    private OrderStatusRecyclerViewAdapter ordersStatusAdapter;

    private RecyclerView ordersInformationRecyclerView;
    private OrderInformationRecyclerViewAdapter ordersInformationAdapter;

    private LoadingScreen loadingScreen;
    private List<Order> orders = new ArrayList<>();

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        setupComponent();
        setupOrderStatusRecyclerView();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonBack = findViewById(R.id.ordersActivityButtonGoBack);
        searchView = findViewById(R.id.ordersActivitySearchView);

        ordersInformationRecyclerView = findViewById(R.id.ordersActivityInformationRecyclerView);
        globalVariable = (GlobalVariable) getApplication();

        ordersStatusRecyclerView = findViewById(R.id.ordersActivityStatusRecyclerView);
        loadingScreen = new LoadingScreen(this);

        dialog = new Dialog(this);
    }

    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(OrdersActivityViewModel.class);
        viewModel.instantiate();

        /*Step 2 - headers*/
        headers = globalVariable.getHeaders();
        viewModel.getAllOrder(headers, parameters);

        /*Step 3 - pout data into view*/
        viewModel.getAllOrdersResponse().observe(this, response -> {
            int result = response.getResult();
            if( result == 1)
            {
                orders.clear();
                orders.addAll( response.getData() );
                setupOrderInformationRecyclerView( response.getData() );
            }
            else
            {
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.oops_there_is_an_issue), R.drawable.ic_close);
                dialog.btnOK.setOnClickListener(view->dialog.close());
            }
        });

        /*Step 4 - get animation*/
        viewModel.getAnimation().observe(this, aBoolean->{
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

    private void setupOrderStatusRecyclerView()
    {
        /*Step 1 - set up orders status recycler view*/
        OrderStatus status1 = new OrderStatus( getString(R.string.all), "all");
        OrderStatus status2 = new OrderStatus( getString(R.string.to_pay), "processing");
        OrderStatus status3 = new OrderStatus( getString(R.string.packed), "packed");
        OrderStatus status4 = new OrderStatus( getString(R.string.being_transported), "being transported");
        OrderStatus status5 = new OrderStatus( getString(R.string.done), "delivered");
        OrderStatus status6 = new OrderStatus( getString(R.string.cancel), "cancel");


        List<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(status1);
        orderStatuses.add(status2);
        orderStatuses.add(status3);
        orderStatuses.add(status4);
        orderStatuses.add(status5);
        orderStatuses.add(status6);

        ordersStatusAdapter = new OrderStatusRecyclerViewAdapter(this, orderStatuses,OrdersActivity.this);
        ordersStatusRecyclerView.setAdapter(ordersStatusAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ordersStatusRecyclerView.setLayoutManager(manager);
    }


    private void setupOrderInformationRecyclerView(List<Order> orders)
    {
        ordersInformationAdapter = new OrderInformationRecyclerViewAdapter(this, orders);
        ordersInformationRecyclerView.setAdapter( ordersInformationAdapter );

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ordersInformationRecyclerView.setLayoutManager(manager);
    }


    private void setupEvent()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                parameters.put("search", query);
                viewModel.getAllOrder(headers, parameters);
                ordersInformationAdapter.notifyDataSetChanged();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if( newText.equals(""))
                {
                    parameters.put("search", newText);
                    viewModel.getAllOrder(headers, parameters);
                    searchView.clearFocus();
                }
                return false;
            }
        });

        buttonBack.setOnClickListener(view->{
            finish();
        });
    }

    @Override
    public void onItemClicked(String status) {
        if( status.equals("all") )
        {
            parameters.put("status", "");
        }
        else
        {
            parameters.put("status", status);
        }
        viewModel.getAllOrder(headers, parameters);
    }
}