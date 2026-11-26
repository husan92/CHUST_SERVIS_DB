package com.chust.servis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;

public class DeliveryActivity extends Activity {

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
        layout.addView(order);
        layout.addView(operator);

        setContentView(layout);
    }
}
