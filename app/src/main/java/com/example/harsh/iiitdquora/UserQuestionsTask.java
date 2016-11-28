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
import java.util.Date;

/**
 * Created by harsh on 16-11-2016.
 */

public class UserQuestionsTask extends AsyncTask <String,String,String>{

    Context ctx;

    UserQuestionsTask(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String retrieving_url = "http://onlyforgeeks.net16.net/iiitdquora/retrieveuserquestions.php";
        String email = params[0];
        try {
            URL url = new URL(retrieving_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data = URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8");

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
        ArrayList<Question> questionArrayList = new ArrayList<>();
        if(result.equals("No Questions Asked"))
        {
            ((HomeActivity)ctx).updateAsked(questionArrayList);
            Toast.makeText(ctx,"You did not ask any questions yet!!",Toast.LENGTH_LONG).show();

        }
        else if(result.equals("Failed to fetch user questions"))
        {
            Toast.makeText(ctx,"Failed to fetch your questions",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                int questionid,categoryid;
                String description,createdby,createdon,questiontext,categoryname;
                while (count<jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    questionid = JO.getInt("QuestionID");
                    description = JO.getString("Description");
                    createdby = JO.getString("Createdby");
                    createdon = JO.getString("Createdon");
                    questiontext = JO.getString("Questiontext");
                    categoryid = JO.getInt("Categoryid");
                    categoryname = JO.getString("Categoryname");
                    Question question = new Question(questionid,description,createdby,createdon,questiontext,categoryid,categoryname);
                    questionArrayList.add(question);
                    count++;
                }
                ((HomeActivity)ctx).updateAsked(questionArrayList);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
}
