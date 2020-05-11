package com.shailendra.photonotesgallery.media;

import java.io.File;

/**
 * Created by shailendra on 28-06-2016.
 */
public class ImageFileFilter implements java.io.FileFilter {
    File file;
    private final String[] okFilextensions=new String[]{"jpg","png","gif","jpeg"};

   public ImageFileFilter(File newFile)
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
