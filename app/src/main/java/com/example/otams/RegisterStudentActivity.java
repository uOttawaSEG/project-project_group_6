package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterStudentActivity extends AppCompatActivity {

    EditText firstName, lastName, email, password, phone, program;
    Button registerBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        firstName = findViewById(R.id.firstName);
        lastName  = findViewById(R.id.lastName);
        email     = findViewById(R.id.email);
        password  = findViewById(R.id.password);
        phone     = findViewById(R.id.phone);
        program   = findViewById(R.id.program);
        registerBtn = findViewById(R.id.registerBtn);
        backBtn     = findViewById(R.id.backBtn);

        registerBtn.setOnClickListener(v -> {
            String fn = firstName.getText().toString().trim();
            String ln = lastName.getText().toString().trim();
            String em = email.getText().toString().trim().toLowerCase();
            String pw = password.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            String pr = program.getText().toString().trim();

            if (TextUtils.isEmpty(fn) || TextUtils.isEmpty(ln) || TextUtils.isEmpty(em) ||
                    TextUtils.isEmpty(pw) || TextUtils.isEmpty(ph) || TextUtils.isEmpty(pr)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // CHECK IF EMAIL ALREADY EXISTS
            if (UserRepository.emailExists(em)) {
                Toast.makeText(this, "This email is already registered", Toast.LENGTH_SHORT).show();
                email.setError("Email already in use");
                return;
            }

            try {
                UserRepository.addStudent(fn, ln, em, pw, ph, pr);
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } catch (Exception ex) {
                Toast.makeText(this, "Registration failed: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}