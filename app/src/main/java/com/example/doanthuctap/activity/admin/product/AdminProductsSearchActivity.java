package com.example.doanthuctap.activity.admin.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.doanthuctap.R;
import com.example.doanthuctap.arrayadapter.DemandArrayAdapter;
import com.example.doanthuctap.arrayadapter.GraphicCardArrayAdapter;
import com.example.doanthuctap.arrayadapter.ManufacturerArrayAdapter;
import com.example.doanthuctap.model.Option;

import java.util.ArrayList;
import java.util.List;

public class AdminProductsSearchActivity extends AppCompatActivity {

    private AppCompatButton buttonConfirm;
    private AppCompatButton buttonReset;

    private EditText txtKeyword;
    private Spinner spinnerManufacturer;

    private EditText txtPriceFrom;
    private EditText txtPriceTo;

    private AppCompatRadioButton checkBoxScreenSize13inch;
    private AppCompatRadioButton checkBoxScreenSize14inch;
    private AppCompatRadioButton checkBoxScreenSize156inch;
    private AppCompatRadioButton checkBoxScreenSize173inch;

    private AppCompatRadioButton radioButtoni3;
    private AppCompatRadioButton radioButtoni5;
    private AppCompatRadioButton radioButtoni7;
    private RadioGroup radioGroupCpu;
    private RadioGroup radioGroupScreenSize;

    private Spinner spinnerGraphicCard;

    private Spinner spinnerDemand;

    List<Option> manufacturerOptions = new ArrayList<>();
    List<Option> graphicCardOptions = new ArrayList<>();
    List<Option> demandOptions = new ArrayList<>();

    /**
     * they are 7 parameters to send back Search Activity for filtering products
     */
    private String search = "";
    private String manufacturer = "";
    private String priceFrom = "";
    private String priceTo = "";
    private String screenSize = "";
    private String cpu = "";
    private String graphicCard = "";
    private String demand = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products_search);

        setupComponent();
        setupScreen();
        setupEvent();
    }

    /**
     * @author Phong-Kaster
     * mapping components with XML layout
     */
    @SuppressLint("CutPasteId")
    private void setupComponent(){
        buttonConfirm = findViewById(R.id.adminProductsSearchButtonConfirm);
        buttonReset = findViewById(R.id.adminProductsSearchButtonReset);

        txtKeyword = findViewById(R.id.adminProductsSearchKeyword);
        spinnerManufacturer = findViewById(R.id.adminProductsSearchSpinnerManufacturer);

        txtPriceFrom = findViewById(R.id.adminProductsSearchPriceFrom);
        txtPriceTo = findViewById(R.id.adminProductsSearchPriceTo);

        checkBoxScreenSize13inch = findViewById(R.id.adminProductsSearchCheckBox13inch);
        checkBoxScreenSize14inch = findViewById(R.id.adminProductsSearchCheckBox14inch);
        checkBoxScreenSize156inch = findViewById(R.id.adminProductsSearchCheckBox156inch);
        checkBoxScreenSize173inch = findViewById(R.id.adminProductsSearchCheckBox173inch);
        radioGroupScreenSize = findViewById(R.id.radioGroupScreenSize);

        radioButtoni3 = findViewById(R.id.adminProductsSearchRadioButtoni3);
        radioButtoni5 = findViewById(R.id.adminProductsSearchRadioButtoni5);
        radioButtoni7 = findViewById(R.id.adminProductsSearchRadioButtoni7);
        radioGroupCpu = findViewById(R.id.radioGroupCpu);

        spinnerGraphicCard = findViewById(R.id.adminProductsSearchSpinnerGraphicCard);

        spinnerDemand = findViewById(R.id.adminProductsSearchSpinnerDemand);
    }


    /**
     * @author Phong-Kaster
     * set up data into components
     */
    private void setupScreen(){
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


        /*graphic card SPINNER*/
        graphicCardOptions = Option.getGraphicCardOptions();
        GraphicCardArrayAdapter graphicCardAdapter = new GraphicCardArrayAdapter(this, graphicCardOptions);
        spinnerGraphicCard.setAdapter(graphicCardAdapter);
        spinnerGraphicCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = graphicCardOptions.get(position).getName();
                graphicCard = name.toLowerCase().trim();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        /*demand spinner*/
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


        /*CPU filter*/
        radioGroupCpu.setOnCheckedChangeListener((radioGroup, i) -> {
            AppCompatRadioButton radioButton = radioGroup.findViewById(i);
            cpu = radioButton.getText().toString();
        });


        /*Screen Size filter*/
        radioGroupScreenSize.setOnCheckedChangeListener((radioGroup, i) -> {
            AppCompatRadioButton radioButton =  radioGroup.findViewById(i);
            screenSize = radioButton.getText().toString();
        });
    }


    /**
     * @author Phong-Kaster
     * handle event when users click on button
     *
     * button confirm sends conditions that users select to find products
     * button reset set up all conditions to default value
     */
    private void setupEvent()
    {
        /*BUTTON CONFIRM*/
        buttonConfirm.setOnClickListener(view->{
            priceFrom = txtPriceFrom.getText().toString();
            priceTo = txtPriceTo.getText().toString();
            search = txtKeyword.getText().toString();


            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            intent.putExtra("search", search );
            intent.putExtra("manufacturer", manufacturer);
            intent.putExtra("priceFrom", priceFrom);
            intent.putExtra("priceTo", priceTo);
            intent.putExtra("screenSize", screenSize);
            intent.putExtra("cpu", cpu);
            intent.putExtra("graphicCard", graphicCard);
            intent.putExtra("demand", demand);
            finish();
        });


        /*BUTTON RESET*/
        buttonReset.setOnClickListener(view->{
            spinnerManufacturer.setSelection(0);

            txtKeyword.setText("");
            txtPriceFrom.setText("");
            txtPriceTo.setText("");

            checkBoxScreenSize13inch.setChecked(false);
            checkBoxScreenSize14inch.setChecked(false);
            checkBoxScreenSize156inch.setChecked(false);
            checkBoxScreenSize173inch.setChecked(false);

            radioButtoni3.setChecked(false);
            radioButtoni5.setChecked(false);
            radioButtoni7.setChecked(false);

            spinnerGraphicCard.setSelection(0);

            spinnerDemand.setSelection(0);
        });
    }
}