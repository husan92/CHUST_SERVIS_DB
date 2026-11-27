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
import android.view.View;

import com.chust.servis.gps.GPSManager;
import com.chust.servis.gps.LocationTracker;

public class TaxiActivity extends Activity {

    private TextView gpsText;
    private TextView statusText;
    private EditText destination;
    private Button confirmButton;

    private GPSManager gpsManager;
    private LocationTracker tracker;

    private Location currentLocation;
    private boolean locationConfirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 40, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("TAKSI CHAQIRISH");
        title.setTextSize(25);
        title.setTextColor(Color.BLACK);

        TextView price = new TextView(this);
        price.setText("Boshlang'ich narx: 1 000 so'm");
        price.setTextSize(17);

        TextView km = new TextView(this);
        km.setText("Har bir km: 2 500 so'm");
        km.setTextSize(17);

        TextView pickupTitle = new TextView(this);
        pickupTitle.setText("1. QAYERDAN OLAMIZ?");
        pickupTitle.setTextSize(18);

        gpsText = new TextView(this);
        gpsText.setText("GPS joylashuv hali aniqlanmadi");
        gpsText.setTextSize(16);

        Button gpsButton = new Button(this);
        gpsButton.setText("GPS ORQALI ANIQLASH");

        confirmButton = new Button(this);
        confirmButton.setText("JOYLASHUVNI TASDIQLASH");
        confirmButton.setEnabled(false);

        TextView destinationTitle = new TextView(this);
        destinationTitle.setText("2. QAYERGA BORAMIZ?");
        destinationTitle.setTextSize(18);

        destination = new EditText(this);
        destination.setHint("Boradigan manzilni yozing");

        Button order = new Button(this);
        order.setText("TAKSI CHAQIRISH");

        statusText = new TextView(this);
        statusText.setText("");
        statusText.setTextSize(16);

        layout.addView(title);
        layout.addView(price);
        layout.addView(km);
        layout.addView(pickupTitle);
        layout.addView(gpsButton);
        layout.addView(gpsText);
        layout.addView(confirmButton);
        layout.addView(destinationTitle);
        layout.addView(destination);
        layout.addView(order);
        layout.addView(statusText);

        setContentView(layout);

        gpsManager = new GPSManager(this);
        tracker = new LocationTracker(this);

        tracker.setCallback(location -> showLocation(location));

        gpsButton.setOnClickListener(v -> {
            locationConfirmed = false;
            confirmButton.setEnabled(false);
            gpsText.setText("GPS aniqlanmoqda...");

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

        confirmButton.setOnClickListener(v -> {
            if (currentLocation != null) {
                locationConfirmed = true;
                confirmButton.setText("JOYLASHUV TASDIQLANDI");
                gpsText.setText("Olish joyi tasdiqlandi");
                statusText.setText("");
            }
        });

        order.setOnClickListener(v -> {
            String address = destination.getText().toString().trim();

            if (!locationConfirmed) {
                statusText.setText("Avval GPS joylashuvni tasdiqlang!");
                return;
            }

            if (address.isEmpty()) {
                statusText.setText("Boradigan manzilni yozing!");
                return;
            }

            statusText.setText(
                "Buyurtma ma'lumotlari tayyor.\n" +
                "Serverga ulanish keyingi bosqichda qo'shiladi."
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

    private void startGPS() {
        Location location = gpsManager.getLocation(this);

        if (location != null) {
            showLocation(location);
        }

        tracker.start(this);
    }

    private void showLocation(Location location) {
        runOnUiThread(() -> {
            currentLocation = location;

            if (!locationConfirmed) {
                gpsText.setText(
                    "GPS topildi:\n" +
                    "Kenglik: " + location.getLatitude() +
                    "\nUzunlik: " + location.getLongitude()
                );

                confirmButton.setEnabled(true);
                confirmButton.setText("JOYLASHUVNI TASDIQLASH");
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

        if (requestCode == 101 && hasLocationPermission()) {
            startGPS();
        } else if (requestCode == 101) {
            gpsText.setText("GPS uchun ruxsat berilmadi");
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
