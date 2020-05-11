package com.shailendra.photonotesgallery.media;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shailendra.photonotesgallery.BuildConfig;
import com.shailendra.photonotesgallery.R;
import com.shailendra.photonotesgallery.api.LocalMediaFolderEntry;
import com.shailendra.photonotesgallery.api.MediaEntry;
import com.shailendra.photonotesgallery.base.ThemedActivity;
import com.shailendra.photonotesgallery.settings.SettingsActivity;
import com.shailendra.photonotesgallery.utils.PrefUtils;
import com.shailendra.photonotesgallery.widget.breadcrumbs.Crumb;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MediaFragment1 extends Fragment implements MediaView1 {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MediaAdapter1 mAdapter;
    private MediaPresenter1 mPresenter;
    private View mEmptyView;
    public static final int REQUEST_SETTINGS = 9000;

    RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    final void jumpToTop(boolean animateChange) {
        if (animateChange) {
            //stopAnimation();
            mRecyclerView.smoothScrollToPosition(0);
        } else {
            mRecyclerView.scrollToPosition(0);
        }
    }

    public final void setListShown(boolean shown) {
        View v = getView();
        if (v == null || getActivity() == null) {
            return;
        }
        if (shown) {
            mEmptyView.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            v.findViewById(R.id.empty).setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.empty)).setText(mPresenter.getEmptyText());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mEmptyView = view.findViewById(R.id.empty);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRefresh();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(((ThemedActivity) getActivity()).accentColor());

        mPresenter.onViewCreated(savedInstanceState);
        mPresenter.setGridModeOn(false);
        mPresenter.changeSortMode(MediaAdapter1.SORT_SIZE_DESC, mPresenter.mSortRememberDir ? mPresenter.mPath : null);


    }

    @Override
    public void initializeRecyclerView(boolean gridMode, int size, MediaAdapter1 adapter) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContextCompat(), size));

        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        });

        if (gridMode) {
            mPresenter.setGridModeOn(true);
        }
    }

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    public void updateGridModeOn(boolean gridMode) {
        int gridSpacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        if (gridMode) {
            mRecyclerView.setPadding(gridSpacing, 0, 0, gridSpacing);
        } else {
            mRecyclerView.setPadding(0, 0, 0, 0);
        }
    }

    @Override
    public void updateGridColumns(int size) {
        final GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(size);
    }

    @Override
    public MediaAdapter1 getAdapter() {
        return mAdapter;
    }

    @Override
    public Context getContextCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext();
        } else {
            return getActivity();
        }
    }

    @Override
    public void saveScrollPositionInto(Crumb crumb) {
        if (crumb == null) {
            return;
        }
        crumb.setScrollPosition(((GridLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        final View firstChild = mRecyclerView.getChildAt(0);
        if (firstChild != null) {
            crumb.setScrollOffset((int) firstChild.getY());
        }
    }

    @Override
    public void restoreScrollPositionFrom(Crumb crumb) {
        if (crumb == null) {
            return;
        }
        final int scrollY = crumb.getScrollPosition();
        if (scrollY > -1 && scrollY < getAdapter().getItemCount()) {
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(scrollY, crumb.getScrollOffset());
        }
    }

    private void setFilterMode(@MediaAdapter1.FileFilterMode int mode) {
        PrefUtils.setFilterMode(getActivity(), mode);
        getPresenter().reload();
        getActivity().invalidateOptionsMenu();
    }


    public void invalidateEmptyText() {
        @MediaAdapter1.FileFilterMode int mode = PrefUtils.getFilterMode(getActivity());
        View v = getView();
        if (v != null) {
            TextView empty = (TextView) v.findViewById(R.id.empty);
            if (empty != null) {
                switch (mode) {
                    default:
                        empty.setText(R.string.no_photosorvideos);
                        break;
                    case MediaAdapter1.FILTER_PHOTOS:
                        empty.setText(R.string.no_photos);
                        break;
                    case MediaAdapter1.FILTER_VIDEOS:
                        empty.setText(R.string.no_videos);
                        break;
                }
            }
        }
    }


    public void invalidateSubtitle(List<MediaEntry> entries) {
        AppCompatActivity act = (AppCompatActivity) getActivity();
        if (act != null) {
            final boolean toolbarStats = PreferenceManager.getDefaultSharedPreferences(act)
                    .getBoolean("toolbar_album_stats", true);
            if (toolbarStats) {
                if (entries == null || entries.size() == 0) {
                    act.getSupportActionBar().setSubtitle(getString(R.string.empty));
                    return;
                }

                int folderCount = 0;
                int albumCount = 0;
                int videoCount = 0;
                int photoCount = 0;
                for (MediaEntry e : entries) {
                    if (e.isFolder()) {
                        folderCount++;
                    }
                    //else if (e.isAlbum()) albumCount++;
                    else if (e.isVideo()) {
                        videoCount++;
                    } else {
                        photoCount++;
                    }
                }
                final StringBuilder sb = new StringBuilder();
                if (albumCount > 1) {
                    sb.append(getString(R.string.x_albums, albumCount));
                } else if (albumCount == 1) {
                    sb.append(getString(R.string.one_album));
                }
                if (folderCount > 1) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(getString(R.string.x_folders, folderCount));
                } else if (folderCount == 1) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(getString(R.string.one_folder));
                }
                if (photoCount > 1) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(getString(R.string.x_photos, photoCount));
                } else if (photoCount == 1) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(getString(R.string.one_photo));
                }
                if (videoCount > 1) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(getString(R.string.x_videos, videoCount));
                } else if (videoCount == 1) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(getString(R.string.one_video));
                }
                act.getSupportActionBar().setSubtitle(sb.toString());
            } else {
                act.getSupportActionBar().setSubtitle(null);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter.detachView();
    }

    public MediaPresenter1 getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mPresenter = createPresenter();

        mPresenter.attachView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == MediaCab.COPY_REQUEST_CODE || requestCode == MediaCab.MOVE_REQUEST_CODE)) {
            ((SizeActivity) getActivity()).getMediaCab1().finishCopyMove(new File(data.getData().getPath()), requestCode);
        }
    }

    private void setStatus(String status) {
        ((SizeActivity) getActivity()).setStatus(status);
    }

    public void onCreateSortMenuSelection(Menu menu, @MediaAdapter1.SortMode int sortMode, boolean sortRememberDir) {
        switch (sortMode) {
            default:
                menu.findItem(R.id.sortNameAsc).setChecked(true);
                break;
            case MediaAdapter1.SORT_NAME_DESC:
                menu.findItem(R.id.sortNameDesc).setChecked(true);
                break;
            case MediaAdapter1.SORT_TAKEN_DATE_ASC:
                menu.findItem(R.id.sortModifiedAsc).setChecked(true);
                break;
            case MediaAdapter1.SORT_TAKEN_DATE_DESC:
                menu.findItem(R.id.sortModifiedDesc).setChecked(true);
                break;
        }
        menu.findItem(R.id.sortCurrentDir).setChecked(sortRememberDir);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    public void showAboutDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.about_dialog_title, BuildConfig.VERSION_NAME))
                .positiveText(R.string.dismiss)
                .content(Html.fromHtml(getString(R.string.about_body)))
                .iconRes(R.mipmap.ic_launcher)
                .linkColor(ContextCompat.getColor(getActivity(), R.color.material_pink_500))
                .show();
    }

    public void openSettings() {
        startActivityForResult(new Intent(getActivity(), SettingsActivity.class), REQUEST_SETTINGS);

    }
    protected MediaPresenter1 createPresenter() {
        return new MediaPresenter1();
    }

}