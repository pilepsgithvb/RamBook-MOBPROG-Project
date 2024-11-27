package com.example.rambooktest;
// Gerong and Lechico

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FacilityActivity extends AppCompatActivity {

    private EditText searchBar;
    private ImageButton searchButton;
    private ImageButton notificationButton;
    private LinearLayout apcCourtLayout;
    private LinearLayout auditoriumLayout;
    private LinearLayout cafeteriaLayout;
    private LinearLayout mph1Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        TextView userName = findViewById(R.id.user_name);
        userName.setText("Turnoy K. Legazpi");

        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);
        notificationButton = findViewById(R.id.notification_button);

        // notif activity button
        notificationButton.setOnClickListener(v -> {
            Intent intent = new Intent(FacilityActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().toLowerCase();
            performSearch(query);
        });

        apcCourtLayout = findViewById(R.id.facility_apc_court);
        auditoriumLayout = findViewById(R.id.facility_auditorium);
        cafeteriaLayout = findViewById(R.id.facility_cafeteria);
        mph1Layout = findViewById(R.id.facility_mph1);

        // onclick listeners to move to next activity
        setFacilityClickListener(apcCourtLayout, "APC Court");
        setFacilityClickListener(auditoriumLayout, "Auditorium");
        setFacilityClickListener(cafeteriaLayout, "Cafeteria");
        setFacilityClickListener(mph1Layout, "MPH1");
    }

    private void setFacilityClickListener(LinearLayout layout, String facilityName) {
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(FacilityActivity.this, ReservationActivity.class);
            intent.putExtra("FACILITY_NAME", facilityName); // add facility name under RESERVATIONS
            startActivity(intent);
        });
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            // pag clear yung searchbar all facilities will show
            apcCourtLayout.setVisibility(View.VISIBLE);
            auditoriumLayout.setVisibility(View.VISIBLE);
            cafeteriaLayout.setVisibility(View.VISIBLE);
            mph1Layout.setVisibility(View.VISIBLE);
            return;
        }

        boolean found = false;

        // magsshow lang yung specfic facility based on kung ano yung nakalagay sa search bar
        if (query.contains("apc court")) {
            apcCourtLayout.setVisibility(View.VISIBLE);
            auditoriumLayout.setVisibility(View.GONE);
            cafeteriaLayout.setVisibility(View.GONE);
            mph1Layout.setVisibility(View.GONE);
            found = true;
        } else if (query.contains("auditorium")) {
            apcCourtLayout.setVisibility(View.GONE);
            auditoriumLayout.setVisibility(View.VISIBLE);
            cafeteriaLayout.setVisibility(View.GONE);
            mph1Layout.setVisibility(View.GONE);
            found = true;
        } else if (query.contains("cafeteria")) {
            apcCourtLayout.setVisibility(View.GONE);
            auditoriumLayout.setVisibility(View.GONE);
            cafeteriaLayout.setVisibility(View.VISIBLE);
            mph1Layout.setVisibility(View.GONE);
            found = true;
        } else if (query.contains("mph1")) {
            apcCourtLayout.setVisibility(View.GONE);
            auditoriumLayout.setVisibility(View.GONE);
            cafeteriaLayout.setVisibility(View.GONE);
            mph1Layout.setVisibility(View.VISIBLE);
            found = true;
        } else {
            apcCourtLayout.setVisibility(View.GONE);
            auditoriumLayout.setVisibility(View.GONE);
            cafeteriaLayout.setVisibility(View.GONE);
            mph1Layout.setVisibility(View.GONE);
            Toast.makeText(this, "No facilities found for: " + query, Toast.LENGTH_SHORT).show();
        }
        if (found) {
            Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
        }
    }
}
