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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tutor);

        // Initialize Database
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

        List<TutorEntity> tutors = db.tutorDao().searchByCourse(query);

        if (tutors.isEmpty()) {
            Toast.makeText(this, "No tutors found for this course.", Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(null);
            return;
        }

        List<AvailabilitySlotEntity> availableSlots = new ArrayList<>();

        for (TutorEntity tutor : tutors) {
            List<AvailabilitySlotEntity> slots = db.availabilitySlotDao().getSlotsByTutor(tutor.id);

            for (AvailabilitySlotEntity slot : slots) {
                boolean isBooked = checkIfBooked(tutor.id, slot.date, slot.startTime);

                if (!isBooked) {
                    availableSlots.add(slot);
                }
            }
        }

        if (availableSlots.isEmpty()) {
            Toast.makeText(this, "Tutors found, but no available slots.", Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(null);
        } else {
            Toast.makeText(this, "Found " + availableSlots.size() + " slots!", Toast.LENGTH_SHORT).show();
        }
    }

   private boolean checkIfBooked(int tutorId, String date, String time) {
        List<SessionEntity> sessions = db.sessionDao().getSessionsForTutor(tutorId);
        for (SessionEntity session : sessions) {
            if (session.date.equals(date) && session.time.equals(time) && !"REJECTED".equals(session.status)) {
                return true;
            }
        }
        return false;
    }
}