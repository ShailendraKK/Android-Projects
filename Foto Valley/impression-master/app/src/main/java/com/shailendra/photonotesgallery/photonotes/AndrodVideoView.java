package com.shailendra.photonotesgallery.photonotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.shailendra.photonotesgallery.R;

/**
 * Created by shailendra on 10-04-2016.
 */
public class AndrodVideoView extends Activity {
    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaController;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        if (mediaController == null) {
            mediaController = new MediaController(AndrodVideoView.this);

        }
        myVideoView = (VideoView) findViewById(R.id.video_view);
        progressDialog = new ProgressDialog(AndrodVideoView.this);
        progressDialog.setTitle("Photo Notes Gallery Video Player");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        try {
            myVideoView.setMediaController(mediaController);
               

            if (Intent.ACTION_VIEW.equals(action) && type != null) {
                if (type.startsWith("video/")) {
                    Uri imageUri = (Uri) intent.getData();
                    if (imageUri != null) {
                        myVideoView.setVideoURI(imageUri);
                       AdView mAdView = (AdView) findViewById(R.id.ad_view);
                        AdRequest adRequest = new AdRequest.Builder()
                                //       .addTestDevice("0A6B37201CD257B683795D1FC30AB57C")

                                //   .addTestDevice("5A569188355EF91D42C1982244A07859")
                                .build();
                        mAdView.loadAd(adRequest);

                    }
                }
            }

        }
        catch (Exception e)
        {
           // Log.e("Error",e.getMessage());
           // e.printStackTrace();
        }
        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
                myVideoView.seekTo(position);
                if(position==0)
                {
                    myVideoView.start();
                }
                else
                {
                    myVideoView.pause();
                }
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position",myVideoView.getCurrentPosition());
        myVideoView.pause();

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        position=savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);

    }

}