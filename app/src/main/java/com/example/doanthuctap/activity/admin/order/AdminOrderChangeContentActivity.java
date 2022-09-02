package com.example.doanthuctap.activity.admin.order;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.recyclerviewadapter.AdminOrderContentRecyclerViewAdapter;
import com.example.doanthuctap.recyclerviewadapter.OrderContentRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.admin.order.AdminOrderChangeContentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * this activity is used to change order content
 */
public class AdminOrderChangeContentActivity
        extends AppCompatActivity
        implements OrderContentRecyclerViewAdapter.Callback{

    private ImageButton buttonBack;

    private RecyclerView recyclerView;
    private AdminOrderChangeContentViewModel viewModel;
    private AdminOrderContentRecyclerViewAdapter adapter;


    private Map<String, String> headers = new HashMap<>();
    private GlobalVariable globalVariable;

    private LoadingScreen loadingScreen;
    private Dialog dialog;

    private String orderId;
    private String orderStatus;
    private FloatingActionButton buttonAddProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_change_content);

        orderId = getIntent().getStringExtra("orderId");

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonBack = findViewById(R.id.adminOrderChangeContentButtonBack);
        recyclerView = findViewById(R.id.adminOrderChangeContentRecyclerView);

        globalVariable = (GlobalVariable) this.getApplication();
        loadingScreen = new LoadingScreen(this);

        dialog = new Dialog(this);

        buttonAddProduct = findViewById(R.id.adminOrderChangeContentButtonAdd);
    }

    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(AdminOrderChangeContentViewModel.class);
        viewModel.instantiate();

        /*Step 2*/
        headers = globalVariable.getHeaders();
        viewModel.getOrderContentById(headers, orderId);


        /*Step 3 - order content*/
        viewModel.getOrderContentResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1 )
            {
                List<GetLatestOrderResponseContent> products = response.getData();
                orderStatus = response.getOrder().getStatus();
                setupRecyclerView(products);


                /*if order status in array <invalid-status> then we do not allow modifying order content*/
                List<String> invalidStatus = Arrays.asList("being transported", "delivered", "cancel");
                boolean flag = invalidStatus.contains(orderStatus);
                if(flag)
                {
                    Toast.makeText(this, "Trạng thái đơn hàng hiện tại là "+orderStatus+" và sẽ không thể thay đổi nội dung",
                            Toast.LENGTH_SHORT).show();
                    buttonAddProduct.setEnabled(false);
                }
            }
        });

        /*Step 4 - animation*/
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

    /*this private final variable is used to start another activity for result*/
    private final ActivityResultLauncher<Intent> startAdminAddProductToCardActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    viewModel.getOrderContentById(headers, orderId);
                }
                else if( result.getResultCode() == Activity.RESULT_CANCELED){
                    Toast.makeText(this, R.string.complete, Toast.LENGTH_SHORT).show();
                }
            });

    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*BUTTON ADD PRODUCT*/
        buttonAddProduct.setOnClickListener(view->{
            Intent intent = new Intent(this, AdminAddProductToCartActivity.class);
            intent.putExtra("orderId", orderId);
            startAdminAddProductToCardActivity.launch(intent);
        });
    }

    private void setupRecyclerView(List<GetLatestOrderResponseContent> contents)
    {
        adapter = new AdminOrderContentRecyclerViewAdapter(this, contents, this, orderStatus);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        swipeToDelete(recyclerView, adapter, contents);
    }

    @Override
    public void onClickButtonQuantity(String action, int productId, int quantity, int price) {

        if( price < 0 || action.length() < 1)
        {
            return;
        }


        if( "add".equals(action) )
        {
            viewModel.modifyOrderContent(headers, orderId, String.valueOf(productId), String.valueOf(quantity+1) );
        }
        else if( "minus".equals(action) )
        {
            viewModel.modifyOrderContent(headers, orderId, String.valueOf(productId), String.valueOf(quantity-1));
        }

        viewModel.getModifyOrderContentResponse().observe(this, response->{
            int result = response.getResult();
            String msg = response.getMsg();
        });
    }

    /**
     * @author Phong-Kaster
     * Swipe from right to left to eradicate a transaction
     * */
    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void swipeToDelete(RecyclerView recycleView, AdminOrderContentRecyclerViewAdapter adapter, List<GetLatestOrderResponseContent> products)
    {
        /*Step 1*/
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
                {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }


                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Remove swiped item from list and notify the RecyclerView

                        /*declare local variables*/
                        int position = viewHolder.getLayoutPosition();
                        String productId = String.valueOf( products.get(position).getProductId() );
                        String quantity = "0"; // quantity = 0 => delete product from order content
                        /*notify to adapter & remove the transaction on SERVER*/
                        products.remove(position);
                        adapter.notifyItemRemoved(position);
                        viewModel.modifyOrderContent(headers, orderId, productId, quantity);
                    }

                    public void onChildDraw (@NonNull Canvas c,
                                             @NonNull RecyclerView recyclerView,
                                             @NonNull RecyclerView.ViewHolder viewHolder,
                                             float dX, float dY, int actionState, boolean isCurrentlyActive){

                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addBackgroundColor(ContextCompat.getColor(AdminOrderChangeContentActivity.this, R.color.colorRed))
                                .addActionIcon(R.drawable.ic_delete_forever)
                                .create()
                                .decorate();

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        /*Step 2*/
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        touchHelper.attachToRecyclerView(recycleView);
    }
}