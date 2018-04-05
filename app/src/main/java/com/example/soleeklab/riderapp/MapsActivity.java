package com.example.soleeklab.riderapp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;
    private boolean isMarkerRotating = false;
    private final LatLng mDefaultLocation = new LatLng(29.974335, 31.279846);
    String TAG = "saif";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNTIuMTc0LjIyLjE4OC95YW1hbS9wdWJsaWMvYXBpL2F1dGgvdmVyaWZ5IiwiaWF0IjoxNTIwOTUyNTc1LCJleHAiOjM3NTIwOTUyNTc1LCJuYmYiOjE1MjA5NTI1NzUsImp0aSI6IjEzWHdnMmVaTWhjazQzRjgiLCJzdWIiOjEyLCJwcnYiOiIyNTkwOGUxMDQzYjNlYWUzYmQ1ZTUxNzllMzgwNWExOTBjZjdmOGE1In0.-7uFSkaXfME1DAlu3S4Hv2J_X07Z4buEUpcmMV9uIig";
    String driverChannel = "driverLocation";
    String riderChannel = "userLocation";
    private Socket mSocket;
    private Boolean isConnected = false;
    ArrayList<com.example.soleeklab.riderapp.Location> route1 = new ArrayList<>();
    ArrayList<com.example.soleeklab.riderapp.Location> route2 = new ArrayList<>();
    ArrayList<com.example.soleeklab.riderapp.Location> route3 = new ArrayList<>();
    ArrayList<com.example.soleeklab.riderapp.Location> newRoute = new ArrayList<>();

    private ArrayList<Car> cars = new ArrayList<>();
    ArrayList<Drivers> drivers = new ArrayList<>();
    String langEt;
    String latEt;
    int l = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initialize();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        getLocationPermission();
        getDeviceLocation();
    }

    public void setAddBtn1(View view) {

       /* new Handler().post(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 11; i++) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }

                    }, 2000*i);
                }
            }
        });*/

        Drivers driver1 = new Drivers("01", "driver1", 0,
                new com.example.soleeklab.riderapp.Location(route1.get(0).getLatitude(), route1.get(0).getLongtude()), route1);
        createMarker(driver1, 0);
    }

    public void initialize() {
        double count = 0.0003;
        for (int i = 0; i < 32; i++) {
            LatLng source = new LatLng(31.279846 + (count * i), 29.974335);
            com.example.soleeklab.riderapp.Location location =
                    new com.example.soleeklab.riderapp.Location(source.latitude, source.longitude);
            if (i < 11)
                route1.add(location);
            else if (i>10&&i<21)route2.add(location);
            else route3.add(location);
        }
    }

    public void setAdd2(View view) {
        l = 2;
        Drivers driver1 = new Drivers("02", "driver2", 0,
                new com.example.soleeklab.riderapp.Location(route2.get(0).getLatitude(), route2.get(0).getLongtude()), route2);
        createMarker(driver1, 1);
    }

    public void move1(View view) {
        ArrayList<com.example.soleeklab.riderapp.Location> route = cars.get(0).getRoute();
        Log.d("array-old", cars.get(0).getRoute().size() + "");
        for (int i = 0; i <= route2.size(); i++) {
            if (i==0){
                //route.add(0,route1.get(route1.size()-1));
                newRoute.add(cars.get(0).getRoute().get(cars.get(0).getRoute().size()-1));
                Log.d("compa",route1.get(route1.size()-1).getLongtude()+"" );
            }
            else
            {
                newRoute.add(route2.get(i-1));
                Log.d("compa1",route2.get(i-1).getLongtude()+"" );
                route.add(route2.get(i-1));
            }
        }
        cars.get(0).setRoute(route);
        Log.d("array-new", cars.get(0).getRoute().size() + "");
    }
    public void move2 (View view){
        ArrayList<com.example.soleeklab.riderapp.Location> route = cars.get(0).getRoute();
        newRoute.clear();
        Log.d("array-old1", cars.get(0).getRoute().size() + "");
        for (int i = 0; i <= route3.size(); i++) {
            if (i==0){
                //route.add(0,route1.get(route1.size()-1));

                newRoute.add(cars.get(0).getRoute().get(cars.get(0).getRoute().size()-1));
                Log.d("compa",route1.get(route1.size()-1).getLongtude()+"" );
            }
            else
            {
                newRoute.add(route3.get(i-1));
                Log.d("compa1",route3.get(i-1).getLongtude()+"" );
                route.add(route3.get(i-1));
            }
            cars.get(0).setRoute(route);
            Log.d("array-new", cars.get(0).getRoute().size() + "");
    }}
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            latEt = task.getResult().getLatitude() + "";
                            langEt = task.getResult().getLongitude() + "";
                            mMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude())).title("soleek lab"));
                            Log.d(TAG, "Current location");
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG + "Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    public void moveCar(final int size, final int index) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (cars.get(index).getRoute() == null) return;
                final ArrayList<Long> delayBetweenMovePoints = cars.get(index).getDelayBetweenMovePoints();
                for (int i = 0; i < size - 1; i++) {
                    Log.d("remove", "removed" + i + "/" + cars.get(index).getRoute().size());
                    final int finalI = i;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            final ArrayList<Long> delayBetweenRotatePoints = cars.get(index).getDelayBetweenRotatePoints();
                            final LatLng beginLatLng = new LatLng(cars.get(index).getRoute().get(finalI).getLatitude(),
                                    cars.get(index).getRoute().get(finalI).getLongtude());
                            final LatLng endLatLng = new LatLng(cars.get(index).getRoute().get(finalI + 1).getLatitude(),
                                    cars.get(index).getRoute().get(finalI + 1).getLongtude());
                            mMap.addMarker(new MarkerOptions().position(new LatLng(endLatLng.latitude,
                                   endLatLng.longitude)).title("soleek lab"));
                            double rotateDuration = delayBetweenRotatePoints.get(finalI);
                            final double moveDuration = Car.getDuration(beginLatLng, endLatLng);
                            if (rotateDuration > moveDuration) rotateDuration = moveDuration - 50;
                            final double finalRotateDuration = rotateDuration;
                            Log.d("loc-tag", cars.get(index).getDelayBetweenMovePoints().size()
                                    + "index= " + finalI + "//size= "
                                    + cars.get(index).getRoute().size());

                            rotateMarker(cars.get(index).getCarMarker(), beginLatLng, endLatLng, finalRotateDuration, cars.get(index).getCarMarker().getRotation());
                            animateCarMove(cars.get(index).getCarMarker(), beginLatLng, endLatLng);
                            if (finalI == size - 2) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (cars.get(index).getRoute().size() > size + 1) {
                                            Log.d("compare", "old="+cars.get(index).getRoute().get(cars.get(index).getRoute().size()-1).getLongtude()+"new="
                                                    +newRoute.get(0).getLongtude()+"/" +
                                                    ""+route2.size());
                                            //newRoute.add(0,new com.example.soleeklab.riderapp.Location(endLatLng.latitude,endLatLng.longitude));
                                            cars.get(index).getRoute().clear();
                                            cars.get(index).setRoute(newRoute);
                                            moveCar(cars.get(index).getRoute().size(), index);
                                        } else {
                                           final CountDownTimer timer= new CountDownTimer(10 * 1000, 1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    if (cars.get(index).getRoute().size()>size+1){
                                                        cars.get(index).getRoute().clear();
                                                        cars.get(index).setRoute(newRoute);
                                                        moveCar(cars.get(index).getRoute().size(), index);
                                                        cancel();
                                                    }
                                                }

                                                @Override
                                                public void onFinish() {
                                                    cars.get(index).getCarMarker().remove();
                                                    Log.d("remove", "removed");
                                                }
                                            }.start();

                                        }
                                    }
                                }, (long) Car.getDuration(beginLatLng,endLatLng));

                            }
                        }
                    }, cars.get(index).getDelayBetweenMovePoints().get(i));
                }
            }
        });
    }

    private void animateCarMove(final Marker marker, final LatLng beginLatLng, final LatLng endLatLng) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) ((float) elapsed
                        / Car.getDuration(beginLatLng, endLatLng)));
                double lng = t * endLatLng.longitude + (1 - t)
                        * beginLatLng.longitude;
                double lat = t * endLatLng.latitude + (1 - t)
                        * beginLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                }
            }
        });
    }

    //method to rotate car in every turn point.
    private void rotateMarker(final Marker marker, final LatLng beginLatLng, final LatLng endLatLng, final double duration, final float startRotation) {
        //if (!isMarkerRotating) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float toRotation = (float) Car.bearingBetweenLocations(beginLatLng, endLatLng);
        Log.d(TAG + "distand", " duration= " + Car.getDuration(beginLatLng, endLatLng) + " bear=" + Car.bearingBetweenLocations(beginLatLng, endLatLng));
        Log.d(TAG + marker.getTitle(), toRotation + " start = " + startRotation + " duration= " + Car.getDuration(beginLatLng, endLatLng));
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                isMarkerRotating = true;
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) (elapsed / duration));
                float rot = t * toRotation + (1 - t) * startRotation;
                marker.setRotation(-rot > 180 ? rot / 2 : rot);
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    isMarkerRotating = false;
                }
            }
        });
    }

    protected void createMarker(Drivers driver, int index) {
        Log.d(TAG + "drivers", drivers.size() + "");
        if (mMap == null) return;
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(driver.getLastLocation().getLatitude(), driver.getLastLocation().getLongtude()))
                .anchor(0.5f, 0.5f)
                .title(driver.getName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        marker.setRotation(180);
        Car car;
        if (index == 0) car = new Car(marker, route1, null, null, driver);
        else car = new Car(marker, route2, null, null, driver);
        cars.add(car);
        moveCar(car.getRoute().size(), index);
    }
}
