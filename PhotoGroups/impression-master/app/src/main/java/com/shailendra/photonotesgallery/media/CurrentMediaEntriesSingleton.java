package com.shailendra.photonotesgallery.media;

import android.content.Context;

import com.shailendra.photonotesgallery.api.MediaEntry;
import com.shailendra.photonotesgallery.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nativeads.AppInstallAdFetcher;
import nativeads.AppInstallAdPlacement;
import nativeads.ContentAdFetcher;
import nativeads.ContentAdPlacement;
import nativeads.ExtendedCustomTemplateAdViewHolder;
import nativeads.MultiFormatAdFetcher;
import nativeads.MultiFormatAdPlacement;
import nativeads.SimpleCustomTemplateAdFetcher;
import nativeads.SimpleCustomTemplateAdPlacement;
import nativeads.SimpleCustomTemplateAdViewHolder;

public class CurrentMediaEntriesSingleton {

    public static final String LOG_TAG = "NativeAdsExample";

    /**
     * A DFP ad unit configured to serve app install and content ads
     */
    private static final String DFP_NATIVE_AD_UNIT_ID = "/6499/example/native";

    /**
     * A DFP ad unit configured to serve a simple custom template ad
     */
    private static final String DFP_SIMPLE_CUSTOM_TEMPLATE_AD_UNIT_ID =
            "/6499/example/custom_template/simple";

    /**
     * A DFP ad unit configured to both of the above in rotation
     */
    private static final String DFP_MULTIFORMAT_AD_UNIT_ID =
            "/6499/example/native-multiformat";

    /**
     * Index where a DFP content ad should be placed.
     */
    private static final int DFP_CONTENT_AD_INDEX = 6;

    /**
     * Index where a DFP app install ad should be placed.
     */
    private static final int DFP_APP_INSTALL_AD_INDEX = 12;

    /**
     * Index where a DFP custom template ad should be placed
     */
    private static final int DFP_CUSTOM_TEMPLATE_AD_INDEX = 18;

    /**
     * Index where a DFP custom template ad should be placed
     */
    private static final int DFP_MULTIFORMAT_AD_INDEX = 24;

    private static CurrentMediaEntriesSingleton currentMediaEntries;

    private List<MediaEntry> mMediaEntries;

    private CurrentMediaEntriesSingleton() {
        mMediaEntries = new ArrayList<>();
    }

    public static CurrentMediaEntriesSingleton getInstance() {
        if (currentMediaEntries == null) {
            currentMediaEntries = new CurrentMediaEntriesSingleton();
        }
        return currentMediaEntries;
    }

    public static boolean instanceExists() {
        return currentMediaEntries != null;
    }

    public void set(List<MediaEntry> newItems) {
        mMediaEntries.clear();
        mMediaEntries.addAll(newItems);
    }

    public void remove(MediaEntry entry) {
        mMediaEntries.remove(entry);
    }

    public void removeAll(List<MediaEntry> ids) {
        mMediaEntries.removeAll(ids);
    }

    public List<MediaEntry> getMediaEntriesCopy(Context context, @MediaAdapter.SortMode int sortMode) {
        if (sortMode != MediaAdapter.SORT_NOSORT && mMediaEntries.size() > 0) {
            Collections.sort(mMediaEntries, PrefUtils.getSortComparator(context, sortMode));
        }
        ArrayList<MediaEntry> ma;
        ma = new ArrayList<>(mMediaEntries);
     //   ma.add(DFP_CONTENT_AD_INDEX,
        //        (MediaEntry) new ContentAdPlacement(new ContentAdFetcher(DFP_NATIVE_AD_UNIT_ID)));
     //   ma.add(DFP_APP_INSTALL_AD_INDEX,
       //         new AppInstallAdPlacement(new AppInstallAdFetcher(DFP_NATIVE_AD_UNIT_ID)));
       // ma.add(DFP_CUSTOM_TEMPLATE_AD_INDEX,
         //       new SimpleCustomTemplateAdPlacement(new SimpleCustomTemplateAdFetcher(
           //             DFP_SIMPLE_CUSTOM_TEMPLATE_AD_UNIT_ID,
     //                   SimpleCustomTemplateAdViewHolder.DFP_TEMPLATE_ID)));
       // ma.add(DFP_MULTIFORMAT_AD_INDEX,
         //       new MultiFormatAdPlacement(new MultiFormatAdFetcher(
           //             DFP_MULTIFORMAT_AD_UNIT_ID,
               //         SimpleCustomTemplateAdViewHolder.DFP_TEMPLATE_ID,
             //           ExtendedCustomTemplateAdViewHolder.DFP_TEMPLATE_ID)));

        return new ArrayList<>(ma);
    }

}
