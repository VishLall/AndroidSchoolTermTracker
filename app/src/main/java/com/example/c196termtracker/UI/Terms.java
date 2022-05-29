package com.example.c196termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196termtracker.Database.Repository;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Terms extends AppCompatActivity {

    public static int numAlert;
    public static int numAlert2;
    public static int numAlert3;
    public static int numAlert4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        Repository repo = new Repository(getApplication());
        List<TermEntity> terms = repo.getAllTerms();

        RecyclerView recyclerView = findViewById(R.id.termRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new TermAdapter(terms, this);
        recyclerView.setAdapter(mAdapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.loadSampleDataClick:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTermClick(View vie){
        Intent i = new Intent(this, TermAdd.class);
        startActivity(i);
    }

    public void editTermButton(View view){
        Intent i = new Intent(this, TermEdit.class);
        startActivity(i);
    }
}