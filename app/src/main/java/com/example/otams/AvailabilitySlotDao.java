package com.example.otams;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AvailabilitySlotDao {
    @Insert
    long insert(AvailabilitySlotEntity slot);

    @Delete
    void delete(AvailabilitySlotEntity slot);

    @Query("SELECT * FROM availability_slots WHERE tutorId = :tutorId ORDER BY date, startTime")
    List<AvailabilitySlotEntity> getSlotsByTutor(int tutorId);

    @Query("SELECT * FROM availability_slots WHERE tutorId = :tutorId AND date = :date AND " +
            "((startTime <= :startTime AND endTime > :startTime) OR " +
            "(startTime < :endTime AND endTime >= :endTime) OR " +
            "(startTime >= :startTime AND endTime <= :endTime))")
    List<AvailabilitySlotEntity> getOverlappingSlots(int tutorId, String date, String startTime, String endTime);

    @Query("SELECT * FROM availability_slots WHERE id = :slotId")
    AvailabilitySlotEntity getSlotById(int slotId);
}