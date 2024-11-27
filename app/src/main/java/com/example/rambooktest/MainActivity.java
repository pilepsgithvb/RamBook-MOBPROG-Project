package com.example.rambooktest;
// Gerong and Lechico

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        TextView forgotPasswordText = findViewById(R.id.forgot_password_text);
        Button loginButton = findViewById(R.id.login_button);

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidCredentials(username, password)) {
                    Intent intent = new Intent(MainActivity.this, FacilityActivity.class);
                    startActivity(intent);
                } else {
                    // need yung data below for the login details
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // login requirements
    private boolean isValidCredentials(String username, String password) {
        return (username.equals("Turnoy Legazpi") || username.equals("tlegazpi@student.apc.edu.ph"))
                && password.equals("password");
    }
}
