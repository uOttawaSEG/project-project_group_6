package com.example.otams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Spinner roleSpinner;
    private Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DB-backed repository once
        UserRepository.init(getApplicationContext());

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        roleSpinner = findViewById(R.id.roleSpinner);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        loginBtn.setOnClickListener(v -> {
            String role = roleSpinner.getSelectedItem().toString();
            String email = emailInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String okRole = UserRepository.authenticate(role, email, pass);
            if (okRole != null) goToWelcome(okRole);
            else Toast.makeText(this, "Invalid credentials for selected role", Toast.LENGTH_SHORT).show();
        });

        registerBtn.setOnClickListener(v -> {
            String role = roleSpinner.getSelectedItem().toString();
            if ("Student".equals(role)) {
                startActivity(new Intent(this, RegisterStudentActivity.class));
            } else if ("Tutor".equals(role)) {
                startActivity(new Intent(this, RegisterTutorActivity.class));
            } else {
                Toast.makeText(this, "You cannot register as an administrator, please choose Student or Tutor to register.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToWelcome(String role) {
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("role", role);
        startActivity(i);
    }
}
