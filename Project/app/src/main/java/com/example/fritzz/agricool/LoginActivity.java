package com.example.fritzz.agricool;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button registerButton = findViewById(R.id.registerButton);
        final Button loginButton = findViewById(R.id.loginButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerButton.setBackgroundColor(Color.rgb(0,180,0));
                Intent loginIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(loginIntent);

            }
        });
<<<<<<< HEAD
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginButton.setBackgroundColor(Color.rgb(0,180,0));
                Intent mainMenuIntent = new Intent(LoginActivity.this,MainMenu.class);
                startActivity(mainMenuIntent);
=======
        Button loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
>>>>>>> 6c42b45518d5fc6a32546e8b4c2c701cbaf0db84
            }
        });


    }

}
