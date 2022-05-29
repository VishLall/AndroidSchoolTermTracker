package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.c196termtracker.Database.Repository;
import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    int courseID;
    int termID;
    String courseName;
    String startDate;
    String endDate;
    String status;
    String notes;
    int statusIndex;
    String name;
    String phone;
    String email;
    String myFormat;
    SimpleDateFormat sdf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID", -1);
        termID = intent.getIntExtra("termID", -1);
        courseName = intent.getStringExtra("courseName");
        startDate = intent.getStringExtra("courseStart");
        endDate = intent.getStringExtra("courseEnd");
        status = intent.getStringExtra("status");
        notes = intent.getStringExtra("notes");
        statusIndex = intent.getIntExtra("index", -1);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");


        TextView tv_courseName = (TextView) findViewById(R.id.tv_courseName);
        TextView tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        TextView tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        TextView tv_status = (TextView) findViewById(R.id.tv_status);
        TextView tv_notes = (TextView) findViewById(R.id.tv_notes);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
        TextView tv_email = (TextView) findViewById(R.id.tv_email);

        Repository repo = new Repository(getApplication());
        List<AssessmentEntity> assessments = repo.getCourseAssessments(courseID);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new AssessmentAdapter(assessments, this);
        recyclerView.setAdapter(mAdapter);

        tv_courseName.setText(courseName);
        tv_startDate.setText(startDate);
        tv_endDate.setText(endDate);
        tv_status.setText(status);
        tv_notes.setText(notes);
        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_email.setText(email);

        myFormat = "MM-dd-yyyy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case android.R.id.home:
                onBackTermInfo();
                return true;
            case R.id.mi_delete:
                Repository repo = new Repository(getApplication());
                List<AssessmentEntity> assessments = repo.getCourseAssessments(courseID);
                CourseEntity c = new CourseEntity(courseID,termID, courseName, startDate, endDate, status, name, phone, email, statusIndex, notes);
                repo.delete(c);
                for (AssessmentEntity a  : assessments){
                    a  = new AssessmentEntity(a.getAssessmentID(), a.getCourseID(),
                            a.getTitle(), a.getStartDate(), a.getEndDate(), a.getType(), a.getTypeIndex());
                    repo.delete(a);
            }
                onBackTermInfo();
                return true;

            case R.id.mi_edit:
                Intent intent2 = new Intent(this, CourseEdit.class);
                intent2.putExtra("courseID", courseID);
                intent2.putExtra("termID", termID);
                intent2.putExtra("courseName", courseName);
                intent2.putExtra("courseStart", startDate);
                intent2.putExtra("courseEnd", endDate);
                intent2.putExtra("status", status);
                intent2.putExtra("notes", notes);
                intent2.putExtra("index", statusIndex);
                intent2.putExtra("name", name);
                intent2.putExtra("phone", phone);
                intent2.putExtra("email", email);
                startActivity(intent2);
                finish();
                return true;

            case R.id.mi_notifyStart:
                String dateFromScreen= startDate;
                Date myDate=null;
                try{
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent i =new Intent(CourseDetails.this,CourseStartReceiver.class);
                i.putExtra("key",courseName +" course starts today");
                PendingIntent sender=PendingIntent.getBroadcast(this,Terms.numAlert3++, i,0);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                return true;

            case R.id.mi_notifyEnd:
                String endDateFromScreen= endDate;
                Date myEndDate=null;
                try{
                    myEndDate=sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger=myEndDate.getTime();
                Intent i2 =new Intent(this,CourseEndReceiver.class);
                i2.putExtra("key",courseName + " course ends today");
                PendingIntent endSender=PendingIntent.getBroadcast(this,Terms.numAlert4++, i2,0);
                AlarmManager endAlarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,endSender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addAssessmentClick(View view) {
        Intent i = new Intent(this, AddAssessment.class);
        i.putExtra("courseID", courseID);
        i.putExtra("courseName", courseName);
        i.putExtra("courseStart", startDate);
        i.putExtra("courseEnd", endDate);
        i.putExtra("status", status);
        i.putExtra("notes", notes);
        i.putExtra("index", statusIndex);
        i.putExtra("name", name);
        i.putExtra("phone", phone);
        i.putExtra("email", email);
        i.putExtra("termID", termID);
        startActivity(i);
    }

    public void onBackTermInfo(){
        Repository repo = new Repository(getApplication());
        List<TermEntity> term = repo.getTerm(termID);
        String termName = term.get(0).getTitle();
        String termStart = term.get(0).getStartDate();
        String termEnd = term.get(0).getEndDate();

        Intent intent = new Intent(this,TermDetails.class);
        intent.putExtra("termID", termID);
        intent.putExtra("termName", termName);
        intent.putExtra("termStart", termStart);
        intent.putExtra("termEnd", termEnd);
        startActivity(intent);
    }

}