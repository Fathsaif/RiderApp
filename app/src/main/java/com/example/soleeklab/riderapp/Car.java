package com.example.soleeklab.riderapp;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by fath_soleeklab on 2/25/2018.
 */

public class Car implements Serializable {
    private Marker carMarker ;
    private ArrayList<com.example.soleeklab.riderapp.Location> route;
    private LatLng source;
    private LatLng destination;
    private ArrayList<Long> delayBetweenMovePoints=new ArrayList<>();
    private ArrayList<Long> delayBetweenRotatePoints= new ArrayList<>();
    private Drivers driver;

    public Car(Marker carMarker, ArrayList<com.example.soleeklab.riderapp.Location> route, LatLng source, LatLng destination,
               Drivers driver) {
        this.carMarker = carMarker;
        this.route = route;
        this.source = source;
        this.destination = destination;
        this.driver = driver;
        if (route!=null){
        setDelayBetweenMovePoints();
        setDelayBetweenRotatePoints();
    }
    }

    public LatLng getSource() {return source;}

    public void setSource(LatLng source) {this.source = source;}

    public LatLng getDestination() {return destination;}

    public void setDestination(LatLng destination) {this.destination = destination;}

    public ArrayList<Long> getDelayBetweenMovePoints() {return delayBetweenMovePoints;}

    public Marker getCarMarker() {
        return carMarker;
    }

    public void setCarMarker(Marker carMarker) {
        this.carMarker = carMarker;
    }

    public ArrayList<com.example.soleeklab.riderapp.Location> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<com.example.soleeklab.riderapp.Location> route) {
        this.route = route;
        setDelayBetweenRotatePoints();
        setDelayBetweenMovePoints();
    }

    public Drivers getDriver() {
        return driver;
    }

    public void setDriver(Drivers driver) {
        this.driver = driver;
    }

    private void setDelayBetweenMovePoints() {

        int mLastDuration = 0;
        delayBetweenMovePoints.add((long) mLastDuration);
        for (int j = 0; j < route.size()-1; j++) {
            double duration = getDuration(new LatLng(route.get(j).getLatitude(),route.get(j).getLongtude()),
                    new LatLng(route.get(j+1).getLatitude(),route.get(j+1).getLongtude()));
            mLastDuration +=duration ;
            delayBetweenMovePoints.add((long) mLastDuration);
        }
    }

    private void setDelayBetweenRotatePoints() {

        double mLastDuration = 10;
        for (int j = 0; j < route.size()-1; j++) {

            double currentBear =  bearingBetweenLocations(new LatLng(route.get(j).getLatitude(),route.get(j).getLongtude()),
                    new LatLng(route.get(j+1).getLatitude(),route.get(j+1).getLongtude()));
            mLastDuration=currentBear/100*1000;
            delayBetweenRotatePoints.add((long) mLastDuration);
        }

    }
    public static double bearingBetweenLocations(LatLng latLng1, LatLng latLng2) {
        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;
        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);
        double brng = Math.atan2(y, x);
        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        return brng;
    }

    public static double getDuration(LatLng latLng1, LatLng latLng2) {
        float [] distance = new float[1];
         int speed =80*1000/(60*60);
        Location.distanceBetween(latLng1.latitude,latLng1.longitude,latLng2.latitude,latLng2.longitude,distance);
        double duration = distance[0] / speed*1000;
       // if(duration<5) duration=5;
        return duration;
    }
    public ArrayList<Long> getDelayBetweenRotatePoints() {
        return delayBetweenRotatePoints;
    }
}
