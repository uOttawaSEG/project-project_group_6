package com.example.otams;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tutors", indices = {@Index(value = {"email"}, unique = true)})
public class TutorEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstName, lastName, email, password, phone, degree, courses;

    //registration status for D2
    public String status; // "PENDING" / "APPROVED" / "REJECTED"


    public TutorEntity(String firstName, String lastName, String email,
                       String password, String phone, String degree, String courses, String status) {
        this.firstName = firstName; this.lastName = lastName;
        this.email = email; this.password = password;
        this.phone = phone; this.degree = degree; this.courses = courses; this.status = status;
    }
}

