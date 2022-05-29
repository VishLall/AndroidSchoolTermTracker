package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.R;

import java.util.Calendar;

public class CourseEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editCourseName;
    private EditText editStartDate;
    private EditText editEndDate;
    private EditText editTextNote;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private Calendar startDate;
    private Calendar endDate;

    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;

    int courseID;
    int termID;
    int statusIndex;
    String courseName;
    String courseStart;
    String courseEnd;
    String courseStatus;
    String notes;
    String name;
    String phone;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);

        editCourseName = (EditText) findViewById(R.id.editCourseName);
        editStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editEndDate = (EditText) findViewById(R.id.editTextEndDate);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID", -1);
        termID = intent.getIntExtra("termID", -1);
        courseName = intent.getStringExtra("courseName");
        courseStart = intent.getStringExtra("courseStart");
        courseEnd = intent.getStringExtra("courseEnd");
        courseStatus = intent.getStringExtra("status");
        notes = intent.getStringExtra("notes");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        statusIndex = intent.getIntExtra("index", -1);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(statusIndex);
        editCourseName.setText(courseName);
        editStartDate.setText(courseStart);
        editEndDate.setText(courseEnd);
        editTextNote.setText(notes);
        et_name.setText(name);
        et_phone.setText(phone);
        et_email.setText(email);
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(editStartDate, startDate);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(editEndDate, endDate);
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
        courseStatus = adapterView.getItemAtPosition(i).toString();
        int index = adapterView.getSelectedItemPosition();
        statusIndex = index;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveClick(View view) {
        String title = editCourseName.getText().toString();
        String startDate = editStartDate.getText().toString();
        String endDate = editEndDate.getText().toString();
        String notes = editTextNote.getText().toString();
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String email = et_email.getText().toString();

        fieldValidation();
        if (fieldValidation()) {
            Toast.makeText(this, "Please Enter All Fields Correctly", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CourseDetails.class);
            intent.putExtra("courseID", courseID);
            intent.putExtra("courseName", title);
            intent.putExtra("courseStart", startDate);
            intent.putExtra("courseEnd", endDate);
            intent.putExtra("status", courseStatus);
            intent.putExtra("notes", notes);
            intent.putExtra("index", statusIndex);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("email", email);
            intent.putExtra("termID", termID);

            Repository repo = new Repository(getApplication());
            CourseEntity c = new CourseEntity(courseID, termID, title, startDate, endDate, courseStatus, name, phone, email, statusIndex, notes);
            repo.update(c);

            startActivity(intent);
            finish();

        }
    }

    public boolean fieldValidation() {
        int notSelected;
        if (statusIndex == 0) {
            notSelected = 1;
        } else notSelected = 0;

        return editCourseName.getText().toString().isEmpty() ||
                editStartDate.getText().toString().isEmpty() ||
                editEndDate.getText().toString().isEmpty() ||
                et_name.getText().toString().isEmpty() ||
                et_phone.getText().toString().isEmpty() ||
                et_email.getText().toString().isEmpty() || notSelected == 1;
    }
}


