package com.shailendra.photonotesgallery.api;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;

import com.shailendra.photonotesgallery.R;

import java.io.File;

public class LocalExplorerFolderEntry implements MediaEntry {

    private File mFile;

    private long foldersize;
    public LocalExplorerFolderEntry() {

    }

    public LocalExplorerFolderEntry(File file) {
        mFile = file;
    }

    @Override
    public long getId() {
        return mFile.hashCode();
    }

    @Override
    public String getData() {
        return mFile.getAbsolutePath();
    }

    @Override
    public long getSize() {
        return mFile.listFiles().length;
    }

    @Override
    public long getFolderSize() {
       return foldersize;
    }

    @Override
    public String getDisplayName(Context context) {
        if (mFile.equals(Environment.getExternalStorageDirectory().getAbsoluteFile())) {
            return context.getString(R.string.internal_storage);
        }
        return mFile.getName();
    }

    @Override
    public String getMimeType() {
        return "";
    }

    @Override
    public long getDateTaken() {
        return -1;
    }

    @Override
    public int getWidth() {
        return -1;
    }

    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public boolean isVideo() {
        return false;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    @Override
    public void delete(Activity context) {
        deleteFile(mFile);

        context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.DATA + " = ?",
                new String[]{mFile.getAbsolutePath()});
    }

    @Override
    public void setfolderSize(long size) {
        foldersize=size;
    }


    private void deleteFile(File parent) {
        for (File file : parent.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            } else {
                deleteFile(file);
            }
        }
    }
}
