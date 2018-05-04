package com.example.fritzz.agricool;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.android.volley.*;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by Cristi on 3/22/2018.
 */

public class RegisterActivity extends AppCompatActivity{
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    User newUser;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button btn = (Button)findViewById(R.id.signUpButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((TextView)findViewById(R.id.username)).getText().toString();
                String password = ((TextView)findViewById(R.id.password)).getText().toString();
                String confirmPassword = ((TextView)findViewById(R.id.confirmPassword)).getText().toString();
                String email = ((TextView) findViewById(R.id.email)).getText().toString();
                String answer = ((TextView) findViewById(R.id.securityAnswer)).getText().toString();
                String question = ((Spinner) findViewById(R.id.securityQuestion)).getSelectedItem().toString();
                String dateOfBirth = ((TextView) findViewById(R.id.dateOfBirth)).getText().toString();
               // Toast.makeText(getApplicationContext(), question, Toast.LENGTH_LONG).show();

                if(!password.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_LONG).show();

                }else if(username.length() == 0 || password.length() == 0 || password.length() == 0 ||
                        email.length() == 0 || answer.length() == 0 || dateOfBirth.length() == 0 ){
                    Toast.makeText(getApplicationContext(), "Complete all the fields!", Toast.LENGTH_LONG).show();

                }else  {
                    newUser = new User(username,password,email,dateOfBirth,question,answer);
                    boolean seachedUser = false;

                    //String userId = UUID.randomUUID().toString();
                    //mDatabase.child("users").child(username).setValue(newUser);


                    startActivity(new Intent(RegisterActivity.this, MainMenuActivity.class));

                }
            }
        });

    }
}
