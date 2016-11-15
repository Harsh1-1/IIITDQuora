package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText inputEmail;
    private EditText inputPassword;
    private TextView RegisterLink;
    private Button LoginButton;
    private SignInButton signinButton;
    public static User user;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;

    // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);



        usernameWrapper.setHint("Email");
        passwordWrapper.setHint("Password");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.


        inputEmail = (EditText)findViewById(R.id.input_email);
        inputPassword = (EditText)findViewById(R.id.input_password);
        LoginButton = (Button)findViewById(R.id.login);
        RegisterLink = (TextView)findViewById(R.id.register);
        signinButton = (SignInButton) findViewById(R.id.sign_in_button);

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

    public void google(View view)
    {
        Toast.makeText(SignInActivity.this,"hello",Toast.LENGTH_SHORT).show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void finishLogin()
    {
        final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
