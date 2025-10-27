package com.example.otams;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "students", indices = {@Index(value = {"email"}, unique = true)})
public class StudentEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstName, lastName, email, password, phone, program;

    //registration status for D2
    public String status; // "PENDING' / "APPROVED" / "REJECTED"
    public StudentEntity(String firstName, String lastName, String email, String password, String phone, String program, String status) {
        this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.password = password; this.phone = phone; this.program = program; this.status = status;

    }
}

