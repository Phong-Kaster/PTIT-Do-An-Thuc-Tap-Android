package com.example.doanthuctap.activity.personality;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthuctap.R;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.GetLatestOrderResponseContent;
import com.example.doanthuctap.recyclerviewadapter.CheckoutOrderContentRecyclerViewAdapter;
import com.example.doanthuctap.viewModel.personality.OrderInformationViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInformationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckoutOrderContentRecyclerViewAdapter adapter;

    private ImageButton buttonBack;
    private OrderInformationViewModel viewModel;

    private Map<String, String> headers = new HashMap<>();
    private GlobalVariable globalVariable;

    private LoadingScreen loadingScreen;
    private String orderId = "";

    private Dialog dialog;
    private TextView txtTotalAmount;
    private TextView txtProvisionalCalculation;

    private TextView txtOrderId;
    private TextView txtDate;
    private TextView txtStatus;
    private TextView txtReceiverName;
    private TextView txtReceiverPhone;
    private TextView txtReceiverAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
        orderId = getIntent().getStringExtra("orderId");

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    public void setupComponent()
    {
        buttonBack = findViewById(R.id.orderInformationButtonGoBack);
        globalVariable = (GlobalVariable) getApplication();

        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);

        txtOrderId = findViewById(R.id.orderInformationOrderID);
        txtDate  = findViewById(R.id.orderInformationOrderDate);
        txtStatus = findViewById(R.id.orderInformationOrderStatus);
        txtReceiverAddress = findViewById(R.id.orderInformationReceiverAddress);
        txtReceiverName = findViewById(R.id.orderInformationReceiverName);
        txtReceiverPhone = findViewById(R.id.orderInformationReceiverPhone);

        txtTotalAmount = findViewById(R.id.orderInformationTotal);
        txtProvisionalCalculation = findViewById(R.id.orderInformationProvisionalCalculation);
        recyclerView = findViewById(R.id.orderInformationContent);
    }

    public void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(OrderInformationViewModel.class);
        viewModel.instanciate();

        /*Step 2 - call api*/
        headers = globalVariable.getHeaders();
        viewModel.getOrderByID(orderId, headers);

        /*Step 3 - pour data into view*/
        viewModel.getOrderByIdResponse().observe(this, response -> {
            int result = response.getResult();
            if( result == 1)
            {

                String orderId = response.getData().getId();
                String date = response.getData().getUpdateAt();
                String receiverName = response.getData().getReceiverName();
                String receiverPhone = response.getData().getReceiverPhone();
                String receiverAddress = response.getData().getReceiverAddress();
                String input = response.getData().getStatus();
                String status = Beautifier.convertStatusToReadableStatus(this, input );
                String total = Beautifier.formatNumber(response.getData().getTotal()) + "đ";
                List<GetLatestOrderResponseContent> objects = response.getContent();


                txtOrderId.setText( orderId );
                txtDate.setText( date );
                txtReceiverName.setText( receiverName );
                txtReceiverPhone.setText( receiverPhone );
                txtReceiverAddress.setText( receiverAddress );
                txtStatus.setText( status );
                txtTotalAmount.setText(total);
                txtProvisionalCalculation.setText(total);
                setupOrderContentRecyclerView(objects);

            }
            else
            {
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.attention), R.drawable.ic_close);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });

        /*Step 4 - get animation*/
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

    private void setupEvent()
    {
        buttonBack.setOnClickListener(view->finish());
    }


    private void setupOrderContentRecyclerView(List<GetLatestOrderResponseContent> objects)
    {
        adapter = new CheckoutOrderContentRecyclerViewAdapter(this, objects);
        recyclerView.setAdapter( adapter );

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
}