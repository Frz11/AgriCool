package com.example.fritzz.agricool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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
import java.util.Comparator;

public class BestPlantationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_plantations);
        final TableLayout tableBest = findViewById(R.id.tableBestPPlantations);
        String serverIp = ((GlobalVariables)getApplication()).getIp();
        String url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM PARCELS;";
        RequestQueue requests = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        ObjectMapper obm = new ObjectMapper();
                        if(!response.equals("[]")){
                            try {
                                Parcel [] parcels = obm.readValue(response,Parcel[].class);
                                Arrays.sort(parcels, new Comparator<Parcel>() {
                                    @Override
                                    public int compare(Parcel o1, Parcel o2) {

                                        return 0;
                                    }
                                });
                            } catch (IOException e) {

                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"No parcels found!",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
