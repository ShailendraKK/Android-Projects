package com.shailendra.photonotesgallery.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shailendra on 25-06-2016.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service=new Intent(context,HeadSetService.class);
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)||intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            context.startService(service);
            Log.v("MyReceiver","MyReceiver Intent started");
        }
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            context.stopService(service);
            Log.v("MyReceiver","MyReceiver Intent stopped");
        }
    }
}
