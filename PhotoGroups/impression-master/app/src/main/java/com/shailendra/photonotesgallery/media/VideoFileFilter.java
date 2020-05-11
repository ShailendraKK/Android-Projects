package com.shailendra.photonotesgallery.media;

import java.io.File;

/**
 * Created by shailendra on 28-06-2016.
 */
public class VideoFileFilter implements java.io.FileFilter {
    File file;
    private final String[] okFilextensions=new String[]{"3gp","mp4"};

    public VideoFileFilter(File newFile)
    {
        this.file=newFile;
    }

    @Override
    public boolean accept(File pathname) {

        for(String extensions:okFilextensions){
            if(file.getName().toLowerCase().endsWith(extensions))
            {
                return true;
            }
        }
        return false;
    }
}
