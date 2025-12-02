package com.example.otams;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface SessionDao {
    @Insert
    long insert(SessionEntity session);

    @Update
    void update(SessionEntity session);

    @Delete
    void delete(SessionEntity session);

    @Query("SELECT * FROM sessions WHERE studentId = :studentId")
    List<SessionEntity> getSessionsForStudent(int studentId);

    @Query("SELECT * FROM sessions WHERE tutorId = :tutorId")
    List<SessionEntity> getSessionsForTutor(int tutorId);

    @Query("SELECT * FROM sessions WHERE id = :id")
    SessionEntity getSessionById(int id);

    @Query("SELECT * FROM sessions")
    List<SessionEntity> getAllSessions();

    @Query("SELECT AVG(rating) FROM sessions WHERE tutorId = :tutorId AND rating > 0")
    Float getAverageRating(int tutorId);
}