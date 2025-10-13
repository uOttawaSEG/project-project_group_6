package com.example.otams;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TutorDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(TutorEntity t);

    @Query("SELECT * FROM tutors WHERE email = LOWER(:email) AND password = :password LIMIT 1")
    TutorEntity login(String email, String password);
}

