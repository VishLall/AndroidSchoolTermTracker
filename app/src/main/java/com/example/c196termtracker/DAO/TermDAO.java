package com.example.c196termtracker.DAO;

import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.models.Term;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TermEntity termEntity);

    @Update
    void update(TermEntity termEntity);

    @Delete
    void delete(TermEntity termEntity);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<TermEntity> getAllTerms();

    @Query("SELECT * FROM terms WHERE termID = :termID ")
    List<TermEntity> getTerm(int termID);


}

