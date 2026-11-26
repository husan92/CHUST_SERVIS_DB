package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;

public class TaxiActivity extends Activity {

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

        Button order = new Button(this);
        order.setText("TAKSI CHAQRISH");

        layout.addView(title);
        layout.addView(price);
        layout.addView(km);
        layout.addView(order);

        setContentView(layout);
    }
}
