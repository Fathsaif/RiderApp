package com.example.soleeklab.riderapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fath_soleeklab on 1/23/2018.
 */

public class RiderModel implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("long")
    private String longtude;
    @SerializedName("lat")
    private String latitude;

    public RiderModel(String name, String longtude, String latitude) {
        this.name = name;
        this.longtude = longtude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
