package com.example.fritzz.agricool;

import android.content.Intent;
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

        myProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                myProfile.setBackgroundColor(1);
            }
        });
    }
}
