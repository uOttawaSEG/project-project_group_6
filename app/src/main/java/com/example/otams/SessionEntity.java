package com.example.otams;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions",
        // session involves student, tutor and an availability slot
        foreignKeys = {
                @ForeignKey( // student entity
                        entity = StudentEntity.class,
                        parentColumns = "id",
                        childColumns = "studentId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey( // tutor entity
                        entity = TutorEntity.class,
                        parentColumns = "id",
                        childColumns = "tutorId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey( // availability slot entity
                        entity = AvailabilitySlotEntity.class,
                        parentColumns = "id",
                        childColumns = "slotId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("studentId"),
                @Index("tutorId"),
                @Index("slotId")
        }
)
public class SessionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Foreign keys
    public int studentId, tutorId, slotId;

    // "REQUESTED", "APPROVED", "REJECTED", "CANCELLED", "COMPLETED"
    public String status;

    // Copy of date and time for sorting and display
    // Same format as in the availability slot
    public String date;      // yyyy-dd-mm
    public String startTime; // hh:mm
    public String endTime;   // hh:mm

    // Used for search by course code
    public String courseCode;

    // tutorRating is null until the student rates the tutor
    public Integer tutorRating;   // 1 to 5 or null

    public SessionEntity(int studentId, int tutorId, int slotId,
                         String status, String courseCode,
                         String date, String startTime, String endTime) {
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.slotId = slotId;
        this.status = status;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseCode = courseCode;
        this.tutorRating = null;
    }
}

