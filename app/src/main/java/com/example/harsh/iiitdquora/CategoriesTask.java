package com.example.harsh.iiitdquora;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;
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


public class CategoriesTask extends AsyncTask<String,String,String>{

    Context ctx;

    CategoriesTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String retrieving_url = "http://onlyforgeeks.net16.net/iiitdquora/retrievecategorieslist.php";

        try {
            URL url = new URL(retrieving_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);

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
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        String[] server_response = result.split("@@@");
        result = server_response[1];

        if (result.equals("No Categories List exist")) {
            Toast.makeText(ctx, "No Category exist!!", Toast.LENGTH_LONG).show();
        } else if (result.equals("Failed to fetch Categories")) {
            Toast.makeText(ctx, "Failed to fetch Categories", Toast.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                int interestid;
                String interest;
                ArrayList<Categories> categoriesArrayList = new ArrayList<>();
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    interestid = JO.getInt("interest_id");
                    interest = JO.getString("interest");
                    Categories category = new Categories(interestid,interest);
                    categoriesArrayList.add(category);
                    count++;
                }

                //put appropriate method here
                HomeActivity.updateCategories(categoriesArrayList);
                //((UserProfileFragment)ctx).setAllCategories(categoriesArrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
