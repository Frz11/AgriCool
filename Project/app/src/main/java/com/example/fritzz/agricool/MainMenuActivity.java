package com.example.fritzz.agricool;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button myProfile = (Button)findViewById(R.id.myProfile);
        final Button plants = (Button) findViewById(R.id.Plants);
        final Button logOut = (Button) findViewById(R.id.logOut);
        final Button myPlantations = (Button) findViewById(R.id.myPlantations);
        final Button credits = findViewById(R.id.Credits);
        credits.setOnClickListener(event -> {
            //finish();
            startActivity(new Intent(MainMenuActivity.this,CreditsActivity.class));
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.os.Process.killProcess(Process.myPid());
                finish();
                startActivity(new Intent(MainMenuActivity.this,LoginActivity.class));
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                startActivity(new Intent(MainMenuActivity.this,Profile.class));
            }
        });
        plants.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainMenuActivity.this,PlantsActivity.class));
            }

        });
        myPlantations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this,MyPlantationsActivity.class));
            }
        });
    }

}
