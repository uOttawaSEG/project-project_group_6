package com.example.otams;

import android.content.Context;

public class UserRepository {
    private static AppDatabase db;

    public static void init(Context ctx) {
        if (db == null) db = AppDatabase.getInstance(ctx);
    }

    public static void addStudent(String fn, String ln, String email, String pw, String phone, String program)
            throws Exception {
        long id = db.studentDao().insert(new StudentEntity(fn, ln, email, pw, phone, program));
        if (id <= 0) throw new Exception("Insert failed");
    }

    public static void addTutor(String fn, String ln, String email, String pw, String phone, String degree, String courses)
            throws Exception {
        long id = db.tutorDao().insert(new TutorEntity(fn, ln, email, pw, phone, degree, courses));
        if (id <= 0) throw new Exception("Insert failed");
    }

    public static String authenticate(String role, String email, String password) {
        if (role == null) return null;
        String e = email == null ? "" : email.trim();
        String p = password == null ? "" : password.trim();

        switch (role) {
            case "Administrator":
                return (e.equals("admin@otams.ca") && p.equals("admin123")) ? "Administrator" : null;
            case "Student":
                return db.studentDao().login(e, p) != null ? "Student" : null;
            case "Tutor":
                return db.tutorDao().login(e, p) != null ? "Tutor" : null;
            default:
                return null;
        }
    }
}

