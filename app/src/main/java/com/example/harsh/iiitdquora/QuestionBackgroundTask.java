package com.example.harsh.iiitdquora;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by harsh on 22-10-2016.
 */

public class QuestionBackgroundTask extends AsyncTask<String,String,String> {

    Context ctx;

    QuestionBackgroundTask(Context ctx)
    {
        this.ctx = ctx;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String askurl = "http://onlyforgeeks.net16.net/iiitdquora/askquestion.php";
        String email = params[0];
        String question = params[1];
        String questiondescription = params[2];


        try {
            URL url = new URL(askurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data =  URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                    + URLEncoder.encode("user_question","UTF-8") + "=" + URLEncoder.encode(question,"UTF-8") + "&"
                    +URLEncoder.encode("question_description","UTF-8") + "=" + URLEncoder.encode(questiondescription,"UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            httpURLConnection.disconnect();
            inputStream.close();

            return "question saved successfully";



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

            Toast.makeText(ctx,"Question posted successfully!!",Toast.LENGTH_LONG).show();

    }
}
