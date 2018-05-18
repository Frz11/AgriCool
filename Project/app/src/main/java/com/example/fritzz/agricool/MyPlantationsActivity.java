package com.example.fritzz.agricool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;

public class MyPlantationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plantations);
        Button addPlantations = findViewById(R.id.addPlantation);
        addPlantations.setOnClickListener(event -> startActivity(new Intent(MyPlantationsActivity.this,MainMenu.class)));
    }
}
