package com.example.fritzz.agricool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GlobalVariables gb = (GlobalVariables) getApplication();
        User user = gb.getThisUser();
        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        username.setText(user.Username);
        email.setText(user.Email);


    }
}
