package com.example.harsh.iiitdquora;

import android.app.Application;

//Listener for connectivity

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
