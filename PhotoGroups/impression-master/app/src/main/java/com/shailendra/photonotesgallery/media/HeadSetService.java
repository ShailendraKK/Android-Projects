package com.shailendra.photonotesgallery.media;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by shailendra on 25-06-2016.
 */
public class HeadSetService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
    super.onCreate();
        IntentFilter receivedFilter=new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        HeadSetIntentReceiver receiver=new HeadSetIntentReceiver();
        registerReceiver(receiver,receivedFilter);
    }
}
