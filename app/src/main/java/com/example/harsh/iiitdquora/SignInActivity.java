package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh.iiitdquora.Helpers.InternetConnectivity;
import com.example.harsh.iiitdquora.Helpers.MyApplication;
import com.example.harsh.iiitdquora.beans.User;
import com.example.harsh.iiitdquora.tasks.AuthenticateWithToken;
import com.example.harsh.iiitdquora.tasks.DatabaseBackgroundTask;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
//Sign in Activity
public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,InternetConnectivity.ConnectivityReceiverListener {

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
                .requestEmail().setHostedDomain("iiitd.ac.in").requestId().requestIdToken(getString(R.string.server_client_id))
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
        signinButton.setSize(SignInButton.SIZE_STANDARD);

        //just temporary login credentials
        inputEmail.setText("test1@iiitd.ac.in");
        inputPassword.setText("abc123");

        signinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(SignInActivity.this,"hello",Toast.LENGTH_SHORT).show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


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

        if(InternetConnectivity.isConnected() == false)
        {
            Toast.makeText(this, "No internet connectivity ", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            dbt.execute("login", outEmail, outPassword);
        }

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

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public  void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {

        if (InternetConnectivity.isConnected() == false) {
            // Toast.makeText(this, "No internet connectivity ", Toast.LENGTH_SHORT).show();
            return;
        }
        else {


            if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("Sign In",acct.getEmail());
            Log.d("Detail",acct.getId());
            Log.d("tokenID", acct.getIdToken());
            Log.d("gdf", acct.getDisplayName());

            AuthenticateWithToken authenticateWithToken = new AuthenticateWithToken(this);
            authenticateWithToken.execute(acct.getIdToken(),"SignIn");

        }
            else {
                // Signed out, show unauthenticated UI.
                }
        }

    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if(isConnected == false)
        {
            Toast.makeText(this,"No internet connectivity here",Toast.LENGTH_SHORT).show();
            return;
        }

        else if(isConnected == true)
        {

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.

                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.

                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {

                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }

    }
}
