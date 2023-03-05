package com.example.doanthuctap.activity.admin.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.doanthuctap.R;
import com.example.doanthuctap.activity.admin.AdminPhotoActivity;
import com.example.doanthuctap.arrayadapter.DemandArrayAdapter;
import com.example.doanthuctap.arrayadapter.ManufacturerArrayAdapter;
import com.example.doanthuctap.arrayadapter.ScreenSizeArrayAdapter;
import com.example.doanthuctap.helper.Beautifier;
import com.example.doanthuctap.helper.Dialog;
import com.example.doanthuctap.helper.GlobalVariable;
import com.example.doanthuctap.helper.LoadingScreen;
import com.example.doanthuctap.model.Option;
import com.example.doanthuctap.model.ProductClient;
import com.example.doanthuctap.viewModel.admin.product.AdminProductEditViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminProductEditActivity extends AppCompatActivity {

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


    private AdminProductEditViewModel viewModel;
    private Map<String, String> headers = new HashMap<>();
    private String productId;
    private GlobalVariable globalVariable;
    private AppCompatButton buttonAddPhoto;

    List<Option> manufacturerOptions = new ArrayList<>();
    List<Option> screenSizeOptions = new ArrayList<>();
    List<Option> demandOptions = new ArrayList<>();
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
        setContentView(R.layout.activity_admin_product_edit);

        if( getIntent() != null)
        {
            productId = getIntent().getStringExtra("productId");
        }
        else
        {
            finish();
        }

        setupComponent();
        setupViewModel();
        setupSpinner();
        setupEvent();
    }

    private void setupComponent()
    {
        buttonConfirm = findViewById(R.id.adminProductButtonConfirm);
        buttonBack = findViewById(R.id.adminProductButtonBack);

        txtName = findViewById(R.id.adminProductName);
        spinnerManufacturer = findViewById(R.id.adminProductManufacturerSpinner);

        txtPrice = findViewById(R.id.adminProductPrice);
        txtRemaining = findViewById(R.id.adminProductRemaining);

        spinnerScreenSize = findViewById(R.id.adminProductScreenSizeSpinner);
        txtPrice = findViewById(R.id.adminProductPrice);

        txtCPU = findViewById(R.id.adminProductCPU);
        txtRAM = findViewById(R.id.adminProductRAM);
        txtGraphicCard = findViewById(R.id.adminProductGraphicCard);

        txtROM = findViewById(R.id.adminProductROM);
        spinnerDemand = findViewById(R.id.adminProductDemandSpinner);

        txtContent = findViewById(R.id.adminProductContent);

        globalVariable = (GlobalVariable) this.getApplication();
        dialog = new Dialog(this);

        loadingScreen = new LoadingScreen(this);
        buttonAddPhoto = findViewById(R.id.adminProductButtonAddPhoto);
    }

    private void setupViewModel()
    {
        /*Step 1 - declare variable*/
        viewModel = new ViewModelProvider(this).get(AdminProductEditViewModel.class);
        viewModel.instantiate();


        /*Step 2 - animation*/
//        viewModel.getAnimation().observe(this, aBoolean -> {
//            if( aBoolean )
//            {
//                loadingScreen.start();
//            }
//            else
//            {
//                loadingScreen.stop();
//            }
//        });


        /*Step 3 - listen response*/
        headers = globalVariable.getHeaders();
        viewModel.getProductByID(headers, productId);
        dialog.announce();
        viewModel.getProductByIdResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {
                product = response.getData();
                setupScreen(product);
            }
            else
            {
                String msg = response.getMsg();
                dialog.show(R.string.fail, msg, R.drawable.ic_close);
            }
        });
        dialog.btnOK.setOnClickListener(view-> dialog.close());
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

    /**
     * @author Phong-Kaster
     * set up screen
     * */
    private void setupScreen(ProductClient product)
    {
        /*Step 1 - declare product*/
        String avatar = Beautifier.getRootURL()  + product.getAvatar();
        String name = product.getName();
        String manufacturer = product.getManufacturer().toLowerCase();
        int price = product.getPrice();
        int remaining = product.getRemaining();
        double screenSize = product.getScreenSize();
        String cpu = product.getCpu();
        String ram = product.getRam();
        String graphicCard = product.getGraphicCard();
        String rom = product.getRom();
        String demand = product.getDemand();
        String content = product.getContent().length() > 0 ? product.getContent() : "";


        /*Step 2 - set up screen*/

        /*Name*/
        txtName.setText(name);

        /*MANUFACTURER*/
        Map<String, Integer> manufacturerValue = new HashMap<>();
        manufacturerValue.put("", 0);
        manufacturerValue.put("acer", 1);
        manufacturerValue.put("asus", 2);
        manufacturerValue.put("dell", 3);
        manufacturerValue.put("hp", 4);
        manufacturerValue.put("msi", 5);
        manufacturerValue.put("lenovo", 6);
        int manufacturerPosition = manufacturerValue.get(manufacturer) != null ? manufacturerValue.get(manufacturer) : 0 ;
        spinnerManufacturer.setSelection(manufacturerPosition);

        /*PRICE*/
        //txtPrice.setText(Beautifier.formatNumber(price));
        txtPrice.setText( String.valueOf(price));

        /*REMAINING*/
//        txtRemaining.setText(Beautifier.formatNumber(remaining));
        txtRemaining.setText( String.valueOf(remaining));

        /*SCREEN SIZE*/
        Map<Double, Integer> screenSizeValue = new HashMap<>();
        screenSizeValue.put(13.0, 0);
        screenSizeValue.put(14.0, 1);
        screenSizeValue.put(15.6, 2);
        screenSizeValue.put(17.3, 3);
        int screenSizePosition = screenSizeValue.get(screenSize) != null ? screenSizeValue.get(screenSize) : 0 ;
        spinnerScreenSize.setSelection(screenSizePosition);

        /*CPU*/
        txtCPU.setText(cpu);

        /*RAM*/
        txtRAM.setText(ram);

        /*Graphic Card*/
        txtGraphicCard.setText(graphicCard);

        /*ROM*/
        txtROM.setText(rom);

        /*DEMAND*/
        Map<String, Integer> demandValue = new HashMap<>();
        demandValue.put("", 0);
        demandValue.put("gaming", 1);
        demandValue.put("student", 2);
        demandValue.put("office", 3);
        demandValue.put("design", 4);
        demandValue.put("lightweight", 5);
        int demandPosition = demandValue.get(demand) != null ? demandValue.get(demand) : 0 ;
        spinnerDemand.setSelection(demandPosition);

        /*CONTENT*/
        txtContent.setText(content);
    }


    private void setupEvent()
    {
        /*BUTTON BACK*/
        buttonBack.setOnClickListener(view->finish());

        /*BUTTON CONFIRM*/
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


            /*Step 2 - send request*/
            Map<String, String> body = new HashMap<>();
            body.put("productId", productId);
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
            viewModel.updateProductByID(headers, body);

//            viewModel.getUpdateProductAnimationAnimation().observe(this, aBoolean -> {
//                if( aBoolean.equals(true) )
//                {
//                    loadingScreen.start();
//                }
//                else
//                {
//                    loadingScreen.stop();
//                }
//            });

            dialog.announce();
            viewModel.getUpdateProductByIDResponse().observe(this, response->{

                int result = response.getResult();
                String msg = response.getMsg();
                if( result == 1)
                {
                    dialog.show(R.string.success, getString(R.string.update_product_successfully), R.drawable.ic_check);
                }
                else
                {
                    dialog.show(R.string.fail, msg, R.drawable.ic_close);
                }
            });
            dialog.btnOK.setOnClickListener(view1->{
                dialog.close();
            });
        });


        /*BUTTON ADD PHOTO*/
        buttonAddPhoto.setOnClickListener(view->{
            Intent intent = new Intent(this, AdminPhotoActivity.class);
            intent.putExtra("productId", productId);
            startActivity(intent);
        });
    }
}