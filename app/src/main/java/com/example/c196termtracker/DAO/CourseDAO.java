package com.example.c196termtracker.DAO;

import com.example.c196termtracker.Entity.CourseEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;



@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseEntity courseEntity);

    @Update
    void update(CourseEntity courseEntity);

    @Delete
    void delete(CourseEntity courseEntity);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<CourseEntity> getAllCourses();

    @Query("SELECT * FROM courses WHERE termID = :termID ")
    List<CourseEntity> getTermCourses(int termID);

    @Query("SELECT * FROM courses WHERE courseID = :courseID ")
    List<CourseEntity> getCourse(int courseID);

}