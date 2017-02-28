package com.example.monicamarcus.sharkfeed_flickr;

import android.app.Application;
import android.content.Context;

/**
 * Created by monicamarcus on 2/27/17.
 */

public class MyApplication extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

}