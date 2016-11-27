package com.example.harsh.iiitdquora;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tushar on 18-11-2016.
 */

public class AnswerListAdapter  extends RecyclerView.Adapter<AnswerListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Answer> dataset = null;

    public AnswerListAdapter(Context context){this.context = context;}

    public void setDataset(ArrayList<Answer> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
        }


    @Override
    public AnswerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnswerListAdapter.MyViewHolder holder, int position) {

        Answer answer = dataset.get(position);
        holder.answerTextView.setText(answer.getText());
        holder.answerUser.setText(answer.getCreated_by());
    }

    @Override
    public int getItemCount() {
        if(dataset == null) return 0;
        return dataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView answerTextView;
        public TextView answerUser;

        public MyViewHolder(View itemView){
            super(itemView);
            answerTextView = (TextView)itemView.findViewById(R.id.answerLayout_answerText);
            answerUser = (TextView)itemView.findViewById(R.id.answerLayout_answerUser);

        }
    }
}
