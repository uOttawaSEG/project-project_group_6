package com.example.otams;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(StudentEntity s);

    @Query("SELECT * FROM students WHERE email = LOWER(:email) AND password = :password LIMIT 1")
    StudentEntity login(String email, String password);
}

