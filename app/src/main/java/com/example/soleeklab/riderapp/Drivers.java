package com.example.soleeklab.riderapp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fath_soleeklab on 3/14/2018.
 */

public class Drivers implements Serializable {
    @SerializedName("driverId")
    String driverId;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private Integer state;
    @SerializedName("last_location")
    private Location lastLocation;
    @SerializedName("locations")
    private ArrayList<Location> locations = new ArrayList<>();

    public Drivers(String id, String name, Integer state, Location lastLocation, ArrayList<Location> locations) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.lastLocation = lastLocation;
        this.locations = locations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public static Drivers fromJson(String json){
        return new Gson().fromJson(json,Drivers.class);
    }
    public static int getIndexById(String id, ArrayList<Drivers> drivers)
    {
        for(Drivers _item : drivers)
        {
            if(_item.getId().equals(id))
                return drivers.indexOf(_item);
        }
        return -1;
    }
}
