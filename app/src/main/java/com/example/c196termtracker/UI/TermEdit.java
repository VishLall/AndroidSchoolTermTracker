package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196termtracker.Database.Repository;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;
import com.example.c196termtracker.models.Term;

import java.util.Calendar;

public class TermEdit extends AppCompatActivity {

    private EditText editTermName;
    private EditText startDateText;
    private EditText endDateText;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private Calendar startDate;
    private Calendar endDate;

    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;

    int termID;
    String termName;
    String termStart;
    String termEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        Intent intent = getIntent();
        termName = intent.getStringExtra("termName");
        termStart = intent.getStringExtra("termStart");
        termEnd = intent.getStringExtra("termEnd");
        termID = intent.getIntExtra("termID",-1);

        editTermName = (EditText) findViewById(R.id.editTermName);
        startDateText = (EditText) findViewById(R.id.editTextStartDate);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateText = (EditText) findViewById(R.id.editTextEndDate);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

        editTermName.setText(termName);
        startDateText.setText(termStart);
        endDateText.setText(termEnd);

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

        String title = editTermName.getText().toString();
        String startDate = startDateText.getText().toString();
        String endDate = endDateText.getText().toString();

        fieldValidation();
        if(fieldValidation()){
            Toast.makeText(this, "Please Enter All Fields Correctly", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", termID);
            intent.putExtra("termName", title);
            intent.putExtra("termStart", startDate);
            intent.putExtra("termEnd", endDate);

            Repository repo = new Repository(getApplication());
            TermEntity t = new TermEntity(termID,title,startDate, endDate);
            repo.update(t);

            startActivity(intent);
            finish();
        }
    }
    public boolean fieldValidation(){
        return editTermName.getText().toString().isEmpty() || startDateText.getText().toString().isEmpty() || endDateText.getText().toString().isEmpty();
    }


}






