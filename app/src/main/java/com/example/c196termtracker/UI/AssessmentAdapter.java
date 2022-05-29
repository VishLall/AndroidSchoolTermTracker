package com.example.c196termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c196termtracker.Entity.AssessmentEntity;
import com.example.c196termtracker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.MyViewHolder> {
    List<AssessmentEntity> assessments;
    Context context;

    public AssessmentAdapter(List<AssessmentEntity> assessments, Context context) {
        this.assessments = assessments;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_assessment, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_assessmentName.setText(assessments.get(position).getTitle());
        holder.tv_assessmentDate.setText("Start:" + assessments.get(position).getStartDate() +", End:"+ assessments.get(position).getEndDate());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("aID", assessments.get(position).getAssessmentID());
                intent.putExtra("courseID", assessments.get(position).getCourseID());
                intent.putExtra("aName", assessments.get(position).getTitle());
                intent.putExtra("aStart", assessments.get(position).getStartDate());
                intent.putExtra("aEnd", assessments.get(position).getEndDate());
                intent.putExtra("type", assessments.get(position).getType());
                intent.putExtra("index", assessments.get(position).getTypeIndex());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_assessmentName;
        TextView tv_assessmentDate;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_assessmentName = itemView.findViewById(R.id.tv_assessmentName);
            tv_assessmentDate = itemView.findViewById(R.id.tv_assessmentDate);
            parentLayout = itemView.findViewById(R.id.oneLineAssessmentLayout);
        }
    }
}

