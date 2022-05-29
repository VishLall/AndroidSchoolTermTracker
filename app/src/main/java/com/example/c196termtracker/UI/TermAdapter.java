package com.example.c196termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c196termtracker.Entity.TermEntity;
import com.example.c196termtracker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.MyViewHolder> {
    List<TermEntity> terms;
    Context context;

    public TermAdapter(List<TermEntity> terms, Context context) {
        this.terms = terms;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_term, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_termName.setText(terms.get(position).getTitle());
        holder.tv_termDate.setText("Start:" + terms.get(position).getStartDate() +", End:"+ terms.get(position).getEndDate());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TermDetails.class);
                intent.putExtra("termID", terms.get(position).getTermID());
                intent.putExtra("termName", terms.get(position).getTitle());
                intent.putExtra("termStart", terms.get(position).getStartDate());
                intent.putExtra("termEnd", terms.get(position).getEndDate());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_termName;
        TextView tv_termDate;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_termName = itemView.findViewById(R.id.tv_termName);
            tv_termDate = itemView.findViewById(R.id.tv_termDate);
            parentLayout = itemView.findViewById(R.id.oneLineTermLayout);
        }
    }
}
