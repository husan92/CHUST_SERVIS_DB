package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;

import com.chust.servis.gps.GPSManager;
import com.chust.servis.gps.LocationTracker;

public class DeliveryActivity extends Activity {

    private TextView gpsText;
    private GPSManager gpsManager;
    private LocationTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 40, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("BUYURTMA — 12 800 so'm");
        title.setTextSize(23);
        title.setTextColor(Color.BLACK);

        EditText name = new EditText(this);
        name.setHint("Ismingiz");

        EditText phone = new EditText(this);
        phone.setHint("Telefon raqamingiz");
        phone.setInputType(3);

        EditText pickup = new EditText(this);
        pickup.setHint("Olib ketish manzili");

        EditText address = new EditText(this);
        address.setHint("Yetkazish manzili");

        EditText note = new EditText(this);
        note.setHint("Izoh");

        gpsText = new TextView(this);
        gpsText.setText("GPS: hali aniqlanmadi");
        gpsText.setTextSize(16);

        Button gpsButton = new Button(this);
        gpsButton.setText("GPS JOYLASHUVNI ANIQLASH");

        Button order = new Button(this);
        order.setText("BUYURTMA BERISH");

        TextView operator = new TextView(this);
        operator.setText("Operator: +998942947411");
        operator.setTextSize(16);

        layout.addView(title);
        layout.addView(name);
        layout.addView(phone);
        layout.addView(pickup);
        layout.addView(address);
        layout.addView(note);
        layout.addView(gpsText);
        layout.addView(gpsButton);
        layout.addView(order);
        layout.addView(operator);

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
                    102
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
        runOnUiThread(() -> gpsText.setText(
            "Kenglik: " + location.getLatitude() +
            "\nUzunlik: " + location.getLongitude()
        ));
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

        if (requestCode == 102 && hasLocationPermission()) {
            startGPS();
        } else if (requestCode == 102) {
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
