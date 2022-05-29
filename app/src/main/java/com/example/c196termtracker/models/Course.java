package com.example.c196termtracker.models;

import java.util.Date;

public class Course {
    private String title;
    private Date startDate;
    private Date endDate;
    private String status;
    private String notes;

    public Course(String title, Date startDate, Date endDate, String status, String notes) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

