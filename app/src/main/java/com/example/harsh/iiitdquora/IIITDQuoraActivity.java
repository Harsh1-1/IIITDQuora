package com.example.harsh.iiitdquora;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class IIITDQuoraActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, InternetConnectivity.ConnectivityReceiverListener {

    private static int SPLASH_TIME = 1000;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iiitdquora);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().setHostedDomain("iiitd.ac.in").requestId().requestIdToken(getString(R.string.server_client_id))
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        //final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);


/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //TODO: A lot of things
                finish();
            }
        },SPLASH_TIME);
    */
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d("Android :","FAiled internet connection");
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
                Log.d("Sign In", acct.getEmail());
                Log.d("Detail", acct.getId());
                Log.d("tokenID", acct.getIdToken());
                Log.d("gdf", acct.getDisplayName());

                AuthenticateWithToken authenticateWithToken = new AuthenticateWithToken(this);
                authenticateWithToken.execute(acct.getIdToken(), "IIITD");


            } else {

                Log.d("Android", "IIIT");
                final Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //TODO: A lot of things
                finish();
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


    public void finishLogin()
    {
        final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
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

/*    @Override
    protected void onStart()
    {
      super.onStart();
    }
**/

}
