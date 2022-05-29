package com.example.c196termtracker.Entity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity (tableName = "courses")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String name;
    private String phone;
    private String email;
    private int statusIndex;
    private String notes;

    public CourseEntity(int courseID, int termID, String title, String startDate, String endDate, String status, String name, String phone, String email, int statusIndex, String notes) {
        this.courseID = courseID;
        this.termID = termID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.statusIndex = statusIndex;
        this.notes = notes;
    }
    @Ignore
    public CourseEntity(int termID, String title, String startDate, String endDate, String status, String name, String phone, String email, int statusIndex, String notes) {
        this.termID = termID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.statusIndex = statusIndex;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseID=" + courseID +
                ", termID=" + termID +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", statusIndex=" + statusIndex +
                ", notes='" + notes + '\'' +
                '}';
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

}