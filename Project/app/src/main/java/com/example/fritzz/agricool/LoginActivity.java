package com.example.fritzz.agricool;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.*;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {


//    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final GlobalVariables gb = ((GlobalVariables)this.getApplication());
        super.onCreate(savedInstanceState);
        final String serverIp = ((GlobalVariables)this.getApplication()).getIp();

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
        final RequestQueue queue = Volley.newRequestQueue(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = ((TextView) findViewById(R.id.username)).getText().toString();
                final String password = ((TextView) findViewById(R.id.password)).getText().toString();

                final String  url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"';";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                if(!response.equals("[]")){

                                    ObjectMapper mapper = new ObjectMapper();
                                    try {
                                        User[] user = mapper.readValue(response,User[].class);
                                        gb.setThisUser(user[0]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    finish();
                                    startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));

                                } else {

                                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();

                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });


    }

}
