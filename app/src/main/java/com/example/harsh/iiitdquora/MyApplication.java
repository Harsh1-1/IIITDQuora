package com.example.harsh.iiitdquora;

import android.app.Application;

/**
 * Created by Tushar on 28-11-2016.
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
