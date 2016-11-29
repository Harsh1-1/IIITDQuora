package com.example.harsh.iiitdquora.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harsh.iiitdquora.beans.Question;
import com.example.harsh.iiitdquora.R;

import java.util.ArrayList;

/*
CLASS NAME : QuestionListAdapter
PURPOSE : Provides interface for question
 */


public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder>{

    Context context;
    ArrayList<Question> dataset = null;

    public void setDataset(ArrayList<Question> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
        //Log.d("hhh", dataset.toString());
    }

    public QuestionListAdapter(Context context){
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Question question = dataset.get(position);
        holder.questionTitleView.setText(question.getText());
        holder.questionDescView.setText(question.getDescription());
        holder.questionCategoryTextView.setText("Asked in " + question.getCategoryname());
        holder.itemView.setOnClickListener(new QuestionListOnClickListener(dataset.get(position).getId(),dataset.get(position).getText(),dataset.get(position).getDescription(),dataset.get(position).getCreated_by(), context));
    }

    @Override
    public int getItemCount() {
        if(dataset == null) return 0;
        return dataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView questionTitleView;
        public TextView questionDescView;
        public TextView questionCategoryTextView;
        public MyViewHolder(View itemView){
            super(itemView);
            questionTitleView = (TextView)itemView.findViewById(R.id.questionLayout_questionTextView);
            questionDescView = (TextView)itemView.findViewById(R.id.questionLayout_descTextView);
            questionCategoryTextView = (TextView)itemView.findViewById(R.id.questionLayout_categoryTextView);
        }
    }

}
