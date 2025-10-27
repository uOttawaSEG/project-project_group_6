package com.example.otams;

import android.content.Context;

import java.util.List;

public class UserRepository {

    private static AppDatabase db;

    // Call this once in each Activity before using the repo
    public static void init(Context ctx) {
        if (db == null) {
            db = AppDatabase.getInstance(ctx);
        }
    }

    // Insert new Student with status = PENDING
    public static void addStudent(String fn,
                                  String ln,
                                  String email,
                                  String pw,
                                  String phone,
                                  String program) throws Exception {

        String e = (email == null) ? "" : email.toLowerCase().trim();

        long id = db.studentDao().insert(
                new StudentEntity(fn, ln, e, pw, phone, program, "PENDING")
        );

        if (id <= 0) throw new Exception("Insert failed");
    }

    // Insert new Tutor with status = PENDING
    public static void addTutor(String fn,
                                String ln,
                                String email,
                                String pw,
                                String phone,
                                String degree,
                                String courses) throws Exception {

        String e = (email == null) ? "" : email.toLowerCase().trim();

        long id = db.tutorDao().insert(
                new TutorEntity(fn, ln, e, pw, phone, degree, courses, "PENDING")
        );

        if (id <= 0) throw new Exception("Insert failed");
    }

    // Result object for login attempts
    public static class AuthResult {
        public final boolean ok;    // credentials valid?
        public final String role;   // "Administrator", "Student", "Tutor", or null
        public final String status; // APPROVED / PENDING / REJECTED (or null if invalid)

        public AuthResult(boolean ok, String role, String status) {
            this.ok = ok;
            this.role = role;
            this.status = status;
        }
    }

    // Check credentials and return role + status
    public static AuthResult authenticate2(String role, String email, String pw) {
        String chosenRole = (role == null) ? "" : role.trim();
        String e = (email == null) ? "" : email.toLowerCase().trim();
        String p = (pw == null)    ? "" : pw;

        switch (chosenRole) {

            case "Administrator": {
                boolean adminOk = (e.equals("admin@otams.ca") && p.equals("admin123"));
                return adminOk
                        ? new AuthResult(true, "Administrator", "APPROVED")
                        : new AuthResult(false, null, null);
            }

            case "Student": {
                StudentEntity s = db.studentDao().login(e, p);
                if (s == null) return new AuthResult(false, null, null);
                return new AuthResult(true, "Student", s.status);
            }

            case "Tutor": {
                TutorEntity t = db.tutorDao().login(e, p);
                if (t == null) return new AuthResult(false, null, null);
                return new AuthResult(true, "Tutor", t.status);
            }

            default:
                return new AuthResult(false, null, null);
        }
    }

    // Admin data access

    public static List<StudentEntity> getPendingStudents() {
        return db.studentDao().getPending();
    }

    public static List<TutorEntity> getPendingTutors() {
        return db.tutorDao().getPending();
    }

    public static List<StudentEntity> getRejectedStudents() {
        return db.studentDao().getRejected();
    }

    public static List<TutorEntity> getRejectedTutors() {
        return db.tutorDao().getRejected();
    }

    // Admin actions

    public static void approveStudent(long id) {
        db.studentDao().updateStatus(id, "APPROVED");
    }

    public static void rejectStudent(long id) {
        db.studentDao().updateStatus(id, "REJECTED");
    }

    public static void approveTutor(long id) {
        db.tutorDao().updateStatus(id, "APPROVED");
    }

    public static void rejectTutor(long id) {
        db.tutorDao().updateStatus(id, "REJECTED");
    }
}
