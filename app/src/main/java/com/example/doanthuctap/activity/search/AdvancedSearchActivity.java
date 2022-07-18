package com.example.doanthuctap.activity.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doanthuctap.R;

import java.util.ArrayList;

public class AdvancedSearchActivity extends AppCompatActivity {

    private AppCompatButton buttonConfirm;
    private AppCompatButton buttonCancel;

    private Spinner spinnerManufacturer;

    private EditText priceFrom;
    private EditText priceTo;

    private AppCompatRadioButton checkBoxScreenSize13inch;
    private AppCompatRadioButton checkBoxScreenSize14inch;
    private AppCompatRadioButton checkBoxScreenSize156inch;
    private AppCompatRadioButton checkBoxScreenSize173inch;

    private Spinner spinnerGraphicCard;

    private Spinner spinnerDemand;
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
    private void setupComponent(){
        buttonConfirm = findViewById(R.id.advancedSearchButtonConfirm);
        buttonCancel = findViewById(R.id.advancedSearchButtonCancel);

        spinnerManufacturer = findViewById(R.id.advancedSearchSpinnerManufacturer);

        priceFrom = findViewById(R.id.advancedSearchPriceFrom);
        priceTo = findViewById(R.id.advancedSearchPriceTo);

        checkBoxScreenSize13inch = findViewById(R.id.advancedSearchCheckBox13inch);
        checkBoxScreenSize14inch = findViewById(R.id.advancedSearchCheckBox14inch);
        checkBoxScreenSize156inch = findViewById(R.id.advancedSearchCheckBox156inch);
        checkBoxScreenSize173inch = findViewById(R.id.advancedSearchCheckBox173inch);

        spinnerGraphicCard = findViewById(R.id.advancedSearchSpinnerGraphicCard);

        spinnerDemand = findViewById(R.id.advancedSearchSpinnerDemand);
    }


    private void setupScreen(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ACER");
        arrayList.add("ASUS");
        arrayList.add("DELL");
        arrayList.add("HP");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManufacturer.setAdapter(arrayAdapter);
        spinnerManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    /**
     * @author Phong-Kaster
     * handle event when users click on button
     */
    private void setupEvent(){
        /*BUTTON CONFIRM*/
        buttonConfirm.setOnClickListener(view->{
            Intent intent = new Intent();
           setResult(RESULT_OK, intent);
            intent.putExtra("hallo", "hallo Phong Kaster");
            finish();
        });


        /*BUTTON CANCEL*/
        buttonCancel.setOnClickListener(view->{
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }
}