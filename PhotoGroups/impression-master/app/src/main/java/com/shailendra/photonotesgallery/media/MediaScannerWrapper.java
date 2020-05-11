package com.shailendra.photonotesgallery.media;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;

/**
 * Created by shailendra on 25-06-2016.
 */
public class MediaScannerWrapper implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mediaScannerConnection;
    private String mpath;
    private String mMimeType;
    public MediaScannerWrapper(Context context, String filepath, String mime)
    {
        this.mpath=filepath;
        this.mMimeType=mime;
        mediaScannerConnection=new MediaScannerConnection(context,this);
    }
    public void scan(){
        mediaScannerConnection.connect();
    }
    @Override
    public void onMediaScannerConnected() {
              mediaScannerConnection.scanFile(mpath,mMimeType);
        Log.w("MediaScannerWrapper","media file scanned :"+mpath);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {

    }
}
