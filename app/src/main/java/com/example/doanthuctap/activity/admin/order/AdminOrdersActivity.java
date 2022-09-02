package com.example.doanthuctap.activity.admin.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Order;
import com.example.doanthuctap.model.OrderStatus;
import com.example.doanthuctap.recyclerviewadapter.AdminOrderInformationRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.OrderStatusRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.admin.order.AdminOrdersActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminOrdersActivity extends AppCompatActivity
        implements OrderStatusRecyclerViewAdapter.callbacks,
        AdminOrderInformationRecyclerViewAdapter.callbacks
{

    private ImageButton buttonBack;
    private androidx.appcompat.widget.SearchView searchView;
    private com.google.android.material.floatingactionbutton.FloatingActionButton buttonCreateOrder;


    /*order information*/
    private AdminOrderInformationRecyclerViewAdapter adapter;
    private RecyclerView ordersInformationRecyclerView;


    /*view model*/
    private AdminOrdersActivityViewModel viewModel;


    /*headers and parameters*/
    private Map<String, String> headers;
    private final Map<String, String> parameters = new HashMap<>();
    private GlobalVariable globalVariable;


    /*order status recycler view*/
    private OrderStatusRecyclerViewAdapter ordersStatusAdapter;
    private RecyclerView ordersStatusRecyclerView;


    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        setupComponent();
        setupOrderStatusRecyclerView();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent() {
        buttonBack = findViewById(R.id.adminOrdersButtonGoBack);
        searchView = findViewById(R.id.adminOrdersSearchView);

        buttonCreateOrder = findViewById(R.id.adminOrdersButtonCreateOrder);
        ordersInformationRecyclerView = findViewById(R.id.adminOrdersInformationRecyclerView);
        ordersStatusRecyclerView = findViewById(R.id.adminOrdersStatusRecyclerView);

        globalVariable = (GlobalVariable) this.getApplication();
        headers = globalVariable.getHeaders();

        searchView = findViewById(R.id.adminOrdersSearchView);
        dialog = new Dialog(this);
        loadingScreen= new LoadingScreen(this);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void setupViewModel()
    {
        /*Step 1*/
        viewModel = new ViewModelProvider(this).get(AdminOrdersActivityViewModel.class);
        viewModel.instantiate();

        /*Step 2*/
        viewModel.adminGetAllOrders(headers, parameters);
        viewModel.getAllOrders().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {
                List<Order> orders = response.getData();
                setupOrderInformationRecyclerView( orders );
            }
        });
    }

    private void setupOrderStatusRecyclerView()
    {
        /*Step 1 - set up orders status recycler view*/
        OrderStatus status1 = new OrderStatus( getString(R.string.all), "all");
        OrderStatus status2 = new OrderStatus( getString(R.string.verified), "verified");
        OrderStatus status3 = new OrderStatus( getString(R.string.to_pay), "processing");
        OrderStatus status4 = new OrderStatus( getString(R.string.packed), "packed");
        OrderStatus status5 = new OrderStatus( getString(R.string.being_transported), "being transported");
        OrderStatus status6 = new OrderStatus( getString(R.string.done), "delivered");
        OrderStatus status7 = new OrderStatus( getString(R.string.cancel), "cancel");


        List<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(status1);
        orderStatuses.add(status2);
        orderStatuses.add(status3);
        orderStatuses.add(status4);
        orderStatuses.add(status5);
        orderStatuses.add(status6);
        orderStatuses.add(status7);

        ordersStatusAdapter = new OrderStatusRecyclerViewAdapter(this, orderStatuses, AdminOrdersActivity.this);
        ordersStatusRecyclerView.setAdapter(ordersStatusAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ordersStatusRecyclerView.setLayoutManager(manager);
    }

    private void setupOrderInformationRecyclerView(List<Order> orders)
    {
        adapter = new AdminOrderInformationRecyclerViewAdapter(this, orders, this);
        ordersInformationRecyclerView.setAdapter( adapter );


        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ordersInformationRecyclerView.setLayoutManager(manager);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*SEARCH VIEW*/
        /* SEARCH VIEW */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*get text users write down to query*/
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                parameters.put("search", query);
                viewModel.adminGetAllOrders(headers, parameters);
                return false;
            }

            /*if users click on close button - newText = "" then query again */
            @Override
            public boolean onQueryTextChange(String newText) {
                parameters.put("search","");
                viewModel.adminGetAllOrders(headers, parameters);
                return false;
            }
        });

        /*BUTTON CREATE ORDER*/
        buttonCreateOrder.setOnClickListener(view -> {
            Intent intent = new Intent(AdminOrdersActivity.this, AdminOrderCreateActivity.class);
            startActivity(intent);
        });

        /*SWIPE REFRESH LAYOUT*/
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.adminGetAllOrders(headers, parameters);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    /**
     * OrderStatusRecyclerViewAdapter - interface
     */
    @Override
    public void onItemClicked(String status) {
        if( status.equals("all") )
        {
            parameters.put("search", "");
        }
        else
        {
            parameters.put("search", status);
        }
        viewModel.adminGetAllOrders(headers, parameters);
    }

    /**
     * AdminOrderInformationRecyclerViewAdapter
     * */
    @Override
    public void onRemoveButtonClicked(String orderId) {
        viewModel.adminDeleteOrder(headers, orderId);


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

        dialog.announce();
        viewModel.getDeleteOrderResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {
                dialog.show(R.string.success, getString(R.string.remove_successfully), R.drawable.ic_check);
            }
            else
            {
                String message = response.getMsg();
                dialog.show(R.string.fail, message, R.drawable.ic_close);
            }
        });
        dialog.btnOK.setOnClickListener(view1->{
            dialog.close();
            viewModel.adminGetAllOrders(headers, parameters);
        });
    }
}