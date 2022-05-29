package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.c196termtracker.Database.Repository;
import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    String aName;
    String aStartDate;
    String aEndDate;
    String aType;
    int aTypeIndex;
    int aID;
    int courseID;
    String myFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv_aTitle = (TextView) findViewById(R.id.tv_aTitle);
        TextView tv_aDate = (TextView) findViewById(R.id.tv_aDate);
        TextView tv_aType = (TextView) findViewById(R.id.tv_aType);
        TextView tv_aEndDate = (TextView) findViewById(R.id.tv_aEndDate);

        Intent intent = getIntent();
        aName = intent.getStringExtra("aName");
        aStartDate = intent.getStringExtra("aStart");
        aEndDate = intent.getStringExtra("aEnd");
        aType = intent.getStringExtra("type");
        aTypeIndex = intent.getIntExtra("index", -1);
        aID = intent.getIntExtra("aID", -1);
        courseID = intent.getIntExtra("courseID", -1);

        tv_aTitle.setText(aName);
        tv_aDate.setText(aStartDate);
        tv_aType.setText(aType);
        tv_aEndDate.setText(aEndDate);

        myFormat = "MM-dd-yyyy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackCourseInfo();
                return true;

            case R.id.mi_delete:
                Repository repo = new Repository(getApplication());
                AssessmentEntity a = new AssessmentEntity(aID, courseID, aName, aStartDate, aEndDate, aType, aTypeIndex);
                repo.delete(a);
                onBackCourseInfo();
                return true;

            case R.id.mi_edit:
                Intent intent = new Intent(this, AssessmentEdit.class);
                intent.putExtra("aID", aID);
                intent.putExtra("aName", aName);
                intent.putExtra("aStart", aStartDate);
                intent.putExtra("aEnd", aEndDate);
                intent.putExtra("type", aType);
                intent.putExtra("index", aTypeIndex);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
               // finish();
                return true;

            case R.id.mi_notifyStart:
                String dateFromScreen= aStartDate;
                Date myDate=null;
                try{
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent i =new Intent(AssessmentDetails.this,StartReceiver.class);
                i.putExtra("key",aName +" assessment starts today");
                PendingIntent sender=PendingIntent.getBroadcast(this,Terms.numAlert++, i,0);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                return true;

            case R.id.mi_notifyEnd:
                String endDateFromScreen= aEndDate;
                Date myEndDate=null;
                try{
                    myEndDate=sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger=myEndDate.getTime();
                Intent i2 =new Intent(AssessmentDetails.this,EndReceiver.class);
                i2.putExtra("key",aName + " assessment is due today");
                PendingIntent endSender=PendingIntent.getBroadcast(AssessmentDetails.this,Terms.numAlert2++, i2,0);
                AlarmManager endAlarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,endSender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackCourseInfo() {
        Repository repo = new Repository(getApplication());
        List<CourseEntity> course = repo.getCourse(courseID);
        String courseName = course.get(0).getTitle();
        String startDate = course.get(0).getStartDate();
        String endDate = course.get(0).getEndDate();
        String status = course.get(0).getStatus();
        String name = course.get(0).getName();
        String phone = course.get(0).getPhone();
        String email = course.get(0).getEmail();
        int termID = course.get(0).getTermID();

        Intent i2 = new Intent(this, CourseDetails.class);
        i2.putExtra("courseID", courseID);
        i2.putExtra("courseName", courseName);
        i2.putExtra("courseStart", startDate);
        i2.putExtra("courseEnd", endDate);
        i2.putExtra("status", status);
        i2.putExtra("name", name);
        i2.putExtra("phone", phone);
        i2.putExtra("email", email);
        i2.putExtra("termID", termID);
        startActivity(i2);
    }
}