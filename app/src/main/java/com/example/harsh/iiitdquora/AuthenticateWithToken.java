package com.example.harsh.iiitdquora;

import android.os.AsyncTask;

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
 * Created by Tushar on 27-11-2016.
 */

public class AuthenticateWithToken extends AsyncTask<String, Void, Integer> {
    @Override
    protected Integer doInBackground(String... strings) {

         String signinurl = "www.";
        String tokenID = strings[0];

        try {
            URL url = new URL(signinurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);



            httpURLConnection.disconnect();


            return 0;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        return null;
    }
}
