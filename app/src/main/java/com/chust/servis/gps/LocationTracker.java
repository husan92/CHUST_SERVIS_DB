
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

    public interface LocationCallback {
        void onLocationChanged(Location location);
    }

    private LocationManager manager;
    private LocationCallback callback;

    public LocationTracker(Context context) {
        manager = (LocationManager)
            context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setCallback(LocationCallback callback) {
        this.callback = callback;
    }

    public void start(Activity activity) {

        boolean fine = activity.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        boolean coarse = activity.checkSelfPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        if (!fine && !coarse) {
            activity.requestPermissions(
                new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                },
                101
            );
            return;
        }

        try {
            String provider = fine
                ? LocationManager.GPS_PROVIDER
                : LocationManager.NETWORK_PROVIDER;

            manager.requestLocationUpdates(
                provider,
                3000,
                5,
                this
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (manager != null) {
            manager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (callback != null) {
            callback.onLocationChanged(location);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(
        String provider, int status, Bundle extras
    ) {
    }
}
