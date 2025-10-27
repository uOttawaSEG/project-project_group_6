package com.example.otams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AdminInboxActivity extends AppCompatActivity {

    private LinearLayout pendingStudentsContainer;
    private LinearLayout pendingTutorsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inbox);

        UserRepository.init(getApplicationContext());

        pendingStudentsContainer = findViewById(R.id.pendingStudentsContainer);
        pendingTutorsContainer   = findViewById(R.id.pendingTutorsContainer);

        refreshLists();
    }

    private void refreshLists() {
        pendingStudentsContainer.removeAllViews();
        pendingTutorsContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);

        List<StudentEntity> pendingStudents = UserRepository.getPendingStudents();
        for (StudentEntity s : pendingStudents) {
            View row = inflater.inflate(R.layout.row_pending_request, pendingStudentsContainer, false);

            TextView nameText  = row.findViewById(R.id.nameText);
            TextView emailText = row.findViewById(R.id.emailText);
            TextView phoneText = row.findViewById(R.id.phoneText);
            TextView extraText = row.findViewById(R.id.extraText);
            Button approveBtn  = row.findViewById(R.id.approveBtn);
            Button rejectBtn   = row.findViewById(R.id.rejectBtn);

            nameText.setText("Name: " + s.firstName + " " + s.lastName);
            emailText.setText("Email: " + s.email);
            phoneText.setText("Phone: " + s.phone);
            extraText.setText("Program: " + s.program + " | Status: " + s.status);

            approveBtn.setOnClickListener(v -> {
                UserRepository.approveStudent(s.id);
                Toast.makeText(this, "Approved " + s.firstName, Toast.LENGTH_SHORT).show();
                refreshLists();
            });

            rejectBtn.setOnClickListener(v -> {
                UserRepository.rejectStudent(s.id);
                Toast.makeText(this, "Rejected " + s.firstName, Toast.LENGTH_SHORT).show();
                refreshLists();
            });

            pendingStudentsContainer.addView(row);
        }

        List<TutorEntity> pendingTutors = UserRepository.getPendingTutors();
        for (TutorEntity t : pendingTutors) {
            View row = inflater.inflate(R.layout.row_pending_request, pendingTutorsContainer, false);

            TextView nameText  = row.findViewById(R.id.nameText);
            TextView emailText = row.findViewById(R.id.emailText);
            TextView phoneText = row.findViewById(R.id.phoneText);
            TextView extraText = row.findViewById(R.id.extraText);
            Button approveBtn  = row.findViewById(R.id.approveBtn);
            Button rejectBtn   = row.findViewById(R.id.rejectBtn);

            nameText.setText("Name: " + t.firstName + " " + t.lastName);
            emailText.setText("Email: " + t.email);
            phoneText.setText("Phone: " + t.phone);
            extraText.setText("Degree: " + t.degree + "\nCourses: " + t.courses + "\nStatus: " + t.status);

            approveBtn.setOnClickListener(v -> {
                UserRepository.approveTutor(t.id);
                Toast.makeText(this, "Approved " + t.firstName, Toast.LENGTH_SHORT).show();
                refreshLists();
            });

            rejectBtn.setOnClickListener(v -> {
                UserRepository.rejectTutor(t.id);
                Toast.makeText(this, "Rejected " + t.firstName, Toast.LENGTH_SHORT).show();
                refreshLists();
            });

            pendingTutorsContainer.addView(row);
        }
    }
}
