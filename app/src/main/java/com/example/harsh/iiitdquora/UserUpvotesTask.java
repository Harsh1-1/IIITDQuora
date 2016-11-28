package com.example.harsh.iiitdquora;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;


public class UserUpvotesTask extends AsyncTask<String,String,String> {

    Context ctx;

    UserUpvotesTask(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String retrieving_url = "http://onlyforgeeks.net16.net/iiitdquora/userupvotesonanswers.php";
        String quesid = params[0];
        String email = params[1];
        try {
            URL url = new URL(retrieving_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data = URLEncoder.encode("ques_id","UTF-8") + "=" + URLEncoder.encode(quesid,"UTF-8")+ "&"
                    +URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine())!=null)
            {
                response = response + line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return response;


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
        String[] server_response = result.split("@@@");
        result=server_response[1];

        if(result.equals("No Answer upvoted"))
        {
            Toast.makeText(ctx,"please upvote answer you like most",Toast.LENGTH_LONG).show();
        }
        else if(result.equals("Failed to fetch upvotes"))
        {
            Toast.makeText(ctx,"Failed to fetch user upvotes",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                Integer answerid,rating;
                String interest;
                ArrayList<Integer> answeridArrayList = new ArrayList<>();
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    answerid = JO.getInt("answer_id");
                    answeridArrayList.add(answerid);
                    count++;
                }

                ((AnswerListActivity)ctx).updateAnswered(answeridArrayList);
                //put appropriate method here to get arraylist of answerids


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
