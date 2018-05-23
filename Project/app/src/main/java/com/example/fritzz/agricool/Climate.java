package com.example.fritzz.agricool;

public class Climate {
    public int climate_id;
    public String climate_name;
    public float avg_temp,avg_prec;
    Climate(){};
    Climate(int climate_id,String climate_name,float avg_temp,float avg_prec){
        this.climate_id = climate_id;
        this.climate_name = climate_name;
        this.avg_prec = avg_prec;
        this.avg_temp = avg_temp;
    }
}
