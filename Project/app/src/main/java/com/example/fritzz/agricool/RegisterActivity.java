package com.example.fritzz.agricool;
import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.android.volley.*;
import com.google.firebase.database.Logger;

import org.w3c.dom.Text;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.UUID;

import static com.android.volley.Request.Method.GET;

/**
 * Created by Cristi on 3/22/2018.
 */

public class RegisterActivity extends AppCompatActivity{
//    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    User newUser;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button btn = (Button)findViewById(R.id.signUpButton);
        final RequestQueue queue = Volley.newRequestQueue(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final Context app = getApplicationContext();
                String username = ((TextView)findViewById(R.id.username)).getText().toString();
                String password = ((TextView)findViewById(R.id.password)).getText().toString();
                String confirmPassword = ((TextView)findViewById(R.id.confirmPassword)).getText().toString();
                String email = ((TextView) findViewById(R.id.email)).getText().toString();
                String answer = ((TextView) findViewById(R.id.securityAnswer)).getText().toString();
                final String question = ((Spinner) findViewById(R.id.securityQuestion)).getSelectedItem().toString();
                String dateOfBirth = ((TextView) findViewById(R.id.dateOfBirth)).getText().toString();
               // Toast.makeText(getApplicationContext(), question, Toast.LENGTH_LONG).show();

                if(!password.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_LONG).show();

                }else if(username.length() == 0 || password.length() == 0 || password.length() == 0 ||
                        email.length() == 0 || answer.length() == 0 || dateOfBirth.length() == 0 ){
                    Toast.makeText(getApplicationContext(), "Complete all the fields!", Toast.LENGTH_LONG).show();

                }else  {
                   // newUser = new User(username,password,email,dateOfBirth,question,answer,0);
                    //boolean seachedUser = false;

                    //String userId = UUID.randomUUID().toString();
                    //mDatabase.child("users").child(username).setValue(newUser);
                    String serverIp = ((GlobalVariables) getApplication()).getIp();
                    final String url = "http://"+serverIp+"/sql/inserter.php?query=INSERT INTO USERS VALUES(" +
                            "DEFAULT,'"+username+"','"+password+"','"+email+"','"+dateOfBirth+"','"+question+"','"+answer+"',now());";
                    //Tag t = new Tag("url");
                    //Log.d("url:", url);
                    Toast.makeText(app,"kk",Toast.LENGTH_LONG);

                    final StringRequest request = new StringRequest(GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(app,"Regitered! Please log in!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            Log.v("registerurl",url);
                            PrintWriter print = new PrintWriter(System.out);
                            print.print(url);


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(app,"Something went wrong..",Toast.LENGTH_LONG).show();

                        }
                    });
                    final String  urlSearch = "http://"+serverIp+"/sql/selecter.php?" +
                            "query=SELECT * FROM users WHERE username='"+username+"' OR email='"+email+"';";
                    StringRequest requestSearch = new StringRequest(GET, urlSearch, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equals("[]")) {
                                Toast.makeText(getApplicationContext(), "Username or email taken...", Toast.LENGTH_LONG).show();
                            } else {
                                queue.add(request);
                                finish();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Something went wrong...",Toast.LENGTH_LONG).show();
                        }
                    }
                    );
                    queue.add(requestSearch);
                    //startActivity(new Intent(RegisterActivity.this, MainMenuActivity.class));

                }
            }
        });

    }
}
