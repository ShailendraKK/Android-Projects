package com.shailendra.photonotesgallery.api;

import android.app.Activity;
import android.content.Context;
import android.provider.MediaStore;
import android.widget.Toast;

import com.afollestad.inquiry.annotations.Column;

import java.io.File;

public class LocalMediaEntry implements MediaEntry {

    public boolean mIsVideo;
    public long foldeSize;
    @Column(name = MediaStore.Images.Media._ID)
    protected long _id;
    @Column(name = MediaStore.Images.Media.DATA)
    protected String _data;
    @Column(name = MediaStore.Images.Media.SIZE)
    protected long _size;
    @Column(name = MediaStore.Images.Media.TITLE)
    protected String title;
    @Column(name = MediaStore.Images.Media.DISPLAY_NAME)
    protected String _displayName;
    @Column(name = MediaStore.Images.Media.MIME_TYPE)
    protected String mimeType;
    @Column(name = MediaStore.Images.Media.DATE_ADDED)
    protected long dateAdded;
    @Column(name = MediaStore.Images.Media.DATE_TAKEN)
    protected long dateTaken;
    @Column(name = MediaStore.Images.Media.DATE_MODIFIED)
    protected long dateModified;
    @Column(name = MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    protected String bucketDisplayName;
    @Column(name = MediaStore.Images.Media.BUCKET_ID)
    protected String bucketId;
    @Column(name = MediaStore.Images.Media.WIDTH)
    protected int width;
    @Column(name = MediaStore.Images.Media.HEIGHT)
    protected int height;

    public LocalMediaEntry() {
    }

    public LocalMediaEntry(boolean isVideo) {
        mIsVideo = isVideo;
    }

    @Override
    public long getId() {
        return _id;
    }

    @Override
    public String getData() {
        return _data;
    }

    @Override
    public long getSize() {
        return _size;
    }

    @Override
    public long getFolderSize() {
        return foldeSize;
    }

    @Override
    public String getDisplayName(Context context) {
        return _displayName;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public long getDateTaken() {
        return dateTaken;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isVideo() {
        return mIsVideo;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    @Override
    public void delete(final Activity context) {
        try {
            final File currentFile = new File(getData());
            context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.DATA + " = ?",
                    new String[]{currentFile.getAbsolutePath()});
            currentFile.delete();


        } catch (final Exception e) {
            e.printStackTrace();
            if (context == null) return;
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void setfolderSize(long size) {
        foldeSize=size;
    }

}
