package com.example.otams;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        StudentEntity.class,
        TutorEntity.class,
        AvailabilitySlotEntity.class,
        SessionEntity.class
},
        version = 9,  // Increment version for schema changes
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract StudentDao studentDao();
    public abstract TutorDao tutorDao();
    public abstract AvailabilitySlotDao availabilitySlotDao();
    public abstract SessionDao sessionDao();

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                                    AppDatabase.class, "otams.db")
                            .fallbackToDestructiveMigration()  // Recreate DB on schema change
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}