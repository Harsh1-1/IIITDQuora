package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private TextView RegisterLink;
    private Button LoginButton;
    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputEmail = (EditText)findViewById(R.id.input_email);
        inputPassword = (EditText)findViewById(R.id.input_password);
        LoginButton = (Button)findViewById(R.id.login);
        RegisterLink = (TextView)findViewById(R.id.register);


        RegisterLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                final Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    public void userLogin(View view)
    {
        String outEmail = inputEmail.getText().toString();
        String outPassword = inputPassword.getText().toString();

        if(outEmail.equals("") || outPassword.equals(""))
        {
            Toast.makeText(SignInActivity.this,"Fields cannot be left blank",Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseBackgroundTask dbt = new DatabaseBackgroundTask(this);
        dbt.execute("login",outEmail,outPassword);

    }

    public void finishLogin()
    {
        final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
