package com.example.fritzz.agricool;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;

import static com.android.volley.Request.Method.GET;

public class PlantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final GlobalVariables gb = ((GlobalVariables)this.getApplication());
        final String serverIp = gb.getIp();
        final String  url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CROPS;";
        final Context context = this;
        final Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final TableLayout tablePlants = findViewById(R.id.plantsTable);
        tablePlants.setBackgroundColor(Color.LTGRAY);
        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CropsResponse",response);
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT);

                        if(!response.equals("[]")){

                            ObjectMapper mapper = new ObjectMapper();
                            try {
                                Crops [] crops = mapper.readValue(response,Crops[].class);
                                gb.setCrops(crops);
                                for(int i = 0;i < crops.length;i++){
                                    TableRow row = new TableRow(getApplicationContext());
                                    String cropName = crops[i].crop_name;
                                    TextView tv = new TextView(getApplicationContext());
                                    tv.setText(cropName);
                                    tv.setTextSize(24);
                                    row.addView(tv);
                                    TableLayout.LayoutParams tableRowParams=
                                            new TableLayout.LayoutParams
                                                    (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                    tableRowParams.setMargins(0,8,0,0);
                                    row.setLayoutParams(tableRowParams);
                                    tv.setTextColor(Color.WHITE);
                                    if(i%2 == 0){
                                        row.setBackgroundColor(Color.DKGRAY);
                                    } else {
                                        row.setBackgroundColor(Color.GRAY);
                                    }
                                    tablePlants.addView(row);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } else {

                            Toast.makeText(getApplicationContext(), "No plants found!", Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_plant);
                dialog.setTitle("Add Plant");


                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.buttonOk);
                // if button is clicked, close the custom dialog
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogButtonOk.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        String cropName = ((EditText) dialog.findViewById(R.id.cropName)).getText().toString();
                        if(cropName.equals("")){
                            Toast.makeText(context, "Complete all fields!", Toast.LENGTH_SHORT).show();
                        } else {
                            String url = "http://"+serverIp+"/inserter.php?query=INSERT INTO CROPS VALUES("+cropName+");";

                        }
                    }
                });
                dialog.show();
            }
        });
    }

}
