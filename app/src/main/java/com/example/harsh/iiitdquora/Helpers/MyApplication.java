package com.example.harsh.iiitdquora.Helpers;

import android.app.Application;

/*
CLASS NAME : MyApplication
PURPOSE : Listener for connectivity
*/

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(InternetConnectivity.ConnectivityReceiverListener listener) {
        InternetConnectivity.connectivityReceiverListener = listener;
    }
}
