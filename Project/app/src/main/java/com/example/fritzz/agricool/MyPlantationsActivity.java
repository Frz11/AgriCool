package com.example.fritzz.agricool;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class MyPlantationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plantations);
        RequestQueue requests = Volley.newRequestQueue(this);
        Button addPlantations = findViewById(R.id.addPlantation);
        addPlantations.setOnClickListener(event -> {finish();startActivity(new Intent(MyPlantationsActivity.this,AddParcelActivity.class)); });
        String user_id = String.valueOf(((GlobalVariables)getApplication()).getThisUser().user_id);
        String serverIp = ((GlobalVariables)getApplication()).getIp();
        String url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM PARCELS WHERE user_id="+user_id+";";
        TableLayout tableParcels = findViewById(R.id.parcelsTable);
        StringRequest getMyPlants = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("tag",response);

                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            Parcel [] parcels = mapper.readValue(response,Parcel[].class);
                            //Crops[] crops = ((GlobalVariables) getApplication()).getSavedCrops();
                            int i = 0;
                            for(Parcel parcel : parcels) {
                                TableRow row = new TableRow(getApplicationContext());
                                TextView tv = new TextView(getApplicationContext());
                                TableRow row2 = new TableRow(getApplicationContext());
                                TextView tv2 = new TextView(getApplicationContext());
                                LinearLayout ll = new LinearLayout(getApplicationContext());
                                LinearLayout ll2 = new LinearLayout(getApplicationContext());
                                final ButtonDelete button = new ButtonDelete(getApplicationContext(), parcel.parcel_id);
                                button.setBackgroundResource(R.drawable.ic_button_delete);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(getApplicationContext(),button.itemId+"",Toast.LENGTH_LONG).show();
                                        final String url = "http://"+serverIp+"/sql/deleter.php?query=DELETE FROM PARCELS WHERE" +
                                                " parcel_id="+button.itemId+";";
                                        StringRequest request = new StringRequest(GET, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                                                Log.v("tag",response);
                                                Log.v("tag",url);

                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getApplicationContext(),"Couldn't delete!",Toast.LENGTH_LONG).show();

                                            }
                                        });
                                        requests.add(request);

                                    }
                                });
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                //lp.setMargins(700,0,0,0);
                                //button.setLayoutParams(lp);
                                String calcUrl = "http://"+serverIp+"/sql/calculate.php?soil_id="+parcel.soil_id+"&climate_id="+parcel.climate_id
                                        +"&crop_id="+parcel.crop_id;
                                StringRequest requestCalc = new StringRequest(Request.Method.GET, calcUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        tv.setText(parcel.parcel_name+"--Size:"+parcel.size);
                                        tv2.setText("Change:"+response);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                requests.add(requestCalc);
                                tv.setTextSize(24);
                                tv2.setTextSize(24);
                                ll.addView(tv);
                                ll.addView(button);
                                ll2.addView(tv2);
                                row.addView(ll);
                                row2.addView(ll2);
                                //row.addView(button);
                                //button.setY(row.getY()/2);
                                //button.setX(row.getX());

                                TableLayout.LayoutParams tableRowParams=
                                        new TableLayout.LayoutParams
                                                (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                tableRowParams.setMargins(0,8,0,0);
                                row.setLayoutParams(tableRowParams);
                                row2.setLayoutParams(tableRowParams);
                                tv.setTextColor(Color.WHITE);
                                tv2.setTextColor(Color.WHITE);
                                i++;
                                if(i%2 == 0){
                                    row.setBackgroundColor(Color.DKGRAY);
                                    row2.setBackgroundColor(Color.DKGRAY);
                                } else {
                                    row.setBackgroundColor(Color.GRAY);
                                    row2.setBackgroundColor(Color.GRAY);
                                }
                                tableParcels.addView(row);
                                tableParcels.addView(row2);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, event -> {

        });
        requests.add(getMyPlants);
    }
}
