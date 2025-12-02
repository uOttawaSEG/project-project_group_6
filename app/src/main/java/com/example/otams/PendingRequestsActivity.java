package com.example.otams;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PendingRequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private int tutorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions); // Reusing layout for simplicity

        db = AppDatabase.getInstance(this);
        tutorId = getIntent().getIntExtra("TUTOR_ID", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // TextView title = findViewById(R.id.pageTitle);
        // title.setText("Pending Requests");

        loadPendingRequests();
    }

    private void loadPendingRequests() {
        List<SessionEntity> allSessions = db.sessionDao().getSessionsForTutor(tutorId);
        List<SessionEntity> pendingList = new ArrayList<>();

        for (SessionEntity s : allSessions) {
            if ("PENDING".equals(s.status)) {
                pendingList.add(s);
            }
        }

        if (pendingList.isEmpty()) {
            Toast.makeText(this, "No pending requests.", Toast.LENGTH_SHORT).show();
        }

        // We need a specific adapter to show Approve/Reject buttons
        // For simplicity, we can use StudentSessionAdapter logic BUT act differently on click
        // Or create a 'RequestAdapter'. Let's reuse StudentSessionAdapter and treat 'cancel' as 'reject'.
        // Ideally, you'd make a custom adapter, but for time, we will use the existing click listener logic.

        StudentSessionAdapter adapter = new StudentSessionAdapter(pendingList, db, new StudentSessionAdapter.OnActionClickListener() {
            @Override
            public void onCancelClick(SessionEntity session) {
                // In this context, clicking the main item/button approves it
                session.status = "APPROVED";
                db.sessionDao().update(session);
                Toast.makeText(PendingRequestsActivity.this, "Session Approved", Toast.LENGTH_SHORT).show();
                loadPendingRequests();
            }

            @Override
            public void onRateClick(SessionEntity session) {
                // Use this as Reject button if needed, or simply add long-press logic
                session.status = "REJECTED";
                db.sessionDao().update(session);
                Toast.makeText(PendingRequestsActivity.this, "Session Rejected", Toast.LENGTH_SHORT).show();
                loadPendingRequests();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}