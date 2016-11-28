package com.example.harsh.iiitdquora;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tushar on 18-11-2016.
 */

public class AnswerListAdapter  extends RecyclerView.Adapter<AnswerListAdapter.MyViewHolder> {

    static Context context;
    static ArrayList<Answer> dataset = null;
    static ArrayList<Integer>answerid = null;
    static ArrayList<Integer>rating = null;
    static ArrayList<Integer>answered = null;

    public AnswerListAdapter(Context context){this.context = context;}

    public void setAnswered(ArrayList<Integer>answered)
    {
        this.answered = answered;
        notifyDataSetChanged();
    }

    public void setDataset(ArrayList<Answer> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
        }

    public void setRating(ArrayList<Integer>answerid, ArrayList<Integer>rating)
    {
        this.answerid = answerid;
        this.rating = rating;
        notifyDataSetChanged();
    }

    @Override
    public AnswerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnswerListAdapter.MyViewHolder holder, int position) {

        Log.d("Android :","Inside onBindViewHolder");

        Answer answer = dataset.get(position);
        holder.answerTextView.setText(answer.getText());
        holder.answerUser.setText(answer.getCreated_by());

        if(answerid == null)
            holder.upvoteValueButton.setText("0");

        else {
                for(int j = 0; j < answerid.size(); j++)
                {
                    if (answer.getAnswer_id() == answerid.get(j))
                    {   holder.upvoteValueButton.setText(Integer.toString(rating.get(j)));
                        break;
                    }
                }
        }
        int id = answer.getAnswer_id();
        int i = 0;

        if(answered == null);

        else
        {
            while(i < answered.size())
            {
                if(id == answered.get(i))
                {
                    holder.upvoteButton.setEnabled(false);
                    break;
                }
                i++;
            }}

    }

    @Override
    public int getItemCount() {
        if(dataset == null) return 0;
        return dataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView answerTextView;
        public TextView answerUser;
        public Button upvoteButton;
        public Button upvoteValueButton;

        public MyViewHolder(View itemView){
            super(itemView);
            answerTextView = (TextView)itemView.findViewById(R.id.answerLayout_answerText);
            answerUser = (TextView)itemView.findViewById(R.id.answerLayout_answerUser);
            upvoteButton = (Button)itemView.findViewById(R.id.upvote);
            upvoteValueButton = (Button)itemView.findViewById(R.id.upvoteVAlue);

            upvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int rating = Integer.parseInt(upvoteValueButton.getText().toString());
                    int position = getLayoutPosition();

                    Log.d("Inside Answer List  ","rating : "+rating);
                    Log.d("Inside Answer list:","position : "+position);
                    Answer answer = dataset.get(position);
                    //Answer_Rating answer_rating = new Answer_Rating(answer.getAnswer_id(),SignInActivity.user.getEmailId(),rating+1);

                    if(InternetConnectivity.isConnected() == false)
                    {
                        Toast.makeText(context,"NO internet",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    UpvoteTask upvoteTask = new UpvoteTask(context);
                    upvoteTask.execute(SignInActivity.user.getEmailId(),Integer.toString(answer.getAnswer_id()),Integer.toString(rating+1));
                    upvoteButton.setEnabled(false);
                    upvoteValueButton.setText(Integer.toString(rating+1)) ;

                }
            });
        }
    }
}
