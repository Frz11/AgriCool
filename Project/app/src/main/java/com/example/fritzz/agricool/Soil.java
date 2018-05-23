package com.example.fritzz.agricool;

public class Soil {
    public int soil_id;
    public String soil_name;
    public float ph;
    Soil(){}
    Soil(int soil_id,String soil_name,float ph){
        this.soil_id = soil_id;
        this.soil_name = soil_name;
        this.ph = ph;
    }
}
