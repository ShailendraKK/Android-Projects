package com.shailendra.photonotesgallery.media;


import android.content.Context;

import com.shailendra.photonotesgallery.api.MediaEntry;
import com.shailendra.photonotesgallery.utils.AlphanumComparator;

import java.util.Comparator;

public class MediaNameSorter implements Comparator<MediaEntry> {

    private final Context context;
    private final boolean desc;

    public MediaNameSorter(Context context, boolean desc) {
        this.context = context;
        this.desc = desc;
    }

    @Override
    public int compare(MediaEntry lhs, MediaEntry rhs) {
        String right = rhs.getDisplayName(context);
        String left = lhs.getDisplayName(context);
        if (right == null) {
            right = "";
        }
        if (left == null) {
            left = "";
        }
        if (desc) {
            return AlphanumComparator.compare(right, left);
        } else {
            return AlphanumComparator.compare(left, right);
        }
    }
}
