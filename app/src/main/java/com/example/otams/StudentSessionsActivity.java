package com.example.otams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentSessionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView pageTitle;
    private Button backBtn;
    private AppDatabase db;
    private int studentId;
    private String mode; // "UPCOMING" or "HISTORY"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sessions);

        UserRepository.init(getApplicationContext());
        db = AppDatabase.getInstance(this);

        studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        mode = getIntent().getStringExtra("MODE");

        pageTitle = findViewById(R.id.pageTitle);
        backBtn = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn.setOnClickListener(v -> finish());

        loadSessions();
    }

    private void loadSessions() {
        List<SessionEntity> allSessions = db.sessionDao().getSessionsForStudent(studentId);
        List<SessionEntity> filteredSessions = new ArrayList<>();

        // Helper date formatters
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
        Date now = new Date();

        if ("UPCOMING".equals(mode)) {
            pageTitle.setText("Upcoming Sessions");
            for (SessionEntity s : allSessions) {
                // Combine date and time to check if it's in the future
                try {
                    Date sessionDate = sdf.parse(s.date + " " + s.time);
                    if (sessionDate != null && sessionDate.after(now)) {
                        filteredSessions.add(s);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            pageTitle.setText("Session History");
            for (SessionEntity s : allSessions) {
                try {
                    Date sessionDate = sdf.parse(s.date + " " + s.time);
                    // Include past sessions or explicitly completed ones
                    if (sessionDate != null && sessionDate.before(now)) {
                        filteredSessions.add(s);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        StudentSessionAdapter adapter = new StudentSessionAdapter(filteredSessions, db, new StudentSessionAdapter.OnActionClickListener() {
            @Override
            public void onCancelClick(SessionEntity session) {
                handleCancellation(session);
            }

            @Override
            public void onRateClick(SessionEntity session) {
                if ("HISTORY".equals(mode)) {
                    showRatingDialog(session);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void handleCancellation(SessionEntity session) {
        // Logic: Can cancel if PENDING or if APPROVED and > 24 hours away
        if ("REJECTED".equals(session.status)) {
            Toast.makeText(this, "Session is already rejected.", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
        try {
            Date sessionDate = sdf.parse(session.date + " " + session.time);
            Date now = new Date();

            // Calculate difference in hours
            long diff = sessionDate.getTime() - now.getTime();
            long hours = diff / (60 * 60 * 1000);

            if ("PENDING".equals(session.status)) {
                deleteSession(session);
            } else if ("APPROVED".equals(session.status)) {
                if (hours >= 24) {
                    deleteSession(session);
                } else {
                    Toast.makeText(this, "Cannot cancel: Session is in less than 24h.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error parsing date.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSession(SessionEntity session) {
        db.sessionDao().delete(session);
        Toast.makeText(this, "Session cancelled.", Toast.LENGTH_SHORT).show();
        loadSessions(); // Refresh list
    }

    private void showRatingDialog(SessionEntity session) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rate Tutor");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("Enter rating (1-5)");
        builder.setView(input);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String ratingStr = input.getText().toString();
            if (!ratingStr.isEmpty()) {
                float rating = Float.parseFloat(ratingStr);
                if (rating >= 1 && rating <= 5) {
                    session.rating = rating;
                    session.status = "COMPLETED"; // Mark as completed if not already
                    db.sessionDao().update(session);
                    Toast.makeText(this, "Rating submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}