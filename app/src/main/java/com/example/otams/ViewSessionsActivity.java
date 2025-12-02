package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewSessionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView pageTitle;
    private Button backBtn;
    private AppDatabase db;
    private int tutorId;
    private String sessionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);

        db = AppDatabase.getInstance(this);
        tutorId = getIntent().getIntExtra("TUTOR_ID", -1);
        sessionType = getIntent().getStringExtra("SESSION_TYPE");

        pageTitle = findViewById(R.id.pageTitle);
        backBtn = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewSessionsActivity.this, TutorDashboardActivity.class);
            intent.putExtra("TUTOR_ID", tutorId);
            intent.putExtra("TUTOR_NAME", "Tutor");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        setupTitle();
        loadSessions();
    }

    private void setupTitle() {
        if ("upcoming".equals(sessionType)) {
            pageTitle.setText("Upcoming Sessions");
        } else {
            pageTitle.setText("Past Sessions");
        }
    }

    private void loadSessions() {
        List<SessionEntity> allSessions = db.sessionDao().getSessionsForTutor(tutorId);
        List<SessionEntity> filteredList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
        Date now = new Date();

        for (SessionEntity s : allSessions) {
            try {
                Date sessionDate = sdf.parse(s.date + " " + s.time);
                if (sessionDate == null) continue;

                boolean isPast = sessionDate.before(now);

                if ("upcoming".equals(sessionType) && !isPast && !"REJECTED".equals(s.status)) {
                    filteredList.add(s);
                } else if ("past".equals(sessionType) && isPast) {
                    filteredList.add(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // FIX IS HERE: We pass 'true' to indicate this is a Tutor View
        StudentSessionAdapter adapter = new StudentSessionAdapter(filteredList, db, true, new StudentSessionAdapter.OnActionClickListener() {
            @Override
            public void onCancelClick(SessionEntity session) {
                // Tutor cancels an upcoming session -> Rejected
                session.status = "REJECTED";
                db.sessionDao().update(session);
                loadSessions(); // Refresh list
            }

            @Override
            public void onRateClick(SessionEntity session) {
                // Tutors do not rate sessions, so we leave this empty
            }
        });

        recyclerView.setAdapter(adapter);
    }
}