package com.example.harsh.iiitdquora;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputEmail = (EditText)findViewById(R.id.input_email);
        inputPassword = (EditText)findViewById(R.id.input_password);
        LoginButton = (Button)findViewById(R.id.login);
        RegisterLink = (TextView)findViewById(R.id.register);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String outEmail = inputEmail.getText().toString();
                String outPassword = inputPassword.getText().toString();

                /*
                * TODO
                * Boolean check = function_name(outEmail,outPassword);
                * */
            }
        });

        RegisterLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //TODO
            }
        });

    }
}
