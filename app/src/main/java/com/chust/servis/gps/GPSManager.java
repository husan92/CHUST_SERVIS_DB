package com.chust.servis.gps;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;

public class GPSManager {

    private LocationManager manager;

    public GPSManager(Context context) {
        manager = (LocationManager)
            context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLocation(Activity activity) {

        if (activity.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                100
            );
            return null;
        }

        try {
            return manager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            );
        } catch (Exception e) {
            return null;
        }
    }
}
