package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txt = findViewById(R.id.welcomeText);
        Button logout = findViewById(R.id.logoutBtn);

        String role = getIntent().getStringExtra("role");
        if (role == null) role = "User";
        txt.setText(getString(R.string.welcome_message, role));

        logout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}

