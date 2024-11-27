package com.example.rambooktest;
// Gerong and Lechico

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotificationActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private static final String PREFS_NAME = "ReservationsPrefs";
    private static final String BOOKINGS_KEY = "BookingsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        listView = findViewById(R.id.list_view);
        TextView notificationTitle = findViewById(R.id.notification_title);

        // load data from the adapter
        ArrayList<String> bookings = loadBookings();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookings);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> loadBookings() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> bookingsSet = preferences.getStringSet(BOOKINGS_KEY, new HashSet<>());

        Log.d("NotificationActivity", "Loaded bookings: " + bookingsSet);

        return new ArrayList<>(bookingsSet);
    }
}
