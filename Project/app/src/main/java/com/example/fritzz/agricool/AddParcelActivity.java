package com.example.fritzz.agricool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddParcelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel);
        Spinner cropSpinner = findViewById(R.id.plantsSpinner);
        Spinner soilSpinner = findViewById(R.id.soilsSpinner);
        Spinner climateSpinner = findViewById(R.id.climatesSpinner);

        //get all grops
        final List<String> crops = new ArrayList<String>();
        final ArrayAdapter<String> adapaterCrops = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                crops);
        RequestQueue requests = Volley.newRequestQueue(this);
        String serverIp = ((GlobalVariables) getApplication()).getIp();
        String url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CROPS;";
        StringRequest getCrops = new StringRequest(Request.Method.GET, url, response -> {
            if(!response.equals("[]")){
                Log.v("tag:",response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Crops[] cropsObjects = mapper.readValue(response,Crops[].class);
                    for(Crops cropObj : cropsObjects){
                        crops.add(cropObj.crop_name);
                    }
                    adapaterCrops.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                Toast.makeText(getApplicationContext(), "NO PLANTS FOUND!", Toast.LENGTH_LONG).show();
                finish();
            }
        }, error -> {

        });

        requests.add(getCrops);
        cropSpinner.setAdapter(adapaterCrops);
        adapaterCrops.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //get soils

        final List<String> soils = new ArrayList<String>();
        final ArrayAdapter<String> adapterSoil = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                soils);
        url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM SOILS;";
        StringRequest getSoils = new StringRequest(Request.Method.GET, url, response -> {
            if(!response.equals("[]")){
                Log.v("tag:",response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Soil[] soilObjects = mapper.readValue(response,Soil[].class);
                    for(Soil soilObj : soilObjects){
                        soils.add(soilObj.soil_name);
                    }
                    adapterSoil.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                Toast.makeText(getApplicationContext(), "NO SOILS FOUND!", Toast.LENGTH_LONG).show();
                finish();
            }
        }, error -> {

        });

        requests.add(getSoils);
        soilSpinner.setAdapter(adapterSoil);
        adapterSoil.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //climates
        final List<String> climates = new ArrayList<String>();
        final ArrayAdapter<String> adapterClimates = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                climates);
        url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CLIMATES;";
        StringRequest getClimates = new StringRequest(Request.Method.GET, url, response -> {
            if(!response.equals("[]")){
                Log.v("tag:",response);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Climate[] climateObjects = mapper.readValue(response,Climate[].class);
                    for(Climate climateObj : climateObjects){
                        climates.add(climateObj.climate_name);
                    }
                    adapterClimates.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                Toast.makeText(getApplicationContext(), "NO CLIMATES FOUND!", Toast.LENGTH_LONG).show();
                finish();
            }
        }, error -> {

        });

        requests.add(getClimates);
        climateSpinner.setAdapter(adapterClimates);
        adapterClimates.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Button cancel = findViewById(R.id.cancelButton);
        cancel.setOnClickListener(event -> finish());
        Button add = findViewById(R.id.addButton);
        add.setOnClickListener((View event) -> {

                    String crop_name = ((Spinner) findViewById(R.id.plantsSpinner)).getSelectedItem().toString();
                    String size = ((EditText) findViewById(R.id.size)).getText().toString();
                    String soil_name = ((Spinner) findViewById(R.id.soilsSpinner)).getSelectedItem().toString();
                    String climate_name = ((Spinner) findViewById(R.id.climatesSpinner)).getSelectedItem().toString();
                    String user_id = String.valueOf(((GlobalVariables) getApplication()).getThisUser().user_id);
                    String parcel_name = ((EditText) findViewById(R.id.parcelName)).getText().toString();
                    String addPlantUrl = "http://"+serverIp+"/sql/addParcel.php?size="+size+"&crop_name="+crop_name+
                            "&user_id="+user_id+"&soil_name="+soil_name+"&climate_name="+climate_name+
                            "&parcel_name="+parcel_name;
                   // Toast.makeText(getApplicationContext(),addPlantUrl,Toast.LENGTH_LONG).show();
                    Log.v("tag",addPlantUrl);
                    StringRequest addPlantRequest = new StringRequest(Request.Method.GET,addPlantUrl,new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                           // if(response.equals("[{result:Inserted,error:}]")){
                                Toast.makeText(getApplicationContext(),"Parcel added !",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(AddParcelActivity.this,MyPlantationsActivity.class));
                            //} else {
                             //   Log.v("tag",response);
                            //}

                    }
                    },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Something went wrong...",Toast.LENGTH_LONG).show();
                    }
                    });
                    requests.add(addPlantRequest);
                }
        );

    }
}
