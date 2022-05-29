package com.example.c196termtracker.UI;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196termtracker.Database.Repository;
import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.R;

import java.util.Calendar;
import java.util.List;

public class AssessmentEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText startDateText;
    private EditText et_aEndDate;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private Calendar startDate;
    private Calendar endDate;
    private EditText et_aName;
    String aType;
    String aTitle;
    String aDate;
    String aEndDate;
    int aTypeIndex;
    int aID;
    int courseID;

    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        aTitle = intent.getStringExtra("aName");
        aDate = intent.getStringExtra("aStart");
        aEndDate = intent.getStringExtra("aEnd");
        aTypeIndex = intent.getIntExtra("index",-1);
        aID = intent.getIntExtra("aID",-1);
        courseID = intent.getIntExtra("courseID", -1);

        startDateText = (EditText) findViewById(R.id.et_startDate);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        et_aEndDate = (EditText) findViewById(R.id.et_aEndDate);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);
        et_aName = (EditText)findViewById(R.id.et_aName);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        et_aName.setText(aTitle);
        startDateText.setText(aDate);
        et_aEndDate.setText(aEndDate);
        spinner.setSelection(aTypeIndex);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(startDateText, startDate);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(et_aEndDate, endDate);
            }
        });

    }

    private void updateDisplay(TextView dateDisplay, Calendar date) {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(date.get(Calendar.MONTH) + 1).append("-")
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("-")
                        .append(date.get(Calendar.YEAR)).append(" "));
    }

    public void showDateDialog(TextView dateDisplay, Calendar date) {
        activeDateDisplay = dateDisplay;
        activeDate = date;
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            activeDate.set(Calendar.YEAR, year);
            activeDate.set(Calendar.MONTH, monthOfYear);
            activeDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(activeDateDisplay, activeDate);
            unregisterDateDisplay();
        }
    };

    private void unregisterDateDisplay() {
        activeDateDisplay = null;
        activeDate = null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String type = adapterView.getItemAtPosition(i).toString();
        int index = adapterView.getSelectedItemPosition();
        aType = type;
        aTypeIndex = index;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveClick(View view){
        String title = et_aName.getText().toString();
        String startDate = startDateText.getText().toString();
        String endDate = et_aEndDate.getText().toString();

        fieldValidation();
        if(fieldValidation()){
            Toast.makeText(this, "Please Enter All Fields Correctly", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, AssessmentDetails.class);
            intent.putExtra("aID",aID);
            intent.putExtra("aName", title);
            intent.putExtra("aStart", startDate);
            intent.putExtra("aEnd", endDate);
            intent.putExtra("type", aType);
            intent.putExtra("index", aTypeIndex);
            intent.putExtra("courseID", courseID);

            Repository repo = new Repository(getApplication());
            AssessmentEntity a = new AssessmentEntity(aID, courseID, title, startDate, endDate, aType, aTypeIndex);
            repo.update(a);

            startActivity(intent);
            finish();
        }
    }

    public boolean fieldValidation() {
        int notSelected;
        if(aTypeIndex == 0){
            notSelected = 1;
        } else notSelected = 0;

        return et_aName.getText().toString().isEmpty() || startDateText.getText().toString().isEmpty()
                || et_aEndDate.getText().toString().isEmpty() || notSelected == 1;
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

        Intent i2 = new Intent(this, CourseDetails.class);
        i2.putExtra("courseID", courseID);
        i2.putExtra("courseName", courseName);
        i2.putExtra("courseStart", startDate);
        i2.putExtra("courseEnd", endDate);
        i2.putExtra("status", status);
        i2.putExtra("name", name);
        i2.putExtra("phone", phone);
        i2.putExtra("email", email);
        startActivity(i2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AssessmentDetails.class);
                intent.putExtra("aID",aID);
                intent.putExtra("aName", aTitle);
                intent.putExtra("aStart", aDate);
                intent.putExtra("type", aType);
                intent.putExtra("index", aTypeIndex);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}