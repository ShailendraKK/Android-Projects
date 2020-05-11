package com.shailendra.photonotesgallery.media;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shailendra.photonotesgallery.R;
import com.shailendra.photonotesgallery.api.MediaEntry;
import com.shailendra.photonotesgallery.utils.PrefUtils;
import com.shailendra.photonotesgallery.utils.Utils;
import com.shailendra.photonotesgallery.widget.ImpressionThumbnailImageView;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aidan Follestad (shailendra)
 */
public class MediaAdapter1 extends RecyclerView.Adapter<MediaAdapter1.ViewHolder> {

    public static final int SORT_NAME_ASC = 0;
    public static final int SORT_NAME_DESC = 1;
    public static final int SORT_TAKEN_DATE_ASC = 2;
    public static final int SORT_TAKEN_DATE_DESC = 3;
    public static final int SORT_SIZE_DESC=5;
    public static final int SORT_NOSORT = 4;
    public static final int FILTER_ALL = 0;
    public static final int FILTER_PHOTOS = 1;
    public static final int FILTER_VIDEOS = 2;
    public static final int VIEW_TYPE_GRID = 0;
    public static final int VIEW_TYPE_GRID_FOLDER = 1;
    public static final int VIEW_TYPE_LIST = 2;
    private static final int ACTIVATION_UPDATE = -42;
    private static final String TAG = "MediaAdapter";

    private final Context mContext;
    private final Callback mCallback;
    private final List<String> mCheckedPaths;
    private final boolean mSelectAlbumMode;
    private List<MediaEntry> mEntries;
    @SortMode
    private int mSortMode;
    private boolean mGridMode;
    private boolean mExplorerMode;
    private int mDefaultImageBackground;
    private int mEmptyImageBackground;

    public MediaAdapter1(Context context, @SortMode int sort, Callback callback, boolean selectAlbumMode) {
        mContext = context;
        mSortMode = sort;
        mGridMode = PrefUtils.isGridMode(context);
        mExplorerMode = PrefUtils.isExplorerMode(context);
        mCallback = callback;
        mCheckedPaths = new ArrayList<>();
        mSelectAlbumMode = selectAlbumMode;

        mDefaultImageBackground = Utils.resolveColor(context, R.attr.default_image_background);
        mEmptyImageBackground = Utils.resolveColor(context, R.attr.empty_image_background);

        mEntries = CurrentMediaEntriesSingleton.getInstance().getMediaEntriesCopy(mContext, mSortMode);

        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        if (!mGridMode) {
            return VIEW_TYPE_LIST;
        } else if (mEntries.get(position).isFolder() && mExplorerMode) {
            return VIEW_TYPE_GRID_FOLDER;
        } else {
            return VIEW_TYPE_GRID;
        }
    }

    public void setItemChecked(MediaEntry entry, boolean checked) {
        if (checked) {
            if (!mCheckedPaths.contains(entry.getData())) {
                mCheckedPaths.add(entry.getData());
            }
            for (int i = 0; i < mEntries.size(); i++) {
                if (mEntries.get(i).getData() != null &&
                        mEntries.get(i).getData().equals(entry.getData())) {
                    notifyItemChanged(i, ACTIVATION_UPDATE);
                    break;
                }
            }
        } else {
            if (mCheckedPaths.contains(entry.getData())) {
                mCheckedPaths.remove(entry.getData());
            }
            for (int i = 0; i < mEntries.size(); i++) {
                if (mEntries.get(i).getData().equals(entry.getData())) {
                    notifyItemChanged(i, ACTIVATION_UPDATE);
                    break;
                }
            }
        }
    }

    public void clearChecked() {
        for (int i = 0; i < mEntries.size(); i++) {
            for (String path : mCheckedPaths) {
                if (mEntries.get(i).getData().equals(path)) {
                    notifyItemChanged(i, ACTIVATION_UPDATE);
                    break;
                }
            }
        }
        mCheckedPaths.clear();
    }

    @Override
    public long getItemId(int position) {
        return mEntries.get(position).getId();
    }

    public void clear() {
        mEntries.clear();
        notifyDataSetChanged();
    }

    public void updateEntriesFromSort() {
        mEntries = CurrentMediaEntriesSingleton.getInstance().getMediaEntriesCopy(mContext, mSortMode);
        notifyDataSetChanged();
    }

    public void updateGridModeOn() {
        mGridMode = PrefUtils.isGridMode(mContext);
        notifyDataSetChanged();
    }

    public void updateGridColumns() {
        notifyDataSetChanged();
    }

    public void updateSortMode(@SortMode int mode) {
        mSortMode = mode;
        updateEntriesFromSort();
    }

    public void updateExplorerMode() {
        mExplorerMode = PrefUtils.isExplorerMode(mContext);
        //Don't notifyDataSetChanged() because setPath() will be called and then reload().
    }

    public void updateTheme() {
        mDefaultImageBackground = Utils.resolveColor(mContext, R.attr.default_image_background);
        mEmptyImageBackground = Utils.resolveColor(mContext, R.attr.empty_image_background);

        //TODO: Use selective updating since by using stable IDs this doesn't actually do anything
        notifyDataSetChanged();
    }

    /**
     * Use with caution. Don't modify.
     */
    public List<MediaEntry> getEntries() {
        return mEntries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes;
                layoutRes = R.layout.list_item_media_size;
 View v = LayoutInflater.from(mContext).inflate(layoutRes, parent, false);
        return new ViewHolder(v);
    }

    /**
     * These payloads are awesome. More people should use these. USE THESE PEOPLE
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            if (payloads.contains(ACTIVATION_UPDATE)) {
                holder.view.setActivated(mCheckedPaths.contains(mEntries.get(position).getData()));
            }
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MediaEntry entry = mEntries.get(position);

        if (!mSelectAlbumMode || (entry.isFolder()/*|| entry.isAlbum()*/)) {
            holder.view.setActivated(mCheckedPaths.contains(entry.getData()));
            if (!mSelectAlbumMode) {
                holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int index = holder.getAdapterPosition();
                        if (index != RecyclerView.NO_POSITION) {
                            mCallback.onItemClick(index, v, mEntries.get(index), true);
                        }
                        return true;
                    }
                });
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = holder.getAdapterPosition();
                    if (index != RecyclerView.NO_POSITION) {
                        mCallback.onItemClick(index, v, mEntries.get(index), false);
                    }
                }
            });
            holder.image.setBackgroundColor(mDefaultImageBackground);
            ViewCompat.setTransitionName(holder.image, "view_" + position);
        } else {
            holder.view.setBackground(null);
        }

        if (entry.isFolder() && !mExplorerMode) {
            holder.titleFrame.setVisibility(View.VISIBLE);
            holder.title.setText(entry.getDisplayName(mContext));
            long folder_size=entry.getFolderSize();
            File file=new File(entry.getData());
              long length=0;
            for(File file1:file.listFiles())
            {
                if(new ImageFileFilter(file1).accept(file1.getAbsoluteFile()))
                {
                    length+=file1.length();
                }
                else if(new VideoFileFilter(file1).accept(file1.getAbsoluteFile()))
                {
                    length+=file1.length();
                }
                           }
            entry.setfolderSize(length);
            holder.size.setText("Size : "+Utils.readableFileSize(length));
         /*   if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

                    File fileDirectory = new File(Environment.getExternalStorageDirectory().toString());
                    long totalspace = fileDirectory.getTotalSpace();
                long percent = (length/totalspace)*100;
                    holder.percent.setText(percent + " % of total storage");
                }
                */
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
         long bytesAvailable=0;
            if(Build.VERSION.SDK_INT<18)
            {
                bytesAvailable = (long) stat.getBlockSize() * (long) stat.getBlockCount();

            }
            else {
                 bytesAvailable = (long) stat.getBlockSizeLong() * (long) stat.getBlockCountLong();
            }
           double percent = ((double)length/(double)bytesAvailable)*100;

            holder.percent.setText(String.format("%.2f",percent)+ " % of "+Utils.readableFileSize(bytesAvailable));
                // Fi
            // le file = new File(entry.getData());
           /* for (int i = 0; i < ; i++) {
                if (mMediaEntries.get(i).getData().equals(p.getData())) {
                    if (!forceCheckOn) {
                        mMediaEntries.remove(i);
                    }
                    found = true;
                    break;
                }
            }*/
          //  holder.size.setText(entry.getData());

            if (entry.getData() == null) {
                holder.image.setBackgroundColor(mEmptyImageBackground);
                if (holder.subTitle != null) {
                    holder.subTitle.setText("0");

                }
            }
            //TODO
            /*else if (entry.size() == 1) {
                if (holder.subTitle != null)
                    holder.subTitle.setText("1");
            } else if (holder.subTitle != null) {
                holder.subTitle.setText(String.valueOf(entry.size()));
            }*/
            holder.image.load(entry, holder.imageProgress);
        } else if (entry.isFolder() && mExplorerMode) {
            holder.image.setBackgroundColor(mEmptyImageBackground);
            holder.image.setBackgroundColor(mEmptyImageBackground);
            holder.titleFrame.setVisibility(View.VISIBLE);
            holder.title.setText(entry.getDisplayName(mContext));

            if (holder.imageProgress != null) {
                holder.imageProgress.setVisibility(View.GONE);
            }
            holder.image.setImageResource(R.drawable.ic_folder);

            if (!mGridMode) {
                if (holder.subTitle != null) {
                    holder.subTitle.setVisibility(View.VISIBLE);
                    holder.subTitle.setText(R.string.folder);
                }
            } else if (holder.subTitle != null) {
                holder.subTitle.setVisibility(View.GONE);
            }
        } else {
            if (mGridMode) {
                holder.titleFrame.setVisibility(View.GONE);
            } else {
                holder.titleFrame.setVisibility(View.VISIBLE);
                holder.title.setText(entry.getDisplayName(mContext));
                holder.size.setText(Utils.readableFileSize(entry.getSize()));
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                long bytesAvailable=0;
                if(Build.VERSION.SDK_INT<18)
                {
                    bytesAvailable = (long) stat.getBlockSize() * (long) stat.getBlockCount();

                }
                else {
                    bytesAvailable = (long) stat.getBlockSizeLong() * (long) stat.getBlockCountLong();
                }
                double percent = ((double)entry.getSize()/(double)bytesAvailable)*100;
                holder.percent.setText(String.format("%.2f", percent) + " % of  "+Utils.readableFileSize(bytesAvailable));
                if (holder.subTitle != null) {
                    holder.subTitle.setVisibility(View.VISIBLE);
                    holder.subTitle.setText(entry.getMimeType());
                }
            }
            holder.image.load(entry, holder.imageProgress);
        }
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    @SuppressLint("UniqueConstants")
    @IntDef({SORT_NAME_ASC, SORT_NAME_DESC, SORT_TAKEN_DATE_ASC, SORT_TAKEN_DATE_DESC, SORT_NOSORT,SORT_SIZE_DESC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortMode {
    }

    @IntDef({FILTER_ALL, FILTER_PHOTOS, FILTER_VIDEOS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileFilterMode {
    }


    public interface Callback {
        void onItemClick(int index, View view, MediaEntry pic, boolean longClick);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final ImpressionThumbnailImageView image;
        public final View imageProgress;
        public final View titleFrame;
        public final TextView title;
        public final TextView subTitle;
        public final TextView size;
        public final TextView percent;
        public ViewHolder(View v) {
            super(v);
            view = v;
            image = (ImpressionThumbnailImageView) v.findViewById(R.id.image);
            imageProgress = v.findViewById(R.id.imageProgress);
            titleFrame = v.findViewById(R.id.titleFrame);
            title = (TextView) v.findViewById(R.id.title);
            subTitle = (TextView) v.findViewById(R.id.subTitle);
            size=(TextView)v.findViewById(R.id.size);
            percent=(TextView)v.findViewById(R.id.percent);
        }
    }
}