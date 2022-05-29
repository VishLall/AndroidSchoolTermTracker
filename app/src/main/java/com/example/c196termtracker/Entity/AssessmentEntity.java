package com.example.c196termtracker.Entity;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessments")
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String title;
    private String startDate;
    private String endDate;
    private String type;
    private int typeIndex;

    public AssessmentEntity(int assessmentID, int courseID, String title, String startDate, String endDate, String type, int typeIndex) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.typeIndex = typeIndex;

    }
    @Ignore
    public AssessmentEntity(int courseID, String title, String startDate, String endDate, String type, int typeIndex) {
        this.courseID = courseID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.typeIndex = typeIndex;

    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentID=" + assessmentID +
                ", courseID=" + courseID +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                ", typeIndex=" + typeIndex +
                '}';
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
