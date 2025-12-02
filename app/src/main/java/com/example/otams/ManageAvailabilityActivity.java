package com.example.otams;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ManageAvailabilityActivity extends AppCompatActivity {

    private TextView tvSelectedDate, tvStartTime, tvEndTime, tvEmptyState;
    private Button btnSelectDate, btnSelectStartTime, btnSelectEndTime, btnCreateSlot, btnBack;
    private CheckBox cbAutoApprove;
    private RecyclerView rvAvailabilitySlots;

    private String selectedDate = "";
    private String selectedStartTime = "";
    private String selectedEndTime = "";

    private int tutorId;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_availability);

        db = AppDatabase.getInstance(getApplicationContext());
        tutorId = getIntent().getIntExtra("TUTOR_ID", -1);

        initViews();
        setupPickers();
        loadMySlots();
    }

    private void initViews() {
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectStartTime = findViewById(R.id.btnSelectStartTime);
        btnSelectEndTime = findViewById(R.id.btnSelectEndTime);
        btnCreateSlot = findViewById(R.id.btnCreateSlot);
        btnBack = findViewById(R.id.btnBack);
        cbAutoApprove = findViewById(R.id.cbAutoApprove);

        rvAvailabilitySlots = findViewById(R.id.rvAvailabilitySlots);
        rvAvailabilitySlots.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> finish());
        btnCreateSlot.setOnClickListener(v -> createSlot());
    }

    private void createSlot() {
        if (selectedDate.isEmpty() || selectedStartTime.isEmpty() || selectedEndTime.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        AvailabilitySlotEntity newSlot = new AvailabilitySlotEntity(
                tutorId, selectedDate, selectedStartTime, selectedEndTime, cbAutoApprove.isChecked()
        );
        db.availabilitySlotDao().insert(newSlot);
        Toast.makeText(this, "Slot Created!", Toast.LENGTH_SHORT).show();
        loadMySlots();
    }

    private void loadMySlots() {
        List<AvailabilitySlotEntity> slots = db.availabilitySlotDao().getSlotsByTutor(tutorId);

        if (slots.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvAvailabilitySlots.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvAvailabilitySlots.setVisibility(View.VISIBLE);

            AvailabilityAdapter adapter = new AvailabilityAdapter(slots, slot -> {
                attemptDeleteSlot(slot);
            });
            rvAvailabilitySlots.setAdapter(adapter);
        }
    }

    private void attemptDeleteSlot(AvailabilitySlotEntity slot) {
        boolean isBooked = checkSlotBooking(slot);

        if (isBooked) {
            Toast.makeText(this, "Error: Cannot delete a slot that has a booked session.", Toast.LENGTH_LONG).show();
        } else {
            db.availabilitySlotDao().delete(slot);
            Toast.makeText(this, "Slot deleted.", Toast.LENGTH_SHORT).show();
            loadMySlots();
        }
    }

    private boolean checkSlotBooking(AvailabilitySlotEntity slot) {
        List<SessionEntity> sessions = db.sessionDao().getSessionsForTutor(tutorId);
        for (SessionEntity s : sessions) {
            if (s.date.equals(slot.date) && s.time.equals(slot.startTime) && !"REJECTED".equals(s.status)) {
                return true;
            }
        }
        return false;
    }

    private void setupPickers() {
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        btnSelectStartTime.setOnClickListener(v -> showTimePicker(true));
        btnSelectEndTime.setOnClickListener(v -> showTimePicker(false));
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    tvSelectedDate.setText(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker(final boolean isStartTime) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    if (isStartTime) {
                        selectedStartTime = time;
                        tvStartTime.setText(time);
                    } else {
                        selectedEndTime = time;
                        tvEndTime.setText(time);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }
}