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
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;
import com.example.c196termtracker.models.Term;

import java.util.Calendar;

public class TermAdd extends AppCompatActivity {
    private EditText editTermName;
    private EditText startDateText;
    private EditText endDateText;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private Calendar startDate;
    private Calendar endDate;
    private String title;
    private String termStart;
    private String termEnd;


    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        editTermName = (EditText) findViewById(R.id.editTermName);
        startDateText = (EditText) findViewById(R.id.editTextStartDate);
        startDateButton = (ImageButton) findViewById(R.id.startDateButton);
        endDateText = (EditText) findViewById(R.id.editTextEndDate);
        endDateButton = (ImageButton) findViewById(R.id.endDateButton);

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

    public void saveClick(View view) {

        title = editTermName.getText().toString();
        termStart = startDateText.getText().toString();
        termEnd = endDateText.getText().toString();

        fieldValidation();
        if (fieldValidation()) {
            Toast.makeText(this, "Please Enter All Fields Correctly", Toast.LENGTH_LONG).show();
        }
        else {
            Repository repo = new Repository(getApplication());
            TermEntity t = new TermEntity(title, termStart, termEnd);
            repo.insert(t);

            Intent intent = new Intent(this, Terms.class);
            startActivity(intent);
            finish();
        }
    }
    public boolean fieldValidation(){
        return editTermName.getText().toString().isEmpty() || startDateText.getText().toString().isEmpty()|| endDateText.getText().toString().isEmpty();
        //return title.isEmpty() || termStart.isEmpty() ||termEnd.isEmpty();
    }


}