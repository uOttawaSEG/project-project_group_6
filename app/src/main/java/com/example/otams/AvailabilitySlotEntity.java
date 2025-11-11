package com.example.otams;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "availability_slots",
        foreignKeys = @ForeignKey(entity = TutorEntity.class,
                parentColumns = "id",
                childColumns = "tutorId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"tutorId"})})
public class AvailabilitySlotEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int tutorId;
    public String date; // Format: "YYYY-MM-DD"
    public String startTime; // Format: "HH:MM"
    public String endTime; // Format: "HH:MM"
    public boolean autoApprove;

    public AvailabilitySlotEntity(int tutorId, String date, String startTime, String endTime, boolean autoApprove) {
        this.tutorId = tutorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.autoApprove = autoApprove;
    }
}