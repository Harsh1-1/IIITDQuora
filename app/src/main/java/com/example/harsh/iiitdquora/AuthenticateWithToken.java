package com.example.harsh.iiitdquora;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class AuthenticateWithToken extends AsyncTask<String, Void, ArrayList<String>> {

    Context ctx;
    ProgressDialog loading;

    AuthenticateWithToken(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(ctx, "Signing in...", null,true,true);
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        String signinurl = "http://onlyforgeeks.net16.net/iiitdquora/googlesignin.php";
        String tokenID = strings[0];
        String activityName = strings[1];

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

            ArrayList<String>param = new ArrayList<String>();
            param.add(response);
            param.add(activityName);

            return param;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

            return null;
    }


    @Override
    protected void onPostExecute(ArrayList<String> results) {


        String[] server_response = results.get(0).split("@@@");
        String activityName = results.get(1);
        String result = server_response[1];
        loading.dismiss();

        if (result.equals("new user registered, click again to sign in")) {
            Toast.makeText(ctx, "new user registered, click again to sign in", Toast.LENGTH_LONG).show();
        } else if (result.equals("Failed to sign in")) {
            Toast.makeText(ctx, "Failed to sign in", Toast.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;

                String name="",email="",picture="";
                while (count < jsonArray.length()) {
                     JSONObject JO = jsonArray.getJSONObject(count);

                    name = JO.getString("name");
                    email = JO.getString("email");
                    picture = JO.getString("picture");
                    count++;
                }

                Log.d("Android","Inside authentication");



                SignInActivity.user = new User(email,name,"","",picture);
                if(activityName.equals("SignIn"))
                    ((SignInActivity)ctx).finishLogin();

                else if(activityName.equals("IIITD"))
                    ((IIITDQuoraActivity)ctx).finishLogin();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


}
