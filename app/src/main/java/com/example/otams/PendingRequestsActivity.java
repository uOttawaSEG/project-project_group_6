package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions); // Reusing this generic layout is fine

        db = AppDatabase.getInstance(this);
        tutorId = getIntent().getIntExtra("TUTOR_ID", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView title = findViewById(R.id.pageTitle);
        title.setText("Pending Requests");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PendingRequestsActivity.this, TutorDashboardActivity.class);
            intent.putExtra("TUTOR_ID", tutorId);
            intent.putExtra("TUTOR_NAME", "Tutor");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

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

        // Use the new PendingRequestsAdapter
        PendingRequestsAdapter adapter = new PendingRequestsAdapter(pendingList, db, new PendingRequestsAdapter.OnActionListener() {
            @Override
            public void onApprove(SessionEntity session) {
                session.status = "APPROVED";
                db.sessionDao().update(session);
                Toast.makeText(PendingRequestsActivity.this, "Session Approved", Toast.LENGTH_SHORT).show();
                loadPendingRequests(); // Refresh list
            }

            @Override
            public void onReject(SessionEntity session) {
                session.status = "REJECTED";
                db.sessionDao().update(session);
                Toast.makeText(PendingRequestsActivity.this, "Session Rejected", Toast.LENGTH_SHORT).show();
                loadPendingRequests(); // Refresh list
            }
        });

        recyclerView.setAdapter(adapter);
    }
}