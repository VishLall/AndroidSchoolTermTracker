package com.example.c196termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c196termtracker.Entity.CourseEntity;
import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    List<CourseEntity> courses;
    Context context;

    public CourseAdapter(List<CourseEntity> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_course, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_courseName.setText(courses.get(position).getTitle());
        holder.tv_courseDate.setText("Start:" + courses.get(position).getStartDate() +", End:"+ courses.get(position).getEndDate());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("courseID", courses.get(position).getCourseID());
                intent.putExtra("courseName", courses.get(position).getTitle());
                intent.putExtra("courseStart", courses.get(position).getStartDate());
                intent.putExtra("courseEnd", courses.get(position).getEndDate());
                intent.putExtra("status", courses.get(position).getStatus());
                intent.putExtra("notes", courses.get(position).getNotes());
                intent.putExtra("index", courses.get(position).getStatusIndex());
                intent.putExtra("name", courses.get(position).getName());
                intent.putExtra("phone", courses.get(position).getPhone());
                intent.putExtra("email", courses.get(position).getEmail());
                intent.putExtra("termID", courses.get(position).getTermID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_courseName;
        TextView tv_courseDate;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_courseName = itemView.findViewById(R.id.tv_courseName);
            tv_courseDate = itemView.findViewById(R.id.tv_courseDate);
            parentLayout = itemView.findViewById(R.id.oneLineCourseLayout);
        }
    }
}
