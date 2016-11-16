package com.example.harsh.iiitdquora;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IIITDQuoraActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iiitdquora);
        //final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //TODO: A lot of things
                finish();
            }
        },SPLASH_TIME);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }


}
