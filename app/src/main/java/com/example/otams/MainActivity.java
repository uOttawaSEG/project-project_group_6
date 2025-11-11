package com.example.otams;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner roleSpinner;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserRepository.init(getApplicationContext());

        roleSpinner   = findViewById(R.id.roleSpinner);
        emailInput    = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn      = findViewById(R.id.loginBtn);
        registerBtn   = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(v -> handleLogin());
        registerBtn.setOnClickListener(v -> handleRegister());
    }

    private void handleLogin() {
        String role  = roleSpinner.getSelectedItem().toString();
        String email = emailInput.getText().toString();
        String pw    = passwordInput.getText().toString();

        UserRepository.AuthResult res = UserRepository.authenticate2(role, email, pw);

        if (!res.ok) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("Administrator".equals(res.role)) {
            goToWelcome("Administrator");
            return;
        }

        if ("APPROVED".equals(res.status)) {
            if ("Tutor".equals(res.role)) {
                TutorEntity tutor = UserRepository.getTutorByEmail(email);
                if (tutor != null) {
                    Intent intent = new Intent(MainActivity.this, TutorDashboardActivity.class);
                    intent.putExtra("TUTOR_ID", tutor.id);
                    intent.putExtra("TUTOR_NAME", tutor.firstName + " " + tutor.lastName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error: Tutor not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                goToWelcome(res.role);
            }
        } else if ("PENDING".equals(res.status)) {
            showInfoDialog("Your account is still pending approval by the administrator.");
        } else if ("REJECTED".equals(res.status)) {
            showInfoDialog("Your registration was rejected. Please call the OTAMS admin at 613-555-0123.");
        } else {
            showInfoDialog("Unknown status: " + res.status);
        }
    }

    private void handleRegister() {
        String role = roleSpinner.getSelectedItem().toString();

        if ("Student".equalsIgnoreCase(role)) {
            startActivity(new Intent(this, RegisterStudentActivity.class));
        } else if ("Tutor".equalsIgnoreCase(role)) {
            startActivity(new Intent(this, RegisterTutorActivity.class));
        } else if ("Administrator".equalsIgnoreCase(role)) {
            Toast.makeText(this, "Administrators are predefined. Please log in.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please choose Student or Tutor.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToWelcome(String role) {
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("role", role);
        startActivity(i);
    }

    private void showInfoDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Login Status")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}