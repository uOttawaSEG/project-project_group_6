package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TutorDashboardActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button btnManageAvailability, btnUpcomingSessions, btnPastSessions, btnPendingRequests, btnLogout;
    private int currentTutorId;
    private String tutorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_dashboard);

        // Initialize views
        initViews();

        // Get tutor data from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TUTOR_ID") && intent.hasExtra("TUTOR_NAME")) {
            currentTutorId = intent.getIntExtra("TUTOR_ID", -1);
            tutorName = intent.getStringExtra("TUTOR_NAME");

            if (currentTutorId == -1 || tutorName == null) {
                Toast.makeText(this, "Error: Tutor data missing", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Set welcome message
            welcomeText.setText("Welcome, " + tutorName + "!");
        } else {
            Toast.makeText(this, "Error: No tutor data provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Setup click listeners
        setupClickListeners();
    }

    private void initViews() {
        welcomeText = findViewById(R.id.welcomeText);
        btnManageAvailability = findViewById(R.id.btnManageAvailability);
        btnUpcomingSessions = findViewById(R.id.btnUpcomingSessions);
        btnPastSessions = findViewById(R.id.btnPastSessions);
        btnPendingRequests = findViewById(R.id.btnPendingRequests);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void setupClickListeners() {
        btnManageAvailability.setOnClickListener(v -> {
            Intent intent = new Intent(TutorDashboardActivity.this, ManageAvailabilityActivity.class);
            intent.putExtra("TUTOR_ID", currentTutorId);
            startActivity(intent);
        });

        btnUpcomingSessions.setOnClickListener(v -> {
            Intent intent = new Intent(TutorDashboardActivity.this, ViewSessionsActivity.class);
            intent.putExtra("TUTOR_ID", currentTutorId);
            intent.putExtra("SESSION_TYPE", "upcoming");
            startActivity(intent);
        });

        btnPastSessions.setOnClickListener(v -> {
            Intent intent = new Intent(TutorDashboardActivity.this, ViewSessionsActivity.class);
            intent.putExtra("TUTOR_ID", currentTutorId);
            intent.putExtra("SESSION_TYPE", "past");
            startActivity(intent);
        });

        btnPendingRequests.setOnClickListener(v -> {
            Intent intent = new Intent(TutorDashboardActivity.this, PendingRequestsActivity.class);
            intent.putExtra("TUTOR_ID", currentTutorId);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            // Go back to main activity
            Intent intent = new Intent(TutorDashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}