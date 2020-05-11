package com.shailendra.photonotesgallery.media;


import com.shailendra.photonotesgallery.api.MediaEntry;

import java.util.Comparator;

public class MediaTakenSorter implements Comparator<MediaEntry> {

    private final boolean asc;

    public MediaTakenSorter(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(MediaEntry lhs, MediaEntry rhs) {
        Long right;
        Long left;
        if (rhs != null) {
            right = rhs.getDateTaken();
        } else {
            right = 0L;
        }
        if (lhs != null) {
            left = lhs.getDateTaken();


        } else {
            left = 0L;
        }

        if (asc) {
            return left.compareTo(right);
        } else {

            return right.compareTo(left);


        }
    }
}
