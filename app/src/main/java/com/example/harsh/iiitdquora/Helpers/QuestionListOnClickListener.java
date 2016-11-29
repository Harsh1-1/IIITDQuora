package com.example.harsh.iiitdquora.Helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.harsh.iiitdquora.AnswerListActivity;

/*
CLASS NAME : QuestionListOnClickListener
PURPOSE : Listener Class to handle when a question is clicked
*/

public class QuestionListOnClickListener implements View.OnClickListener {

    private int questionID;
    private String questionText;
    private String questionDescription;
    private String Questionuser;
    private Context context;

    public QuestionListOnClickListener(int questionID, String questionText,String questionDescription,String Questionuser, Context context){
        this.questionID = questionID;
        this.context = context;
        this.questionText = questionText;
        this.questionDescription = questionDescription;
        this.Questionuser = Questionuser;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context,AnswerListActivity.class);
        Log.d("Question id :","Question id"+questionID);
        intent.putExtra("questionID",questionID);
        intent.putExtra("questionText",questionText);
        intent.putExtra("questionDesc",questionDescription);
        intent.putExtra("questionuser",Questionuser);


        context.startActivity(intent);


        //TODO: Launch an activity with the given QuestionID
    }
}
