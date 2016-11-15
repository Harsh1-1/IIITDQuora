package com.example.harsh.iiitdquora;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Abhi on 15-11-2016.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder>{

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
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
