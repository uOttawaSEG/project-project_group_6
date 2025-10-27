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

public class AdminRejectActivity extends AppCompatActivity {

    private LinearLayout rejectedStudentsContainer;
    private LinearLayout rejectedTutorsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rejected);

        UserRepository.init(getApplicationContext());

        rejectedStudentsContainer = findViewById(R.id.rejectedStudentsContainer);
        rejectedTutorsContainer   = findViewById(R.id.rejectedTutorsContainer);

        refreshLists();
    }

    private void refreshLists() {
        rejectedStudentsContainer.removeAllViews();
        rejectedTutorsContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);

        List<StudentEntity> rejectedStudents = UserRepository.getRejectedStudents();
        for (StudentEntity s : rejectedStudents) {
            View row = inflater.inflate(R.layout.row_rejected_request, rejectedStudentsContainer, false);

            TextView nameText  = row.findViewById(R.id.nameText);
            TextView emailText = row.findViewById(R.id.emailText);
            TextView phoneText = row.findViewById(R.id.phoneText);
            TextView extraText = row.findViewById(R.id.extraText);
            Button approveBtn  = row.findViewById(R.id.approveBtn);

            nameText.setText("Name: " + s.firstName + " " + s.lastName);
            emailText.setText("Email: " + s.email);
            phoneText.setText("Phone: " + s.phone);
            extraText.setText("Program: " + s.program + " | Status: " + s.status);

            approveBtn.setOnClickListener(v -> {
                UserRepository.approveStudent(s.id);
                Toast.makeText(this, "Approved " + s.firstName, Toast.LENGTH_SHORT).show();
                refreshLists();
            });

            rejectedStudentsContainer.addView(row);
        }

        List<TutorEntity> rejectedTutors = UserRepository.getRejectedTutors();
        for (TutorEntity t : rejectedTutors) {
            View row = inflater.inflate(R.layout.row_rejected_request, rejectedTutorsContainer, false);

            TextView nameText  = row.findViewById(R.id.nameText);
            TextView emailText = row.findViewById(R.id.emailText);
            TextView phoneText = row.findViewById(R.id.phoneText);
            TextView extraText = row.findViewById(R.id.extraText);
            Button approveBtn  = row.findViewById(R.id.approveBtn);

            nameText.setText("Name: " + t.firstName + " " + t.lastName);
            emailText.setText("Email: " + t.email);
            phoneText.setText("Phone: " + t.phone);
            extraText.setText("Degree: " + t.degree + "\nCourses: " + t.courses + "\nStatus: " + t.status);

            approveBtn.setOnClickListener(v -> {
                UserRepository.approveTutor(t.id);
                Toast.makeText(this, "Approved " + t.firstName, Toast.LENGTH_SHORT).show();
                refreshLists();
            });

            rejectedTutorsContainer.addView(row);
        }
    }
}


