package com.example.otams;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchTutorActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchBtn, backBtn;
    private RecyclerView recyclerView;
    private AppDatabase db;
    private int studentId;
    private SearchResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tutor);

        UserRepository.init(getApplicationContext());
        db = AppDatabase.getInstance(this);

        studentId = getIntent().getIntExtra("STUDENT_ID", -1);

        initViews();

        searchBtn.setOnClickListener(v -> performSearch());
        backBtn.setOnClickListener(v -> finish());
    }

    private void initViews() {
        searchInput = findViewById(R.id.searchInput);
        searchBtn = findViewById(R.id.searchBtn);
        backBtn = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a course code", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Find Tutors teaching this course
        List<TutorEntity> tutors = db.tutorDao().searchByCourse(query);

        if (tutors.isEmpty()) {
            Toast.makeText(this, "No tutors found for this course.", Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(null);
            return;
        }

        List<AvailabilitySlotEntity> availableSlots = new ArrayList<>();

        // 2. Filter slots: Must NOT be booked by anyone else
        for (TutorEntity tutor : tutors) {
            // Logically fetch slots for this specific tutor
            List<AvailabilitySlotEntity> slots = db.availabilitySlotDao().getSlotsByTutor(tutor.id);

            for (AvailabilitySlotEntity slot : slots) {
                // Check if this slot is already booked in the sessions table
                boolean isBooked = checkIfBookedGlobal(tutor.id, slot.date, slot.startTime);

                if (!isBooked) {
                    availableSlots.add(slot);
                }
            }
        }

        if (availableSlots.isEmpty()) {
            Toast.makeText(this, "Tutors found, but no available slots.", Toast.LENGTH_LONG).show();
            recyclerView.setAdapter(null);
        } else {
            // 3. Set Adapter
            adapter = new SearchResultsAdapter(availableSlots, db, slot -> {
                attemptBooking(slot, query);
            });
            recyclerView.setAdapter(adapter);
        }
    }

    private void attemptBooking(AvailabilitySlotEntity slot, String courseCode) {
        // DELIVERABLE 4: CONFLICT CHECK
        // Check if THIS student already has a session at this time
        if (hasStudentConflict(slot.date, slot.startTime)) {
            Toast.makeText(this, "Conflict! You already have a session at this time.", Toast.LENGTH_LONG).show();
            return;
        }

        // Create Session
        SessionEntity session = new SessionEntity(
                studentId,
                slot.tutorId,
                slot.date,
                slot.startTime,
                courseCode,
                slot.autoApprove ? "APPROVED" : "PENDING"
        );

        db.sessionDao().insert(session);
        Toast.makeText(this, "Session Requested!", Toast.LENGTH_SHORT).show();

        // Refresh to remove the booked slot
        performSearch();
    }

    // Check if ANYONE has booked this slot
    private boolean checkIfBookedGlobal(int tutorId, String date, String time) {
        List<SessionEntity> sessions = db.sessionDao().getSessionsForTutor(tutorId);
        for (SessionEntity session : sessions) {
            // Match Date AND Time AND ensure it wasn't rejected
            if (session.date.equals(date) && session.time.equals(time) && !"REJECTED".equals(session.status)) {
                return true;
            }
        }
        return false;
    }

    // Check if current student has a session at this time
    private boolean hasStudentConflict(String date, String time) {
        List<SessionEntity> mySessions = db.sessionDao().getSessionsForStudent(studentId);
        for (SessionEntity s : mySessions) {
            if (s.date.equals(date) && s.time.equals(time) && !"REJECTED".equals(s.status)) {
                return true;
            }
        }
        return false;
    }
}