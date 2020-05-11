package com.shailendra.photonotesgallery.media;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.shailendra.photonotesgallery.MvpView;
import com.shailendra.photonotesgallery.api.MediaEntry;
import com.shailendra.photonotesgallery.widget.breadcrumbs.Crumb;

import java.util.List;

interface MediaView extends MvpView {

    void initializeRecyclerView(boolean gridMode, int size, MediaAdapter adapter);

    void updateGridModeOn(boolean gridMode);

    void updateGridColumns(int size);

    MediaAdapter getAdapter();

    Context getContextCompat();

    Bundle getArguments();

    void saveScrollPositionInto(Crumb crumb);

    void invalidateEmptyText();

    void setListShown(boolean listShown);

    void restoreScrollPositionFrom(Crumb crumb);

    void invalidateSubtitle(List<MediaEntry> allEntries);

    void onCreateSortMenuSelection(Menu menu, @MediaAdapter.SortMode int sortMode, boolean sortRememberDir);

    void scrollToTop();
}
