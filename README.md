OTAMS – Online Tutoring Appointment Management System
-----------------------------------------------------

Course: SEG2105 – Introduction to Software Engineering  
University of Ottawa

OTAMS is an Android application designed to simplify tutoring appointment scheduling at 
the University of Ottawa Help Centre. The system supports three user roles: Student, Tutor, 
and Administrator.

-----------------------------------------------------
Overview
-----------------------------------------------------

STUDENTS:
- Register and wait for Administrator approval.
- Log in after approval.
- View upcoming and past tutoring sessions.
- Book available 30-minute tutoring slots based on course code.
- Cancel eligible sessions.
- Rate Tutors after completed sessions.

TUTORS:
- Register and wait for Administrator approval.
- Log in after approval.
- Create and manage availability slots (30-minute increments).
- Approve, reject, or auto-approve Student session requests.
- View upcoming sessions, past sessions, and pending bookings.
- Cancel previously approved sessions.

ADMINISTRATOR:
- View all pending registration requests.
- Approve or reject Student and Tutor registrations.
- View previously rejected requests and approve them if needed.
- Maintain system user access and integrity.

-----------------------------------------------------
Administrator Login
-----------------------------------------------------

Username: admin@otams.ca  
Password: admin123

-----------------------------------------------------
Project Purpose
-----------------------------------------------------

This project serves as the foundation for a tutoring management platform that connects 
Students with Tutors. It includes account management, scheduling features, session booking, 
and administration workflows required by the SEG2105 course deliverables.

-----------------------------------------------------
Technical Features
-----------------------------------------------------

- Android application built with Android Studio
- SQLite or Firebase used for data persistence
- 30-minute scheduling logic
- Role-based navigation and UI
- CRUD operations for availability, sessions, and registration handling
- Field validation and error handling
- Incremental GitHub releases (Deliverables 1–4)

-----------------------------------------------------
Installation
-----------------------------------------------------

1. Clone the repository from GitHub.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Build and run the app on an emulator or Android device.
5. Log in using the Administrator credentials or register as a Student/Tutor.

-----------------------------------------------------
Included Deliverables
-----------------------------------------------------

- APKs for each deliverable (D1–D4)
- UML Class Diagrams
- README with Admin credentials
- Demo videos
- Final report (Deliverable 4)
- Local unit tests (Deliverable 4)

