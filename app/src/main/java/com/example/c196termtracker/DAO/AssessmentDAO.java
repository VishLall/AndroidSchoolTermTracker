package com.example.c196termtracker.DAO;

import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.Entity.CourseEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AssessmentEntity assessmentEntity);

    @Update
    void update(AssessmentEntity assessmentEntity);

    @Delete
    void delete(AssessmentEntity assessmentEntity);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<AssessmentEntity> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseID = :courseID ")
    List<AssessmentEntity> getCourseAssessments(int courseID);
}
