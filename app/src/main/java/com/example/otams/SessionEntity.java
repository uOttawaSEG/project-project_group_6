package com.example.otams;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions")
public class SessionEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int studentId;
    public int tutorId;
    public String date;
    public String time;
    public String courseCode;
    public String status; // "PENDING", "APPROVED", "REJECTED", "COMPLETED"
    public float rating;
    public String feedback;

    public SessionEntity(int studentId, int tutorId, String date, String time, String courseCode, String status) {
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.date = date;
        this.time = time;
        this.courseCode = courseCode;
        this.status = status;
        this.rating = 0;
        this.feedback = "";
    }
    
    public SessionEntity() {}
}