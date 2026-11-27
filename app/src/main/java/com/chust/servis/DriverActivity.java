package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chust.servis.gps.GPSManager;
import com.chust.servis.gps.LocationTracker;

public class DriverActivity extends Activity {

    private TextView status;
    private TextView gpsText;

    private GPSManager gpsManager;
    private LocationTracker tracker;

    private boolean working = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("HAYDOVCHI KABINETI");
        title.setTextSize(24);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);

        status = new TextView(this);
        status.setText("Holat: OFFLINE");
        status.setTextSize(18);

        gpsText = new TextView(this);
        gpsText.setText("GPS: Hali aniqlanmadi");
        gpsText.setTextSize(16);

        Button start = new Button(this);
        start.setText("ISHNI BOSHLASH");

        Button stop = new Button(this);
        stop.setText("ISHNI TUGATISH");

        Button orders = new Button(this);
        orders.setText("BUYURTMALAR");

        layout.addView(title);
        layout.addView(status);
        layout.addView(gpsText);
        layout.addView(start);
        layout.addView(stop);
        layout.addView(orders);

        setContentView(layout);

        gpsManager = new GPSManager(this);
        tracker = new LocationTracker(this);
        tracker.setCallback(location -> showLocation(location));

        start.setOnClickListener(v -> {
            if (hasLocationPermission()) {
                startWork();
            } else {
                requestPermissions(
                    new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    103
                );
            }
        });

        stop.setOnClickListener(v -> stopWork());

        orders.setOnClickListener(v -> {
            status.setText(
                working
                ? "Buyurtmalar serverga ulanishni kutmoqda"
                : "Avval ishni boshlang"
            );
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

    private void startWork() {
        working = true;
        status.setText("Holat: ONLINE");

        Location location = gpsManager.getLocation(this);

        if (location != null) {
            showLocation(location);
        } else {
            gpsText.setText("GPS signalini kutyapmiz...");
        }

        tracker.start(this);
    }

    private void stopWork() {
        working = false;
        tracker.stop();
        status.setText("Holat: OFFLINE");
        gpsText.setText("GPS kuzatuvi to'xtatildi");
    }

    private void showLocation(Location location) {
        runOnUiThread(() -> {
            if (working) {
                gpsText.setText(
                    "Kenglik: " + location.getLatitude() +
                    "\nUzunlik: " + location.getLongitude()
                );
            }
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

        if (requestCode == 103 && hasLocationPermission()) {
            startWork();
        } else if (requestCode == 103) {
            status.setText("GPS uchun ruxsat berilmadi");
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
