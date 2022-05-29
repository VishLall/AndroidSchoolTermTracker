package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.c196termtracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAssessment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText et_aName;
    private EditText et_startDate;
    private EditText et_aEndDate;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private Calendar startDate;
    private Calendar endDate;
    String aType;
    int aTypeIndex;
    int courseID;
    int termID;
    String courseName;
    String courseStart;
    String courseEnd;
    String status;
    String notes;
    int statusIndex;
    String name;
    String phone;
    String email;


    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        et_aName = (EditText) findViewById(R.id.et_aName);
        et_startDate = (EditText) findViewById(R.id.et_startDate);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        et_aEndDate = (EditText) findViewById(R.id.et_aEndDate);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID",-1);
        termID = intent.getIntExtra("termID",-1);
        courseName = intent.getStringExtra("courseName");
        courseStart = intent.getStringExtra("courseStart");
        courseEnd = intent.getStringExtra("courseEnd");
        status = intent.getStringExtra("status");
        notes = intent.getStringExtra("notes");
        statusIndex = intent.getIntExtra("index",-1);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");

        Spinner spinner = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(et_startDate, startDate);
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
        String startDate = et_startDate.getText().toString();
        String endDate = et_aEndDate.getText().toString();

        fieldValidation();
        if(fieldValidation()){
            Toast.makeText(this, "Please Enter All Fields Correctly", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, CourseDetails.class);
            intent.putExtra("courseID", courseID);
            intent.putExtra("courseName", courseName);
            intent.putExtra("courseStart", courseStart);
            intent.putExtra("courseEnd", courseEnd);
            intent.putExtra("status", status);
            intent.putExtra("notes", notes);
            intent.putExtra("index", statusIndex);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("email", email);
            intent.putExtra("termID", termID);

            Repository repo = new Repository(getApplication());
            AssessmentEntity a = new AssessmentEntity(courseID, title, startDate, endDate, aType, aTypeIndex);
            repo.insert(a);

            startActivity(intent);
            finish();
        }
    }

    public boolean fieldValidation() {
        int notSelected;
        if(aTypeIndex == 0){
            notSelected = 1;
        } else notSelected = 0;

        return et_aName.getText().toString().isEmpty() || et_startDate.getText().toString().isEmpty()
                || et_aEndDate.getText().toString().isEmpty() || notSelected == 1;
    }
}