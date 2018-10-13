package com.theobencode.victoroben.backbasesample;

import android.app.Application;

public class BackbaseApp extends Application {

    private static BackbaseApp instance;

    // suppressing sonar lint warning for using a static variable in non static context.
    // Android Application classes are essentially singletons as it is, so using a static var is okay.
    @SuppressWarnings("squid:S2696")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BackbaseApp getContext() {
        return instance;
    }
}
