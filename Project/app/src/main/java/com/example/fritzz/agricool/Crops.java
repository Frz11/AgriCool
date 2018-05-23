package com.example.fritzz.agricool;

/**
 * Created by Cristi on 5/4/2018.
 */

public class Crops {
    public int crop_id,user_id;
    public String crop_name;
    public float ph_min,ph_max,temp_min,temp_max,prec_min,prec_max;
    Crops(int id,String cropName,float ph_min,float ph_max,float prec_min,float prec_max,float temp_min,float temp_max){
        this.crop_id = id;
        this.crop_name = cropName;
        this.user_id = user_id;
        this.ph_min = ph_min;
        this.ph_max = ph_max;
        this.prec_min = prec_min;
        this.prec_max = prec_max;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }
    Crops(){}
    Crops(Crops crop){
        this.crop_id = crop.crop_id;
        this.crop_name = crop.crop_name;
        this.user_id = crop.user_id;
        this.temp_min = crop.temp_min;
        this.temp_max = crop.temp_max;
        this.prec_max = crop.prec_max;
        this.prec_min = crop.prec_min;
        this.ph_max = crop.ph_max;
        this.ph_min = crop.ph_min;
    }

}
