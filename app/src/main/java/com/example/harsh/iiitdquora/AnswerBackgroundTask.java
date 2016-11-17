package com.example.harsh.iiitdquora;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Tushar on 17-11-2016.
 */

public class AnswerBackgroundTask extends AsyncTask<String,String,String> {

    Context ctx;

    AnswerBackgroundTask(Context context){this.ctx = context;}

    @Override
    protected String doInBackground(String... strings) {

        String askurl = "http://onlyforgeeks.net16.net/iiitdquora/saveanswer.php";

        String answer = strings[0];
        String questionID = strings[1];
        String email = SignInActivity.user.getEmailId();



        try {
            URL url = new URL(askurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data =  URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                    + URLEncoder.encode("ques_id","UTF-8") + "=" + URLEncoder.encode(questionID,"UTF-8") + "&"
                    +URLEncoder.encode("user_answer","UTF-8") + "=" + URLEncoder.encode(answer,"UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            httpURLConnection.disconnect();
            inputStream.close();

            return "Answer saved successfully";



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
