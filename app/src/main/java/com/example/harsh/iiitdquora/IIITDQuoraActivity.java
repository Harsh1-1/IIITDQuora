package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IIITDQuoraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iiitdquora);
        final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        //TODO: A lot of things
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }


}
