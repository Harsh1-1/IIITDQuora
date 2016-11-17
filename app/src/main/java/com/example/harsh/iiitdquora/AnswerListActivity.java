package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswerListActivity extends AppCompatActivity {

    AnswerListAdapter answerListAdapter;
    RecyclerView answerRecyclerView;
    RecyclerView.LayoutManager answerRecyclerViewLayoutManager;
    ArrayList<Answer> dataset;

    private TextView QuestionText;
    private TextView QuestionCreatedby;
    private TextView QuestionDesc;
    private Button addAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);

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


        final int id = getIntent().getIntExtra("questionID",0);
        String id_st = Integer.toString(id);
        Log.d("Android :","Inside Answer ListActivity");
        Log.d("Android : ","QuestionID :"+id_st);

        AllAnswersTask allAnswersTask = new AllAnswersTask(this);
        allAnswersTask.execute(id_st);

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

    public void update(ArrayList<Answer> dataset){
        this.dataset = dataset;
        answerListAdapter.setDataset(dataset);
    }
}
