package com.example.doanthuctap.activity.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.doanthuctap.R;
import com.example.doanthuctap.arrayadapter.DemandArrayAdapter;
import com.example.doanthuctap.arrayadapter.GraphicCardArrayAdapter;
import com.example.doanthuctap.arrayadapter.ManufacturerArrayAdapter;
import com.example.doanthuctap.model.Option;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchActivity extends AppCompatActivity {

    private AppCompatButton buttonConfirm;
    private AppCompatButton buttonCancel;

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
        setContentView(R.layout.activity_advanced_search);
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
        buttonConfirm = findViewById(R.id.advancedSearchButtonConfirm);
        buttonCancel = findViewById(R.id.advancedSearchButtonCancel);

        spinnerManufacturer = findViewById(R.id.advancedSearchSpinnerManufacturer);

        txtPriceFrom = findViewById(R.id.advancedSearchPriceFrom);
        txtPriceTo = findViewById(R.id.advancedSearchPriceTo);

        checkBoxScreenSize13inch = findViewById(R.id.advancedSearchCheckBox13inch);
        checkBoxScreenSize14inch = findViewById(R.id.advancedSearchCheckBox14inch);
        checkBoxScreenSize156inch = findViewById(R.id.advancedSearchCheckBox156inch);
        checkBoxScreenSize173inch = findViewById(R.id.advancedSearchCheckBox173inch);
        radioGroupScreenSize = findViewById(R.id.radioGroupScreenSize);

        radioButtoni3 = findViewById(R.id.advancedSearchRadioButtoni3);
        radioButtoni5 = findViewById(R.id.advancedSearchRadioButtoni5);
        radioButtoni7 = findViewById(R.id.advancedSearchRadioButtoni7);
        radioGroupCpu = findViewById(R.id.radioGroupCpu);

        spinnerGraphicCard = findViewById(R.id.advancedSearchSpinnerGraphicCard);

        spinnerDemand = findViewById(R.id.advancedSearchSpinnerDemand);
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
    private void setupEvent(){
        /*BUTTON CONFIRM*/
        buttonConfirm.setOnClickListener(view->{

            priceFrom = txtPriceFrom.getText().toString();
            priceTo = txtPriceTo.getText().toString();

//            if( Integer.parseInt(priceFrom) > Integer.valueOf(priceTo) ){
//
//                Toast.makeText(this, "Giá khởi điểm phải bé hơn giá mua", Toast.LENGTH_SHORT).show();
//                return;
//            }



            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            intent.putExtra("hallo", "hallo Phong Kaster");
            intent.putExtra("manufacturer", manufacturer);
            intent.putExtra("priceFrom", priceFrom);
            intent.putExtra("priceTo", priceTo);
            intent.putExtra("screenSize", screenSize);
            intent.putExtra("cpu", cpu);
            intent.putExtra("graphicCard", graphicCard);
            intent.putExtra("demand", demand);
            finish();
        });


        /*BUTTON CANCEL*/
        buttonCancel.setOnClickListener(view->{
            spinnerManufacturer.setSelection(0);

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

    /**
     * @author Phong-Kaster
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}