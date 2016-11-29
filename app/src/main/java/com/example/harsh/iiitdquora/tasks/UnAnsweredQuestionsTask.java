package com.example.harsh.iiitdquora.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.harsh.iiitdquora.HomeActivity;
import com.example.harsh.iiitdquora.beans.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//Task for retrieving all un answered questions
public class UnAnsweredQuestionsTask extends AsyncTask <String,String,String>{

    Context ctx;

    public UnAnsweredQuestionsTask(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String retrieving_url = "http://onlyforgeeks.net16.net/iiitdquora/unansweredquestion.php";
        try {
            URL url = new URL(retrieving_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);

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
        if(result.equals("No unanswered Questions :D"))
        {
            ((HomeActivity)ctx).updateAnswer(questionArrayList);
            Toast.makeText(ctx,"No unanswered Questions :D",Toast.LENGTH_LONG).show();

        }
        else if(result.equals("Failed to fetch questions"))
        {
            Toast.makeText(ctx,"Failed to fetch questions",Toast.LENGTH_SHORT).show();
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
                for(int i=0; i<count; i++)
                {
                    System.out.println(questionArrayList.get(i));
                }
                ((HomeActivity)ctx).updateAnswer(questionArrayList);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
}
