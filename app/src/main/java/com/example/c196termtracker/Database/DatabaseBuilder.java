package com.example.c196termtracker.Database;

import android.content.Context;

import com.example.c196termtracker.DAO.AssessmentDAO;
import com.example.c196termtracker.DAO.CourseDAO;
import com.example.c196termtracker.DAO.TermDAO;
import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 11, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO AssessmentDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            if (INSTANCE == null) {
                synchronized (DatabaseBuilder.class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "myAppDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
