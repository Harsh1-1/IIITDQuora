package com.example.harsh.iiitdquora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnswerActivity extends AppCompatActivity {

    private EditText mAnswer;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        mAnswer = (EditText)findViewById(R.id.AnswerText);
        mSubmit = (Button)findViewById(R.id.answerButton);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = mAnswer.getText().toString();
                int questionID = getIntent().getIntExtra("questionID",0);

                Log.d("Android :","question ID is :"+ questionID);
                //CHECK context part
                AnswerBackgroundTask  task = new AnswerBackgroundTask(getApplicationContext());
                task.execute(answer,Integer.toString(questionID));
            }
        });

    }
}
