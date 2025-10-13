package com.example.otams;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "students", indices = {@Index(value = {"email"}, unique = true)})
public class StudentEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String firstName, lastName, email, password, phone, program;

    public StudentEntity(String firstName, String lastName, String email, String password, String phone, String program) {
        this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.password = password; this.phone = phone; this.program = program;
    }
}

