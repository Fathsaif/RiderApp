package com.example.soleeklab.riderapp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fath_soleeklab on 1/23/2018.
 */

public class DriverModel implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("long")
    private double longtude;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("location")
    private Location driverLocation;
    @SerializedName("data")
    private ArrayList<DriverModel> drivers;
    @SerializedName("echo")
    RiderModel riderModel;
    @SerializedName("_source")
    private DriverModel driver;

    public DriverModel(String name, double longtude, double latitude, Location driverLocation, ArrayList<DriverModel> drivers,
                       RiderModel riderModel,DriverModel driver) {
        this.name = name;
        this.longtude = longtude;
        this.latitude = latitude;
        this.driverLocation = driverLocation;
        this.drivers = drivers;
        this.riderModel = riderModel;
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongtude() {
        return longtude;
    }

    public void setLongtude(double longtude) {
        this.longtude = longtude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Location getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(Location driverLocation) {
        this.driverLocation = driverLocation;
    }

    public ArrayList<DriverModel> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<DriverModel> drivers) {
        this.drivers = drivers;
    }

    public RiderModel getRiderModel() {
        return riderModel;
    }

    public void setRiderModel(RiderModel riderModel) {
        this.riderModel = riderModel;
    }

    public DriverModel getDriver() {
        return driver;
    }

    public void setDriver(DriverModel driver) {
        this.driver = driver;
    }

    public static DriverModel fromJson(String json){
        return new Gson().fromJson(json,DriverModel.class);
    }
}
