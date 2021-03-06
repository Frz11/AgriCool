package com.example.fritzz.agricool;

import android.app.Application;

/**
 * Created by Cristi on 5/3/2018.
 */

public class GlobalVariables extends Application {
    private String serverIp;
    private User thisUser;
    private Crops[] savedCrops;
    public void setIp(String ip){
        this.serverIp = ip;
    }
    public String getIp(){
        return this.serverIp;
    }
    public void setThisUser(User user){
        this.thisUser = new User(user);
    }
    public User getThisUser(){return this.thisUser;}
    public void setCrops(Crops[] crops){
        savedCrops = crops;
    }
    public Crops[] getSavedCrops(){return savedCrops;}
}
