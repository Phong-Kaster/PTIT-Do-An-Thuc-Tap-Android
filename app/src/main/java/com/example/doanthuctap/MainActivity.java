package com.example.doanthuctap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.doanthuctap.activity.authentication.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*go to LoginActivity*/
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}