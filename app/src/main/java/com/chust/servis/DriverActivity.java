package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DriverActivity extends Activity {

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

        TextView status = new TextView(this);
        status.setText("Holat: Ish boshlanmagan");
        status.setTextSize(18);

        Button start = new Button(this);
        start.setText("ISHNI BOSHLASH");

        Button stop = new Button(this);
        stop.setText("ISHNI TUGATISH");

        Button orders = new Button(this);
        orders.setText("BUYURTMALAR");

        layout.addView(title);
        layout.addView(status);
        layout.addView(start);
        layout.addView(stop);
        layout.addView(orders);

        setContentView(layout);
    }
}
