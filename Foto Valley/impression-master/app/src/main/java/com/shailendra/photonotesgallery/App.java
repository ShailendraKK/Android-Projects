package com.shailendra.photonotesgallery;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.afollestad.inquiry.Inquiry;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
  public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        Inquiry.init(this, "impression", 1);
      context=getApplicationContext();
      //  LeakCanary.install(this);
    }
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}