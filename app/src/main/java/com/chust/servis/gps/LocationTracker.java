package com.chust.servis.gps;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class LocationTracker implements LocationListener {

    private LocationManager manager;

    public LocationTracker(Context context) {
        manager = (LocationManager)
            context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void start(Activity activity) {

        if (activity.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(
                new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                }, 101
            );
            return;
        }

        try {
            manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000,
                5,
                this
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        manager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(
        String provider, int status, Bundle extras) {
    }
}
