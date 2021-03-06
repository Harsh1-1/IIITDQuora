package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh.iiitdquora.Helpers.AnswerListAdapter;
import com.example.harsh.iiitdquora.Helpers.InternetConnectivity;
import com.example.harsh.iiitdquora.beans.Answer;
import com.example.harsh.iiitdquora.tasks.AllAnswersTask;
import com.example.harsh.iiitdquora.tasks.UpvoteAnswersTask;
import com.example.harsh.iiitdquora.tasks.UserUpvotesTask;

import java.util.ArrayList;

//Activity Class for listing all the Answers for a particular question
public class AnswerListActivity extends AppCompatActivity {

    AnswerListAdapter answerListAdapter;
    RecyclerView answerRecyclerView;
    RecyclerView.LayoutManager answerRecyclerViewLayoutManager;
    ArrayList<Answer> dataset;
    ArrayList<Integer> answerid;
    ArrayList<Integer>rating;
    ArrayList<Integer>answered_answer;

    private String id_st;

    private TextView QuestionText;
    private TextView QuestionCreatedby;
    private TextView QuestionDesc;
    private Button addAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.answerToolbar);
        setSupportActionBar(myToolbar);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);


        QuestionText = (TextView)findViewById(R.id.QuestionText);
        QuestionDesc  =(TextView)findViewById(R.id.Questiondesc);
        QuestionCreatedby = (TextView)findViewById(R.id.userView);
        addAnswer = (Button)findViewById(R.id.addAnswer);
        answerListAdapter = new  AnswerListAdapter(this);
        answerRecyclerView = (RecyclerView)findViewById(R.id.answerListRecyclerView);
        answerRecyclerViewLayoutManager = new LinearLayoutManager(this);
        answerRecyclerView.setLayoutManager(answerRecyclerViewLayoutManager);
        answerRecyclerView.setAdapter(answerListAdapter);

        QuestionText.setText(getIntent().getStringExtra("questionText"));
        QuestionDesc.setText(getIntent().getStringExtra("questionDesc"));
        QuestionCreatedby.setText(getIntent().getStringExtra("questionuser"));

        //get questionID for the question
        final int id = getIntent().getIntExtra("questionID",0);
        id_st = Integer.toString(id);
        Log.d("Android :","Inside Answer ListActivity");
        Log.d("Android : ","QuestionID :"+id_st);


        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AnswerActivity.class);
                intent.putExtra("questionID",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        //Check internet connectivity
        if(InternetConnectivity.isConnected() == false)
        {
            Toast.makeText(this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
            return;
        }

        //Update the data in database
        AllAnswersTask allAnswersTask = new AllAnswersTask(this);
        allAnswersTask.execute(id_st);
        UpvoteAnswersTask upvoteAnswersTask = new UpvoteAnswersTask(this);
        upvoteAnswersTask.execute(id_st);
        UserUpvotesTask userUpvotesTask = new UserUpvotesTask(this);
        userUpvotesTask.execute(id_st, SignInActivity.user.getEmailId());

        //Update datasets
        answerListAdapter.setDataset(dataset);
        answerListAdapter.setRating(answerid,rating);
        answerListAdapter.setAnswered(answered_answer);
    }

    //Update the views
    public void update(ArrayList<Answer> dataset){
        this.dataset = dataset;
        answerListAdapter.setDataset(dataset);
    }

    public void updateRating(ArrayList<Integer>answerid,ArrayList<Integer>rating)
    {
        this.answerid = answerid;
        this.rating = rating;
        answerListAdapter.setRating(answerid,rating);
    }

    public void updateAnswered(ArrayList<Integer>answered_answer)
    {
        this.answered_answer = answered_answer;
        answerListAdapter.setAnswered(answered_answer);
    }

}
