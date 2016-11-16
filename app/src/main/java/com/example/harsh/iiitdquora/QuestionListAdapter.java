package com.example.harsh.iiitdquora;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Abhi on 15-11-2016.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder>{

    Context context;

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
        //TODO : Set text of fields and set onClickListener on View
    }

    @Override
    public int getItemCount() {
        //TODO : return correct count
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView questionTitleView;
        public TextView questionDescView;
        public MyViewHolder(View itemView){
            super(itemView);
            questionTitleView = (TextView)itemView.findViewById(R.id.questionLayout_questionTextView);
            questionDescView = (TextView)itemView.findViewById(R.id.questionLayout_descTextView);
        }
    }
}
