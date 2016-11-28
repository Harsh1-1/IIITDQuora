package com.example.harsh.iiitdquora;

import android.app.ProgressDialog;
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

//Task for posting a question
public class QuestionBackgroundTask extends AsyncTask<String,String,String> {

    Context ctx;
    ProgressDialog loading;

    QuestionBackgroundTask(Context ctx)
    {
        this.ctx = ctx;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(ctx, "Posting Question...", null,true,true);
    }

    @Override
    protected String doInBackground(String... params) {

        String askurl = "http://onlyforgeeks.net16.net/iiitdquora/askquestion.php";
        String email = params[0];
        String question = params[1];
        String questiondescription = params[2];
        String categoryid = params[3];
        String categoryname = params[4];


        try {
            URL url = new URL(askurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data =  URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                    + URLEncoder.encode("user_question","UTF-8") + "=" + URLEncoder.encode(question,"UTF-8") + "&"
                    +URLEncoder.encode("question_description","UTF-8") + "=" + URLEncoder.encode(questiondescription,"UTF-8")+ "&"
                    +URLEncoder.encode("category_id","UTF-8") + "=" + URLEncoder.encode(categoryid,"UTF-8")+ "&"
                    +URLEncoder.encode("category_name","UTF-8") + "=" + URLEncoder.encode(categoryname,"UTF-8");

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
            loading.dismiss();
            Toast.makeText(ctx,"Question posted successfully!!",Toast.LENGTH_LONG).show();

    }
}
