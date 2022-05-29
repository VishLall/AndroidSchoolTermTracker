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
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;

import java.util.Calendar;

public class CourseAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editCourseName;
    private EditText startDateText;
    private EditText endDateText;
    private EditText editTextNote;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private Calendar startDate;
    private Calendar endDate;
    String courseStatus;
    int statusIndex;
    int termID;
    String termName;
    String termStart;
    String termEnd;


    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);

        editCourseName = (EditText) findViewById(R.id.editCourseName);
        startDateText = (EditText) findViewById(R.id.editTextStartDate);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateText = (EditText) findViewById(R.id.editTextEndDate);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);

        Intent i = getIntent();
        termID = i.getIntExtra("termID",-1);
        termName = i.getStringExtra("termName");
        termStart = i.getStringExtra("termStart");
        termEnd = i.getStringExtra("termEnd");

        Spinner spinner = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(startDateText, startDate);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(endDateText, endDate);
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

    public void saveClick(View view){

        String title = editCourseName.getText().toString();
        String startDate = startDateText.getText().toString();
        String endDate = endDateText.getText().toString();
        String notes = editTextNote.getText().toString();
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String email = et_email.getText().toString();

        fieldValidation();
        if(fieldValidation()){
            Toast.makeText(this, "Please Enter All Fields Correctly", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", termID);
            intent.putExtra("termName", termName);
            intent.putExtra("termStart", termStart);
            intent.putExtra("termEnd", termEnd);

            Repository repo = new Repository(getApplication());
            CourseEntity c = new CourseEntity(termID, title, startDate, endDate, courseStatus, name, phone, email, statusIndex, notes);
            repo.insert(c);

            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String status = adapterView.getItemAtPosition(i).toString();
        int index = adapterView.getSelectedItemPosition();
        courseStatus = status;
        statusIndex = index;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean fieldValidation(){
        int notSelected;
        if(statusIndex == 0){
            notSelected = 1;
        }else notSelected = 0;

        return editCourseName.getText().toString().isEmpty()||
        startDateText.getText().toString().isEmpty()||
        endDateText.getText().toString().isEmpty()||
        et_name.getText().toString().isEmpty()||
        et_phone.getText().toString().isEmpty()||
        et_email.getText().toString().isEmpty() ||notSelected == 1;
    }
}