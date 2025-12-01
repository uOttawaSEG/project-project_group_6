package com.example.otams;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// empty for now just trying to get code to run
public class ViewSessionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);
        // will need to show upcoming or past sessions depending on SESSION_TYPE extra
    }
}

