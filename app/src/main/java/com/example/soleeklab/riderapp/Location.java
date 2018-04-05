package com.example.soleeklab.riderapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fath_soleeklab on 1/24/2018.
 */

public class Location implements Serializable {
    @SerializedName("lon")
    private double longtude;
    @SerializedName("lat")
    private double latitude;

    public Location(double longtude, double latitude) {
        this.longtude = longtude;
        this.latitude = latitude;
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
}
