package com.example.harsh.iiitdquora;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IIITDQuoraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iiitdquora);
        //final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        final Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //TODO: A lot of things
        finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }


}
