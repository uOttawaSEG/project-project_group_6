package com.example.otams; // <-- match your project

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TutorDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(TutorEntity t);

    @Query("SELECT * FROM tutors WHERE email = LOWER(:email) AND password = :password LIMIT 1")
    TutorEntity login(String email, String password);

    @Query("SELECT * FROM tutors WHERE email = :email LIMIT 1")
    TutorEntity findByEmail(String email);
    //for Deliverable 2
    @Query("SELECT * FROM tutors WHERE status = 'PENDING'")
    List<TutorEntity> getPending();

    @Query("SELECT * FROM tutors WHERE status = 'REJECTED'")
    List<TutorEntity> getRejected();

    @Query("UPDATE tutors SET status = :newStatus WHERE id = :id")
    void updateStatus(long id, String newStatus);

    @Query("SELECT * FROM tutors WHERE courses LIKE '%' || :courseCode || '%' AND status = 'APPROVED'")
    List<TutorEntity> searchByCourse(String courseCode);
    @Query("SELECT * FROM tutors WHERE id = :id")
    TutorEntity getTutorById(int id);
}


