package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harsh.iiitdquora.Helpers.InternetConnectivity;
import com.example.harsh.iiitdquora.tasks.DatabaseBackgroundTask;

//Sign up activity
public class SignUpActivity extends AppCompatActivity {

    private EditText registerUsername;
    private EditText registerPassword;
    private EditText registerAboutMe;
    private EditText registerEmail;
    private EditText registerPasswordagain;
    private EditText registerContact;
    private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerUsername = (EditText)findViewById(R.id.register_username);
        registerAboutMe = (EditText)findViewById(R.id.register_aboutme);
        registerEmail = (EditText)findViewById(R.id.register_email);
        registerPassword = (EditText)findViewById(R.id.register_password);
        registerPasswordagain = (EditText)findViewById(R.id.register_passwordagain);
        registerContact = (EditText)findViewById(R.id.register_contact);
        RegisterButton = (Button)findViewById(R.id.register);

    }




    public void userRegistration(View view)
    {
        //get user details
        String outEmail = registerEmail.getText().toString();
        String outPassword = registerPassword.getText().toString();
        String outUsername = registerUsername.getText().toString();
        String outContact = registerContact.getText().toString();
        String outAboutme = registerAboutMe.getText().toString();
        String outPasswordAgain = registerPasswordagain.getText().toString();


        //Verify email
        String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@iiitd.ac.in";
        Boolean value = outEmail.matches(emailregex);

        if(value == false)
        {
            Toast.makeText(SignUpActivity.this,"Email does not belong to IIITD domain",Toast.LENGTH_SHORT).show();
            return;
        }


        //Basic checks
        if(outPassword.equals("") || outEmail.equals("") || outUsername.equals(""))
        {
            Toast.makeText(SignUpActivity.this,"Fields cannot be left blank",Toast.LENGTH_SHORT).show();
            return;
        }

        if(outPassword.equals(outPasswordAgain) == false)
        {
            Toast.makeText(SignUpActivity.this," Password don't match",Toast.LENGTH_SHORT).show();
            return;
        }

        if(InternetConnectivity.isConnected() == false)
        {
            Toast.makeText(this, "No internet connectivity ", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            //Register the user
            DatabaseBackgroundTask dbt = new DatabaseBackgroundTask(this);
            dbt.execute("register", outUsername, outEmail, outPassword, outContact, outAboutme);
        }

            final Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);

    }
}
