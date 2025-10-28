package com.example.otams;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StudentEntity.class, TutorEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract StudentDao studentDao();
    public abstract TutorDao tutorDao();

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                                    AppDatabase.class, "otams.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() // OK for coursework/demo
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

