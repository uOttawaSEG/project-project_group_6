package com.example.otams;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface SessionDao {

    @Query("SELECT * FROM sessions")
    List<SessionEntity> getAllSessions();

    @Insert
    long insert(SessionEntity session);

    // View sessions for one student, ordered by most recent first
    @Query("SELECT * FROM sessions " +
            "WHERE studentId = :studentId " +
            "ORDER BY date DESC, startTime DESC")
    List<SessionEntity> getSessionsForStudent(int studentId);

    // View sessions for one tutor, ordered by most recent first
    @Query("SELECT * FROM sessions " +
            "WHERE tutorId = :tutorId " +
            "ORDER BY date DESC, startTime DESC")
    List<SessionEntity> getSessionsForTutor(int tutorId);

    // Helper method count overlapping sessions for students
    // Only count sessions that are not cancelled or rejected
    // If greater than 0 throw error since student cannot be double booked
    @Query("SELECT COUNT(*) FROM sessions " +
            "WHERE studentId = :studentId " +
            "AND status IN ('REQUESTED','APPROVED') " +
            "AND date = :date " +
            "AND NOT (endTime <= :startTime OR startTime >= :endTime)")
    int countOverlappingForStudent(int studentId,
                                   String date,
                                   String startTime,
                                   String endTime);

    // Count active sessions attached to a slot toprevent tutors from deleting booked slots
    @Query("SELECT COUNT(*) FROM sessions " +
            "WHERE slotId = :slotId " +
            "AND status IN ('REQUESTED','APPROVED')")
    int countActiveForSlot(int slotId);

    // Update status of a session
    // For example when tutor approves, rejects, or cancels a session
    @Query("UPDATE sessions SET status = :newStatus WHERE id = :sessionId")
    void updateStatus(int sessionId, String newStatus);

    // Update rating after a completed session
    // Student should input rating from 1 to 5, otherwise throw error
    @Query("UPDATE sessions SET tutorRating = :stars WHERE id = :sessionId")
    void updateRating(int sessionId, int stars);


    // Mehtod to get average rating for a tutor
    // Used when showing search results for students
    @Query("SELECT AVG(tutorRating) FROM sessions " +
            "WHERE tutorId = :tutorId AND tutorRating IS NOT NULL")
    Float getAverageRatingForTutor(int tutorId);
}


