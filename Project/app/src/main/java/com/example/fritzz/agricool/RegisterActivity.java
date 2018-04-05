package com.example.fritzz.agricool;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
/**
 * Created by Cristi on 3/22/2018.
 */

public class RegisterActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button btn = (Button)findViewById(R.id.signUpButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainMenuActivity.class));
            }
        });

    }
}
