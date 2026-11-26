package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdminActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("OPERATOR BOSHQARUVI");
        title.setTextSize(24);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);

        Button taxi = new Button(this);
        taxi.setText("TAKSI BUYURTMALARI");

        Button delivery = new Button(this);
        delivery.setText("YETKAZIB BERISH BUYURTMALARI");

        Button drivers = new Button(this);
        drivers.setText("HAYDOVCHILAR");

        layout.addView(title);
        layout.addView(taxi);
        layout.addView(delivery);
        layout.addView(drivers);

        setContentView(layout);
    }
}
