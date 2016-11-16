package com.example.harsh.iiitdquora;

import android.content.Context;
import android.view.View;

/**
 * Created by Abhi on 16-11-2016.
 */

public class QuestionListOnClickListener implements View.OnClickListener {

    private int questionID;
    private Context context;

    public QuestionListOnClickListener(int questionID, Context context){
        this.questionID = questionID;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        //TODO: Launch an activity with the given QuestionID
    }
}
