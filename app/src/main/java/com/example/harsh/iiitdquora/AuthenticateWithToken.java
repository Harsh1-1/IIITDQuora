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

/**
 * Created by Tushar on 27-11-2016.
 */

public class AuthenticateWithToken extends AsyncTask<String, Void, String> {

    Context ctx;

    AuthenticateWithToken(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

         String signinurl = "http://onlyforgeeks.net16.net/iiitdquora/googlesignin.php";
        String tokenID = strings[0];

        try {
            URL url = new URL(signinurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("id_token", "UTF-8") + "=" + URLEncoder.encode(tokenID, "UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
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
    protected void onPostExecute(String result) {


        String[] server_response = result.split("@@@");
        result = server_response[1];

        if (result.equals("No Results found")) {
            Toast.makeText(ctx, "No results found", Toast.LENGTH_LONG).show();
        } else if (result.equals("Failed to fetch questions")) {
            Toast.makeText(ctx, "Failed to retrieve results", Toast.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;

                String name,email,picture;
                ArrayList<Question> questionArrayList = new ArrayList<>();
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    questionid = JO.getInt("QuestionID");
                    description = JO.getString("Description");
                    createdby = JO.getString("Createdby");
                    createdon = JO.getString("Createdon");
                    questiontext = JO.getString("Questiontext");
                    Question question = new Question(questionid, description, createdby, createdon, questiontext);
                    questionArrayList.add(question);
                    count++;
                }

                SignInActivity.user = new User(details[2],details[1],details[3],details[4],"");
                ((SignInActivity)ctx).finishLogin();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


}
