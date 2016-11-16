package com.example.harsh.iiitdquora;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Tushar on 17-11-2016.
 */

public class AnswerBackgroundTask extends AsyncTask<String,String,String> {

    Context ctx;

    AnswerBackgroundTask(Context context){this.ctx = context;}

    @Override
    protected String doInBackground(String... strings) {
        String answer = strings[0];
        int questionID = Integer.parseInt(strings[1]);


        return null;
    }
}
