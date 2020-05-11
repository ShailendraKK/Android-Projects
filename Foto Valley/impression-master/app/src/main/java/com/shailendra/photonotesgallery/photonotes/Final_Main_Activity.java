package com.shailendra.photonotesgallery.photonotes;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
//import com.google.android.gms.ads.VideoController;
//import com.google.android.gms.ads.VideoOptions;
import com.shailendra.photonotesgallery.BuildConfig;
import com.shailendra.photonotesgallery.R;
import com.shailendra.photonotesgallery.media.HeadSetActivity;
import com.shailendra.photonotesgallery.media.MainActivity;
import com.shailendra.photonotesgallery.media.MediaScanActivity;
import com.shailendra.photonotesgallery.media.SizeActivity;
import com.shailendra.photonotesgallery.media.TagActivity;


/**
 * Created by shailendra on 25-02-2017.
 */

public class Final_Main_Activity extends Activity {
    GridView grid;
    NativeExpressAdView mAdView;
  //  VideoController mVideoController;
    String[] web = {

            "Photo Notes",
            "Gallery",

            "HeadSet Connect Automate",
            "Media Usage",
            "Media Rescan",


            "About"




    } ;
    public static final String PLACEMENT_ID = "203890";
    int[] imageId = {
            R.drawable.camera,
            R.drawable.gallerywhite,
            R.drawable.headphone,
            R.drawable.pie,
            R.drawable.media_rescan,


            R.drawable.about


    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_main_menu);

        CustomGrid adapter = new CustomGrid(Final_Main_Activity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // Toast.makeText(Final_Main_Activity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
              Intent intent;
                switch(position)
                {
                    case 0:
                        intent=new Intent(Final_Main_Activity.this,TagActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                       intent=new Intent(Final_Main_Activity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                       intent= new Intent(Final_Main_Activity.this,SizeActivity.class);
                       startActivity(intent);
                        break;
                    case 2:
                        intent= new Intent(Final_Main_Activity.this,HeadSetActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent= new Intent(Final_Main_Activity.this,MediaScanActivity.class);
                        startActivity(intent);
                        break;
                    case 5:    new MaterialDialog.Builder(Final_Main_Activity.this)
                            .title(getString(R.string.about_dialog_title, BuildConfig.VERSION_NAME))
                            .positiveText(R.string.dismiss)
                            .content(Html.fromHtml(getString(R.string.about_body)))
                            .iconRes(R.mipmap.ic_launcher)
                            .linkColor(getResources().getColor(R.color.colorAccent))
                            .show();

                        break;
                }
            }
        });
        // Locate the NativeExpressAdView.
        mAdView = (NativeExpressAdView) findViewById(R.id.adView);

        // Set its video options.
      //  mAdView.setVideoOptions(new VideoOptions.Builder()
        //        .setStartMuted(true)
          //      .build());

        // The VideoController can be used to get lifecycle events and info about an ad's video
        // asset. One will always be returned by getVideoController, even if the ad has no video
        // asset.
     /*   mVideoController = mAdView.getVideoController();
        mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {
              //  Log.d(LOG_TAG, "Video playback is finished.");
                super.onVideoEnd();
            }
        });*/

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
        });
        mAdView.loadAd(new AdRequest.Builder().build());


    }
}
