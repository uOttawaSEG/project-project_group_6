package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {

    private int studentId;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);


        studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        studentName = getIntent().getStringExtra("STUDENT_NAME");

        TextView welcomeText = findViewById(R.id.welcomeText);
        if (studentName != null) {
            welcomeText.setText("Welcome, " + studentName + "!");
        }

        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnMySessions = findViewById(R.id.btnMySessions);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnLogout = findViewById(R.id.btnLogout);


        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchTutorActivity.class);
            intent.putExtra("STUDENT_ID", studentId);
            startActivity(intent);
        });


        btnMySessions.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentSessionsActivity.class);
            intent.putExtra("STUDENT_ID", studentId);
            intent.putExtra("MODE", "UPCOMING");
            startActivity(intent);
        });


        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentSessionsActivity.class);
            intent.putExtra("STUDENT_ID", studentId);
            intent.putExtra("MODE", "HISTORY");
            startActivity(intent);
        });


        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}