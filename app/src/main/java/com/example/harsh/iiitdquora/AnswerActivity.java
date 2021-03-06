package com.example.harsh.iiitdquora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harsh.iiitdquora.Helpers.InternetConnectivity;
import com.example.harsh.iiitdquora.tasks.AnswerBackgroundTask;

//Activity Class for Answering questions
public class AnswerActivity extends AppCompatActivity {

    private EditText mAnswer;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.answerActivityToolbar );
        setSupportActionBar(myToolbar);



        mAnswer = (EditText)findViewById(R.id.AnswerText);
        mSubmit = (Button)findViewById(R.id.answerButton);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = mAnswer.getText().toString();
                int questionID = getIntent().getIntExtra("questionID",0);

                Log.d("Android :","question ID is :"+ questionID);
                //CHECK context part

                //Check Internet Connectivity
                if(InternetConnectivity.isConnected() == false)
                {
                    Toast.makeText(getApplicationContext(), "No internet connectivity ", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Update answer in database
                AnswerBackgroundTask task = new AnswerBackgroundTask(getApplicationContext());
                task.execute(answer,Integer.toString(questionID));
                mAnswer.setText("");
            }
        });

    }
}
