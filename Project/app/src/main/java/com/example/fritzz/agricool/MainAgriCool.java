package com.example.fritzz.agricool;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


import com.example.fritzz.*;


public class MainAgriCool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agri_cool);
        ((GlobalVariables) this.getApplication()).setIp("172.24.28.48");

        final Button button = findViewById(R.id.button_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundColor(Color.rgb(0,180,0));
                Intent loginIntent = new Intent(MainAgriCool.this,LoginActivity.class);
                startActivity(loginIntent);

            }
        });


    }


}
