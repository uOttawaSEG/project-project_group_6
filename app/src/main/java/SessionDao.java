package com.example.otams;
import android.se.omapi.Session;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface SessionDao {
    // Example: Method to get all sessions
    @Query("SELECT * FROM session_table") // Replace 'session_table' with your actual table name
    List<SessionEntity> getAllSessions();

    // Example: Method to insert a session
    @Insert void insert(SessionEntity session);

    // Add other methods you need for database operations (e.g., @Update, @Delete)
}

