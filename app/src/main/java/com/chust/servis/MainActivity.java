package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 60, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("CHUST SERVIS");
        title.setTextSize(28);
        title.setTextColor(Color.BLACK);

        Button taxi = new Button(this);
        taxi.setText("TAKSI");

        Button delivery = new Button(this);
        delivery.setText("BUYURTMA");

        layout.addView(title);
        layout.addView(taxi);
        layout.addView(delivery);

        setContentView(layout);
    }
}
