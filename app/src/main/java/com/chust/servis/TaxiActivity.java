package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;
import android.view.View;

import com.chust.servis.gps.GPSManager;
import com.chust.servis.gps.LocationTracker;

public class TaxiActivity extends Activity {

    private TextView gpsText;
    private GPSManager gpsManager;
    private LocationTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);

        TextView title = new TextView(this);
        title.setText("TAKSI BUYURTMASI");
        title.setTextSize(24);
        title.setTextColor(Color.BLACK);

        TextView price = new TextView(this);
        price.setText("Boshlang'ich narx: 1 000 so'm");
        price.setTextSize(18);

        TextView km = new TextView(this);
        km.setText("Har bir km: 2 500 so'm");
        km.setTextSize(18);

        gpsText = new TextView(this);
        gpsText.setText("Manzil aniqlanmoqda...");
        gpsText.setTextSize(16);

        Button gpsButton = new Button(this);
        gpsButton.setText("GPS JOYLASHUVNI ANIQLASH");

        Button order = new Button(this);
        order.setText("TAKSI CHAQRISH");

        layout.addView(title);
        layout.addView(price);
        layout.addView(km);
        layout.addView(gpsText);
        layout.addView(gpsButton);
        layout.addView(order);

        setContentView(layout);

        gpsManager = new GPSManager(this);
        tracker = new LocationTracker(this);

        tracker.setCallback(location -> showLocation(location));

        gpsButton.setOnClickListener(v -> {
            if (hasLocationPermission()) {
                startGPS();
            } else {
                requestPermissions(
                    new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    101
                );
            }
        });
    }

    private boolean hasLocationPermission() {
        return checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        checkSelfPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void startGPS() {
        Location location = gpsManager.getLocation(this);

        if (location != null) {
            showLocation(location);
        } else {
            gpsText.setText("GPS сигналини кутяпмиз...");
        }

        tracker.start(this);
    }

    private void showLocation(Location location) {
        runOnUiThread(() -> {
            gpsText.setText(
                "Kenglik: " + location.getLatitude() +
                "\nUzunlik: " + location.getLongitude()
            );
        });
    }

    @Override
    public void onRequestPermissionsResult(
        int requestCode,
        String[] permissions,
        int[] grantResults
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        );

        if (requestCode == 101 && hasLocationPermission()) {
            startGPS();
        } else if (requestCode == 101) {
            gpsText.setText("GPS учун рухсат берилмади");
        }
    }

    @Override
    protected void onDestroy() {
        if (tracker != null) {
            tracker.stop();
        }
        super.onDestroy();
    }
}
