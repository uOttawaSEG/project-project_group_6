package com.example.otams; // <-- use whatever your project already uses

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(StudentEntity s);

    @Query("SELECT * FROM students WHERE email = LOWER(:email) AND password = :password LIMIT 1")
    StudentEntity login(String email, String password);

    @Query("SELECT * FROM students WHERE email = :email LIMIT 1")
    StudentEntity findByEmail(String email);
    //for Deliverable 2
    @Query("SELECT * FROM students WHERE status = 'PENDING'")
    List<StudentEntity> getPending();

    @Query("SELECT * FROM students WHERE status = 'REJECTED'")
    List<StudentEntity> getRejected();

    @Query("UPDATE students SET status = :newStatus WHERE id = :id")
    void updateStatus(long id, String newStatus);
}


