package com.primexop.webview.baseHelpers;


import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

/*
 * JavaScript Interface. Web code can access methods in here
 * (as long as they have the @JavascriptInterface annotation)
 */
public class JavaScriptInterface {
    private final Context mContext;
    public WebView mWebView;
    public Window mWindow;
    public FrameLayout mHorizontalProgressFrameLayout;
    public JavaScriptInterface(Context context, WebView webView, Window window, FrameLayout horizontalProgressFrameLayout) {
        mContext = context;
        mWebView=webView;
        mWindow=window;
        mHorizontalProgressFrameLayout=horizontalProgressFrameLayout;
    }


    public String externalIdString="null";



    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    public void passObj(String data,String jsCallback) {
        final String jsCallbackCode=jsCallback;
        String d=data;
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StringBuffer buf = new StringBuffer(d);
                    String s = buf.reverse().toString();
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:" + jsCallbackCode + "(" + true + ",'"+s+"');void(0);");
                        }
                    });
                }
            }, 5000);
     } catch (Exception ex) { mWebView.loadUrl("javascript:" + jsCallbackCode + "(" + false + ","+"error"+");void(0);");}

    }

    @JavascriptInterface
    public void showToast( String msg){

        Log.d(TAG, " primeTest ToastMsg:"+msg);
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();

//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        mWindow.setStatusBarColor(Color.parseColor("#FF0000"));

    }
    @JavascriptInterface
    public void setStatusBarColor( String hexString){



//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(Color.parseColor(hexString));
        mHorizontalProgressFrameLayout.setBackgroundColor(Color.parseColor(hexString));
    }


    @JavascriptInterface
    public void openInBrowser(String url) {
        Log.d(TAG, " primeTest openInBrowser:"+url);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mContext.startActivity(browserIntent);


    }

    @JavascriptInterface
    public void passData(String jsonData) {
        Log.d(TAG, " primeTest jsonData:"+jsonData);

        try {
            JSONObject data = new JSONObject(jsonData); //Convert from string to object, can also use JSONArray
            Log.d(TAG, " primeTest data:"+data);
            String name = data.getString("name");
            Number number = data.getInt("number");
            Log.d(TAG, " primeTest name  number:"+name + number);
        }  catch(Exception e) {

            Log.d(TAG, "Pxop Error"+ e);
        }


    }
    @JavascriptInterface
    public void shareMessage(String jsonData)  {


        try {
            JSONObject data = new JSONObject(jsonData); //Convert from string to object, can also use JSONArray
            Log.d(TAG, " primeTest data:"+data);
            String shareMessage = data.getString("message");


            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "appName");

            // String   shareMessage =text;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            mContext.startActivity(shareIntent);
        } catch(Exception e) {
            Log.d(TAG, "Pxop Error"+ e);
        }

    }


    @JavascriptInterface
//this inter face disabled
    public void initOnesignal___( String jsonData){
        try{
//            JSONObject data = new JSONObject(jsonData);
//            Log.d(TAG, "onesignal initiated");
//            String appId = data.getString("appId");
//            //  final String ONESIGNAL_APP_ID = "cb424881-2e7b-4a48-93fd-d27e81d692f7";
//
//
//            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//            // OneSignal Initialization
//            OneSignal.initWithContext(mContext);
//            OneSignal.setAppId(appId);
//
//            // promptForPushNotifications will show the native Android notification permission prompt.
//            // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
//            OneSignal.promptForPushNotifications();

            // Set the initialization handler to call a function when initialization is complete

        } catch(Exception e) {
            Log.d(TAG, "Pxop Error"+ e);
        }

    }



    @JavascriptInterface

    public void getOnesignalPlayerId(){
        try{

//            OSDeviceState deviceState = OneSignal.getDeviceState();
//            if (deviceState != null) {
//                String playerId  = deviceState.getUserId();
//                // Use playerId as needed
//                Log.d(TAG, "getOnesignalPlayerId "+playerId);
//                // Call a JavaScript function
//
//                mWebView.post(() -> mWebView.evaluateJavascript("onesignalPlayerIdFun('success','"+playerId+"')",null));
//
//            } else {
//                // Device state is not available yet
//                Log.d(TAG, "getOnesignalPlayerId  not found  ");
//
//
//                mWebView.post(() -> mWebView.evaluateJavascript("onesignalPlayerIdFun('error','')",null));
//            }
        } catch(Exception e) {

            Log.d(TAG, "Pxop Error"+ e);
        }

    }

    @JavascriptInterface

    public void setOnesignalExternalId( String jsonData){
        try{
            JSONObject data = new JSONObject(jsonData);

            String externalId = data.getString("externalId");
            Log.d(TAG, "onesignal setOnesignalExternalId"+externalId+"-"+externalIdString);
           // OneSignal.setExternalUserId(externalId);
            if(!externalId.equals(externalIdString)){

                // Setting External User Id with Callback Available in SDK Version 4.0.0+
                OneSignal.login(externalId);

            }

        } catch(Exception e) {
            Log.d(TAG, "Pxop Error"+ e);
        }

    }

    @JavascriptInterface

    public void removeOnesignalExternalId(){
        try{

            OneSignal.logout();
            Log.d(TAG, "onesignal removeExternalUserId");

        } catch(Exception e) {
            Log.d(TAG, "Pxop Error"+ e);
        }

    }




}



