package com.example.harsh.iiitdquora.tasks;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.harsh.iiitdquora.beans.Categories;
import com.example.harsh.iiitdquora.UserProfileFragment;

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

//Task for retrieving user interests
public class RetrieveInterestTask extends AsyncTask<String,String,String> {

    Fragment ctx;

    public RetrieveInterestTask(Fragment ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String retrieving_url = "http://onlyforgeeks.net16.net/iiitdquora/retrieveuserinterests.php";
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
        ArrayList<Categories> categoriesArrayList = new ArrayList<>();
        if(result.equals("No Interests exist"))
        {
            Toast.makeText(ctx.getContext(),"You do not have any interests",Toast.LENGTH_LONG).show();
            ((UserProfileFragment)ctx).setUserInterests(categoriesArrayList);
        }
        else if(result.equals("Failed to fetch interest Category"))
        {
            Toast.makeText(ctx.getContext(),"Failed to fetch interest Category",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                int interestid;
                String interest;

                while (count < jsonArray.length()) {
                    //parse json to fill categories array
                    JSONObject JO = jsonArray.getJSONObject(count);
                    interestid = JO.getInt("interest_id");
                    interest = JO.getString("interest");
                    Categories category = new Categories(interestid,interest);
                    categoriesArrayList.add(category);
                    count++;
                }

                //put appropriate method here
                ((UserProfileFragment)ctx).setUserInterests(categoriesArrayList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}
