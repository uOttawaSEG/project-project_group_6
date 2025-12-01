package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView roleText;
    private Button pendingRequestsBtn;
    private Button rejectedRequestsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        UserRepository.init(getApplicationContext());
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close current activity
            }
        });
        roleText = findViewById(R.id.roleText);
        pendingRequestsBtn = findViewById(R.id.pendingRequestsBtn);
        rejectedRequestsBtn = findViewById(R.id.rejectedRequestsBtn);

        String role = getIntent().getStringExtra("role");
        if (role == null) role = "Unknown";

        roleText.setText("You are logged in as: " + role);

        if ("Administrator".equalsIgnoreCase(role)) {
            pendingRequestsBtn.setVisibility(View.VISIBLE);
            rejectedRequestsBtn.setVisibility(View.VISIBLE);

            pendingRequestsBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, AdminInboxActivity.class)));

            rejectedRequestsBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, AdminRejectActivity.class)));
        } else {
            pendingRequestsBtn.setVisibility(View.GONE);
            rejectedRequestsBtn.setVisibility(View.GONE);

        }
    }
    private void goToDashboard(String role){
        Intent intent;

        switch (role) {
            case "Tutor":
                intent = new Intent(this, TutorDashboardActivity.class);
                break;
//            case "Student":
//              intent = new Intent(this, StudentDashboardActivity.class);
//              break;
        }
    }

}
