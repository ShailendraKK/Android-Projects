package com.shailendra.photonotesgallery.media;


import com.shailendra.photonotesgallery.api.MediaEntry;

import java.util.Comparator;

public class MediaSizeSorter implements Comparator<MediaEntry> {


    @Override
    public int compare(MediaEntry lhs, MediaEntry rhs) {
        Long right;
        Long left;
        if (rhs != null) {
            if(rhs.isFolder())
              right=rhs.getFolderSize();
            else
            right = rhs.getSize();
        } else {
            right = 0L;
        }
        if (lhs != null) {
            if(lhs.isFolder())
                left=lhs.getFolderSize();
            else
            left = lhs.getSize();


        } else {
            left = 0L;
        }

            return right.compareTo(left);


    }
}
