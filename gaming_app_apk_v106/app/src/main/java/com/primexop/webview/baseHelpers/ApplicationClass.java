package com.primexop.webview.baseHelpers;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;


import com.primexop.webview.R;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        final String ONESIGNAL_APP_ID =  getString(R.string.onesignal_app_id);

        Log.d(TAG, "AC onesignal  "+ONESIGNAL_APP_ID);
        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // optIn will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.getUser().getPushSubscription().optIn();
    }

}
