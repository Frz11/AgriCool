package com.example.fritzz.agricool;

/**
 * Created by Cristi on 5/4/2018.
 */

public class Crops {
    public int crop_id;
    public String crop_name;
    Crops(int id,String cropName){
        this.crop_id = id;
        this.crop_name = cropName;
    }
    Crops(){}
    Crops(Crops crop){
        this.crop_id = crop.crop_id;
        this.crop_name = crop.crop_name;
    }
}
