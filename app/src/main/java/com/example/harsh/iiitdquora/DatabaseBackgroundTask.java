package com.example.harsh.iiitdquora;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
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

public class DatabaseBackgroundTask extends AsyncTask<String,String,String> {
    Context ctx;
    AlertDialog alertDialog;

    DatabaseBackgroundTask(Context ctx)
    {
        this.ctx = ctx;
    }


    @Override
    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login info");
    }



    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://onlyforgeeks.net16.net/iiitdquora/register.php";
        String login_url = "http://onlyforgeeks.net16.net/iiitdquora/login.php";
        String action = params[0];

        if (action.equals("register"))
        {
            String name = params[1];
            String email = params[2];
            String password = params[3];
            String phoneno = params[4];
            String about = params[5];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data =  URLEncoder.encode("user_name","UTF-8") + "=" + URLEncoder.encode(name,"UTF-8") + "&"
                        + URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                        +URLEncoder.encode("user_pass","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8") + "&"
                        +URLEncoder.encode("user_phoneno","UTF-8") + "=" + URLEncoder.encode(phoneno,"UTF-8") + "&"
                        +URLEncoder.encode("user_aboutme","UTF-8") + "=" + URLEncoder.encode(about,"UTF-8");

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

        }
        else if(action.equals("login"))
        {
            String email = params[1];
            String password = params[2];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("user_email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                        +URLEncoder.encode("user_pass","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        String[] details  = result.split(",");

        //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();

        if (details[0].equals("registration"))
        {
            Toast.makeText(ctx,details[1],Toast.LENGTH_LONG).show();
        }
        else
        {

            if(details[1].equals("Login Failed"))
            {
                alertDialog.setMessage(details[1]);
                alertDialog.show();
            }
            else
            {
                SignInActivity.user = new User(details[2],details[1],details[3],details[4],"");
                ((SignInActivity)ctx).finishLogin();
            }


        }



    }
}
