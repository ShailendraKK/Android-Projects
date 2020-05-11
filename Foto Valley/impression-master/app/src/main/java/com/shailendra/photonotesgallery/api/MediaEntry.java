package com.shailendra.photonotesgallery.api;

import android.app.Activity;
import android.content.Context;

import java.io.Serializable;

public interface MediaEntry extends Serializable {

    long getId();

    String getData();

    long getSize();

    long getFolderSize();
    /*String title();*/

    String getDisplayName(Context context);

    String getMimeType();

    /*long dateAdded();*/

    /*long dateModified();*/

    long getDateTaken();

    int getWidth();

    int getHeight();

    boolean isVideo();

    boolean isFolder();

    void delete(Activity context);

    void setfolderSize(long size);

}
