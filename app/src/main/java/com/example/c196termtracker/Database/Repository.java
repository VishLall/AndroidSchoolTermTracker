package com.example.c196termtracker.Database;


import android.app.Application;

import com.example.c196termtracker.DAO.AssessmentDAO;
import com.example.c196termtracker.DAO.CourseDAO;
import com.example.c196termtracker.DAO.TermDAO;
import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<TermEntity>mAllTerms;
    private List<CourseEntity>mAllCourses;
    private List<AssessmentEntity>mAllAssessments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.AssessmentDAO();
    }
    public void insert(TermEntity termEntity){
        databaseExecutor.execute(()->{
            mTermDAO.insert(termEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(TermEntity termEntity){
        databaseExecutor.execute(()->{
            mTermDAO.update(termEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(TermEntity termEntity){
        databaseExecutor.execute(()->{
            mTermDAO.delete(termEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<TermEntity>getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<TermEntity>getTerm(int termID){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getTerm(termID);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(CourseEntity courseEntity){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(courseEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(CourseEntity courseEntity){
        databaseExecutor.execute(()->{
            mCourseDAO.update(courseEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(CourseEntity courseEntity){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(courseEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<CourseEntity>getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public List<CourseEntity>getTermCourses(int termID){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getTermCourses(termID);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public List<CourseEntity>getCourse(int courseID){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getCourse(courseID);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessmentEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessmentEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessmentEntity);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<AssessmentEntity>getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<AssessmentEntity>getCourseAssessments(int courseID){
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getCourseAssessments(courseID);
        });
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }
}
