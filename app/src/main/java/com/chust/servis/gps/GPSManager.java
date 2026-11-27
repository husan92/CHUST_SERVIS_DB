package com.chust.servis.gps;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;

public class GPSManager {

    private LocationManager manager;

    public GPSManager(Context context) {
        manager = (LocationManager)
            context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLocation(Activity activity) {

        if (activity.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            activity.checkSelfPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(
                new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                },
                100
            );
            return null;
        }

        try {
            Location location = manager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            );

            if (location == null) {
                location = manager.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER
                );
            }

            return location;

        } catch (SecurityException e) {
            return null;
        }
    }
}
