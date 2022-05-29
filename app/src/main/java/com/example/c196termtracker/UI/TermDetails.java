package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196termtracker.Database.Repository;
import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;

import java.util.List;

public class TermDetails extends AppCompatActivity {

    String termName;
    String termStart;
    String termEnd;
    int termID;

    List<CourseEntity> termCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Intent intent = getIntent();
        termID = intent.getIntExtra("termID", -1);
        termName = intent.getStringExtra("termName");
        termStart = intent.getStringExtra("termStart");
        termEnd = intent.getStringExtra("termEnd");

        Repository repo = new Repository(getApplication());
        termCourses = repo.getTermCourses(termID);


        RecyclerView recyclerView = findViewById(R.id.courseRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new CourseAdapter(termCourses, this);
        recyclerView.setAdapter(mAdapter);

        TextView termNameText =  (TextView) findViewById(R.id.tv_termName);
        TextView termStartText =  (TextView) findViewById(R.id.tv_termStart);
        TextView termEndText =  (TextView) findViewById(R.id.tv_endDate);

        termNameText.setText(termName);
        termStartText.setText(termStart);
        termEndText.setText(termEnd);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_term, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mi_delete:
                deleteValidation();
                if(deleteValidation()){
                    Repository repo = new Repository(getApplication());
                    TermEntity t = new TermEntity(termID, termName,termStart, termEnd);
                    repo.delete(t);
                    Intent i = new Intent(this, Terms.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(this,termName +" term currently has courses assigned to it. Please delete courses first", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.mi_edit:
                Intent intent = new Intent(this, TermEdit.class);
                intent.putExtra("termName", termName);
                intent.putExtra("termStart", termStart);
                intent.putExtra("termEnd", termEnd);
                intent.putExtra("termID", termID);
                startActivity(intent);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void addCourseClick(View view){
        Intent i = new Intent(this, CourseAdd.class);
        i.putExtra("termID", termID);
        i.putExtra("termName", termName);
        i.putExtra("termStart", termStart);
        i.putExtra("termEnd", termEnd);
        startActivity(i);
    }

    public boolean deleteValidation(){
        return termCourses.isEmpty();
    }
}