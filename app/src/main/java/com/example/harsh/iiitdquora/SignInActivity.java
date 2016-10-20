package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        DatabaseBackgroundTask dbt = new DatabaseBackgroundTask(this);
        dbt.execute("login",outEmail,outPassword);
        if(user!=null)
        {
            final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }
    }
}
