package com.example.rambooktest;
// Gerong and Lechico

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ReservationActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Spinner periodSpinner;
    private Button decreasePaxButton;
    private Button increasePaxButton;
    private TextView paxNumber;
    private Button bookButton;
    private CheckBox termsConditionsCheckbox;
    private ImageButton backButton;
    private TextView facilityNameTextView;
    private TextView termsConditionsTextView;
    private int paxCount = 1;
    private String selectedDate;

    private static final String PREFS_NAME = "ReservationsPrefs";
    private static final String BOOKINGS_KEY = "BookingsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        facilityNameTextView = findViewById(R.id.facility_name);
        backButton = findViewById(R.id.back_button);
        calendarView = findViewById(R.id.calendar_view);
        periodSpinner = findViewById(R.id.period_spinner);
        decreasePaxButton = findViewById(R.id.button_decrease_pax);
        increasePaxButton = findViewById(R.id.button_increase_pax);
        paxNumber = findViewById(R.id.pax_number);
        termsConditionsCheckbox = findViewById(R.id.terms_conditions_checkbox);
        bookButton = findViewById(R.id.book_button);
        termsConditionsTextView = findViewById(R.id.terms_conditions);

        // initial values and listeners
        setupFacilityName();
        setupCalendar();
        setupPeriodSpinner();
        setupPaxButtons();

        termsConditionsTextView.setOnClickListener(v -> showTermsDialog());

        backButton.setOnClickListener(v -> finish());

        bookButton.setOnClickListener(v -> {
            if (!termsConditionsCheckbox.isChecked()) {
                Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedPeriod = periodSpinner.getSelectedItem().toString();
            String selectedPax = paxNumber.getText().toString();

            // save booking information
            saveBooking(facilityNameTextView.getText().toString(), selectedDate, selectedPeriod, selectedPax);
        });
    }

    private void showTermsDialog() {
        String termsMessage = "By using the Rambook app, you agree to these Terms of Service. All facility bookings are subject to availability and the specific rules of each facility, and users are expected to use the facilities responsibly while adhering to safety guidelines. Rambook is committed to respecting your privacy. You are responsible for maintaining the security of your account, and any unauthorized use may result in account termination. Additionally, Rambook shall not be liable for any indirect, incidental, or consequential damages that may occur as a result of using the app or facilities.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms and Conditions")
                .setMessage(termsMessage)
                .setPositiveButton("OK", null)
                .show();
    }

    private void setupFacilityName() {
        String facilityName = getIntent().getStringExtra("FACILITY_NAME");
        facilityNameTextView.setText("Facility: " + (facilityName != null ? facilityName : "Unknown"));
    }

    private void setupCalendar() {
        Calendar today = Calendar.getInstance();
        calendarView.setMinDate(today.getTimeInMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        selectedDate = dateFormat.format(today.getTime());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);
            selectedDate = dateFormat.format(selectedCalendar.getTime());
        });
    }

    private void setupPeriodSpinner() {
        String[] periods = {"7:30 - 9:30", "9:30 - 11:30", "11:30 - 1:30", "1:30 - 3:30", "3:30 - 5:30", "5:30 - 7:30"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, periods);
        periodSpinner.setAdapter(adapter);
    }

    private void setupPaxButtons() {
        paxNumber.setText(String.valueOf(paxCount));

        decreasePaxButton.setOnClickListener(v -> {
            if (paxCount > 1) {
                paxCount--;
                paxNumber.setText(String.valueOf(paxCount));
            }
        });

        increasePaxButton.setOnClickListener(v -> {
            paxCount++;
            paxNumber.setText(String.valueOf(paxCount));
        });
    }

    private void saveBooking(String facility, String date, String period, String pax) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> bookings = preferences.getStringSet(BOOKINGS_KEY, new HashSet<>());

        // string to be used for notifications activity
        String booking = facility + " - " + date + " - " + period + " - PAX: " + pax;

        // toast after booking
        if (bookings.add(booking)) {
            preferences.edit().putStringSet(BOOKINGS_KEY, bookings).apply();
            Toast.makeText(this, "Reservation confirmed for " + period + " with " + pax + " PAX on " + date, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This booking already exists!", Toast.LENGTH_SHORT).show();
        }
    }
}
