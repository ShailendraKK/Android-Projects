package com.shailendra.photonotesgallery.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shailendra.photonotesgallery.App;

/**
 * Created by shailendra on 25-06-2016.
 */
public class HeadSetIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("HeadSetintentReceiver","Intent received");
        if(intent.getAction().equals(Intent.ACTION_HEADSET_PLUG))
        {
            int state=intent.getIntExtra("state",-1);
            if(state==1) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                String action = preferences.getString("launch_mode", null);
                String enabled = preferences.getString("headset_detection", null);
                if (enabled != null) {
                    if(enabled.equals("enabled")) {
                        if (action != null) {
                            if (action.equals("myapp")) {
                                Log.v("HeadSetintentReceiver", "HeadSet is Plugged from PNG");
                                Intent intent1 = new Intent(context, MainActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(intent1);
                            } else if (action.equals("otherapp")) {
                                preferences = PreferenceManager.getDefaultSharedPreferences(context);

                                String pkgname = preferences.getString("app", null);
                                Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(pkgname);
                                context.startActivity(LaunchIntent);
                            }
                        }
                    }
                }
            }
        }
    }
}
