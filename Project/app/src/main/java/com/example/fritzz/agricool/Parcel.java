package com.example.fritzz.agricool;

import android.app.Application;
import android.app.VoiceInteractor;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Method;

public class Parcel  {
    public int parcel_id;
    public float size;
    public int user_id;
    public int soil_id;
    public int climate_id;
    public int crop_id;
    public float success;
    private float ph_min,ph_max,ph,temp_min,temp_max,temp,prec_min,prec_max,prec;
    public String parcel_name;
    Parcel(){}
    Parcel(int parcel_id,float size,int user_id,int crop_id,int soil_id,int climate_id,String parcel_name){
        this.parcel_id = parcel_id;
        this.size = size;
        this.user_id = user_id;
        this.crop_id = crop_id;
        this.soil_id = soil_id;
        this.climate_id = climate_id;
        this.parcel_name = parcel_name;
        this.success = success;
    }
    public synchronized void getCalcParams(Context context){
        RequestQueue requests = Volley.newRequestQueue(context);
        String serverIp = ((GlobalVariables) context).getIp();
        String url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM SOILS WHERE soil_id="+soil_id+";";
        int succesRate = 100;
        final Soil[][] soil = new Soil[1][1];
        final boolean[] oks = new boolean[3];
        StringRequest getSoil = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper obm = new ObjectMapper();
                        try {
                            soil[0] = obm.readValue(response,Soil[].class);
                            Log.v("tag",soil[0][0].soil_name);
                            oks[0] = true;
                            ph = soil[0][0].ph;
                            Log.v("tag",Double.toString(ph));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requests.add(getSoil);
        url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CROPS WHERE CROP_ID="+crop_id+";";
        final Crops[][] crop = new Crops[1][1];
        StringRequest getCrops = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper obm = new ObjectMapper();
                        try {
                            crop[0] = obm.readValue(response,Crops[].class);
                            ph_min = crop[0][0].ph_min;
                            ph_max = crop[0][0].ph_max;
                            temp_min = crop[0][0].temp_min;
                            temp_max = crop[0][0].temp_max;
                            prec_min = crop[0][0].prec_min;
                            prec_max = crop[0][0].prec_max;
                            oks[1] = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requests.add(getCrops);
        final Climate[][] climate = {new Climate[1]};
        url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CLIMATES WHERE CLIMATE_ID ="+climate_id+";";
        StringRequest getClimate =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper obm = new ObjectMapper();
                        try {
                            climate[0] = obm.readValue(response,Climate[].class);
                            temp = climate[0][0].avg_temp;
                            prec = climate[0][0].avg_prec;
                            oks[2] = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        }
        });
        requests.add(getClimate);

    }
    public float calculate() throws InterruptedException {
//        wait(2000);
        float succ = (float) 100.00;
        float precError = 0;
        float tempError = 0;
        float phError = 0;
        if(prec < prec_min){
            precError = Math.abs(prec-prec_min);
        } else if (prec > prec_max){
            precError = Math.abs(prec-prec_max);
        }

        if(temp < temp_min){
            tempError = Math.abs(temp-temp_min);
        } else if (temp > temp_max){
            tempError = Math.abs(temp-temp_max);
        }

        if(ph < ph_min){
            phError = Math.abs(ph-ph_min);
        } else if (ph > ph_max){
            phError = Math.abs(ph-ph_max);
        }
        succ -= (1/3*phError*phError+1/3*tempError*tempError+1/3*precError*precError);
        Log.v("tag",Double.toString(ph_min)+Double.toString(ph_max)+Double.toString(ph));
        if(succ < 0){
            return 0;
        }
        return Math.abs(succ);
    }

}

