package com.example.doanthuctap.activity.admin.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.doanthuctap.R;
import com.example.doanthuctap.arrayadapter.DemandArrayAdapter;
import com.example.doanthuctap.arrayadapter.ManufacturerArrayAdapter;
import com.example.doanthuctap.arrayadapter.ScreenSizeArrayAdapter;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Option;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.viewModel.admin.product.AdminProductCreateViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminProductCreateActivity extends AppCompatActivity {

    private AppCompatButton buttonConfirm;
    private ImageButton buttonBack;

    private EditText txtName;
    private Spinner spinnerManufacturer;

    private EditText txtPrice;
    private EditText txtRemaining;
    private Spinner spinnerScreenSize;

    private EditText txtCPU;
    private EditText txtRAM;
    private EditText txtGraphicCard;

    private EditText txtROM;
    private Spinner spinnerDemand;
    private EditText txtContent;


    private LoadingScreen loadingScreen;
    private Dialog dialog;


    private Map<String, String> headers = new HashMap<>();
    private GlobalVariable globalVariable;


    List<Option> manufacturerOptions = new ArrayList<>();
    List<Option> screenSizeOptions = new ArrayList<>();
    List<Option> demandOptions = new ArrayList<>();

    private AdminProductCreateViewModel viewModel;
    /**
     * they are 7 parameters to send back Search Activity for filtering products
     */
    private ProductClient product = null;
    private String manufacturer = "";
    private String screenSize = "";
    private String demand = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_create);

        setupComponent();
        setupSpinner();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonConfirm = findViewById(R.id.adminProductCreateButtonConfirm);
        buttonBack = findViewById(R.id.adminProductCreateButtonBack);

        txtName = findViewById(R.id.adminProductCreateName);
        spinnerManufacturer = findViewById(R.id.adminProductCreateManufacturerSpinner);

        txtPrice = findViewById(R.id.adminProductCreatePrice);
        txtRemaining = findViewById(R.id.adminProductCreateRemaining);

        spinnerScreenSize = findViewById(R.id.adminProductCreateScreenSizeSpinner);
        txtPrice = findViewById(R.id.adminProductCreatePrice);

        txtCPU = findViewById(R.id.adminProductCreateCPU);
        txtRAM = findViewById(R.id.adminProductCreateRAM);
        txtGraphicCard = findViewById(R.id.adminProductCreateGraphicCard);

        txtROM = findViewById(R.id.adminProductCreateROM);
        spinnerDemand = findViewById(R.id.adminProductCreateDemandSpinner);

        txtContent = findViewById(R.id.adminProductCreateContent);

        globalVariable = (GlobalVariable) this.getApplication();
        dialog = new Dialog(this);

        loadingScreen = new LoadingScreen(this);
    }

    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(AdminProductCreateViewModel.class);
        viewModel.instantiate();
    }


    /**
     * @author Phong-Kaster
     * set up spinner
     */
    private void setupSpinner()
    {
        /*MANUFACTURER SPINNER*/
        manufacturerOptions = Option.getManufacturerOption();
        ManufacturerArrayAdapter manufacturerAdapter = new ManufacturerArrayAdapter(this, manufacturerOptions);
        spinnerManufacturer.setAdapter(manufacturerAdapter);
        spinnerManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = manufacturerOptions.get(position).getName();
                manufacturer = name.toLowerCase().trim();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        /*DEMAND SPINNER*/
        demandOptions = Option.getDemandOptions();
        DemandArrayAdapter demandAdapter = new DemandArrayAdapter(this, demandOptions);
        spinnerDemand.setAdapter(demandAdapter);
        spinnerDemand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = demandOptions.get(position).getName();
                demand = name.toLowerCase().trim();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        /*SCREEN SIZE SPINNER*/
        screenSizeOptions = Option.getScreenSize();
        ScreenSizeArrayAdapter screenSizeAdapter = new ScreenSizeArrayAdapter(this, screenSizeOptions);
        spinnerScreenSize.setAdapter(screenSizeAdapter);
        spinnerScreenSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = screenSizeOptions.get(position).getName();
                screenSize = name.toLowerCase().trim();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    private void setupEvent()
    {
        buttonConfirm.setOnClickListener(view->{
            /*Step 1 - declare product*/
            String name = txtName.getText().toString();
            manufacturer = manufacturer.toLowerCase().trim();
            String price = txtPrice.getText().toString();
            String remaining = txtRemaining.getText().toString();
            screenSize = screenSize.toLowerCase().trim();
            String cpu = txtCPU.getText().toString();
            String ram = txtRAM.getText().toString();
            String graphicCard = txtGraphicCard.getText().toString();
            String rom = txtROM.getText().toString();
            demand = demand.toLowerCase().trim();
            String content = txtContent.getText().toString();


//            System.out.println(name);
//            System.out.println(manufacturer);
//            System.out.println(price);
//            System.out.println(remaining);
//            System.out.println(screenSize);
//            System.out.println(cpu);
//            System.out.println(ram);
//            System.out.println(graphicCard);
//            System.out.println(rom);
//            System.out.println(demand);
//            System.out.println(content);

            /*Step 2 - send request*/
            Map<String, String> body = new HashMap<>();
            body.put("name", name);
            body.put("manufacturer", manufacturer);
            body.put("price", price);
            body.put("remaining", remaining);
            body.put("screenSize", screenSize);
            body.put("cpu", cpu);
            body.put("ram", ram);
            body.put("graphicCard", graphicCard);
            body.put("rom", rom);
            body.put("demand", demand);
            body.put("content", content);



            /*Step 3 - make request*/
            headers = globalVariable.getHeaders();
            viewModel.createProduct(headers, body);

//            viewModel.getAnimation().observe(this, aBoolean -> {
//                if( aBoolean )
//                {
//                    loadingScreen.start();
//                }
//                else
//                {
//                    loadingScreen.stop();
//                }
//            });

            dialog.announce();
            viewModel.getResponse().observe(this, response->{
                int result = response.getResult();
                String msg = response.getMsg();

                if( result == 1)
                {
                    dialog.show(R.string.success, getString(R.string.create_product_successfully), R.drawable.ic_check);
                }
                else
                {
                    dialog.show(R.string.fail, msg, R.drawable.ic_close);
                }
            });
            dialog.btnOK.setOnClickListener(view1->dialog.close());
        });

        buttonBack.setOnClickListener(view-> finish());
    }
}