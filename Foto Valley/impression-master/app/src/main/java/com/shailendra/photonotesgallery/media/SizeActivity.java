package com.shailendra.photonotesgallery.media;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SharedElementCallback;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shailendra.photonotesgallery.App;
import com.shailendra.photonotesgallery.BuildConfig;
import com.shailendra.photonotesgallery.R;
import com.shailendra.photonotesgallery.api.LocalMediaFolderEntry;
import com.shailendra.photonotesgallery.base.ThemedActivity;

import com.shailendra.photonotesgallery.providers.IncludedFolderProvider;
import com.shailendra.photonotesgallery.providers.SortMemoryProvider;
import com.shailendra.photonotesgallery.settings.SettingsActivity;
import com.shailendra.photonotesgallery.utils.PrefUtils;
import com.shailendra.photonotesgallery.utils.Utils;
import com.shailendra.photonotesgallery.widget.breadcrumbs.BreadCrumbLayout;
import com.shailendra.photonotesgallery.widget.breadcrumbs.Crumb;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import com.squareup.haha.perflib.Main;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Aidan Follestad (shailendra)
 */
public class SizeActivity extends ThemedActivity
        implements FolderChooserDialog.FolderCallback,fragment_MediaScan.OnFragmentInteractionListener {
    private AdView mAdView;
    int permission;

    private HeadSetIntentReceiver receiver;
    private CharSequence mDrawerTitle;

    public static final String EXTRA_CURRENT_ITEM_POSITION = "com.shailendra.impression.extra_current_item_position";
    public static final String EXTRA_OLD_ITEM_POSITION = "com.shailendra.impression.extra_old_item_position";
    //public static final String EXTRA_REMOVED_ITEMS = "com.shailendra.impression.removed_items";

    public static final String ACTION_SELECT_ALBUM = BuildConfig.APPLICATION_ID + ".SELECT_FOLDER";

    public static final String NAV_DRAWER_FRAGMENT = "NAV_DRAWER";

    public static final int REQUEST_SETTINGS = 9000;



    //   private DrawerLayout mDrawerLayout;
    //  private AnimatedDrawerToggle mAnimatedDrawerToggle;

    private boolean mPickMode;

    private SelectAlbumMode mSelectAlbumMode = SelectAlbumMode.NONE;
    private MediaCab1 mMediaCab1;
    private Toolbar mToolbar;

    private BreadCrumbLayout mBreadCrumbLayout;
    private CharSequence mTitle;
    private View v;
    private Bundle mTmpState;
    private boolean mIsReenteringFromViewer;
    private FloatingActionButton btn;
    //   private FloatingActionButton new_notes_btn;
    //   private FloatingActionButton resume_notes_btn;
    private static void logSharedElementTransition(String message, boolean isReentering) {
        if (BuildConfig.DEBUG) {
            //    Log.i(TAG, String.format("%s: %s", isReentering ? "REENTERING" : "EXITING", message));
        }
    }



    public boolean isPickMode() {
        return mPickMode;
    }

    public void setIsReentering(boolean isReentering) {
        mIsReenteringFromViewer = isReentering;
    }

    public Bundle getTmpState() {
        return mTmpState;
    }

    public void setTmpState(Bundle tmpState) {
        mTmpState = tmpState;
    }

    public MediaCab1 getMediaCab1() {
        return mMediaCab1;
    }

    public void setMediaCab1(MediaCab1 mediaCab) {
        mMediaCab1 = mediaCab;
    }

    public BreadCrumbLayout getBreadCrumbLayout() {
        return mBreadCrumbLayout;
    }

  /*  public void invalidateMenuArrow(final String albumPath) {
        mBreadCrumbLayout.post(new Runnable() {
            @Override
            public void run() {
                if (albumPath == null || albumPath.equals(mBreadCrumbLayout.getTopPath()) || PrefUtils.isExplorerMode(MainActivity.this)) {
                    animateDrawerArrow(true);
                } else {
                    animateDrawerArrow(false);
                }
            }
        });
    }
*/
    /*  public void animateDrawerArrow(boolean close) {
        float cu rrentOffset;

        ValueAnimator anim;
        if (close) {
            anim = ValueAnimator.ofFloat(currentOffset, 0f);
        } else {
            anim = ValueAnimator.ofFloat(currentOffset, 1f);
        }
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
           //     mAnimatedDrawerToggle.setOffset(slideOffset);
            }
        });
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setDuration(300);
        anim.start();
    }*/

    public void setStatus(String status) {
        TextView view = (TextView) findViewById(R.id.status);
        if (status == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(status);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupSharedElementCallback() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        final SharedElementCallback mCallback = new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                logSharedElementTransition("onMapSharedElements(List<String>, Map<String, View>)", mIsReenteringFromViewer);


                int oldPosition = mTmpState != null ? mTmpState.getInt(EXTRA_OLD_ITEM_POSITION) : 0;
                int currentPosition = mTmpState != null ? mTmpState.getInt(EXTRA_CURRENT_ITEM_POSITION) : 0;
                mTmpState = null;

                boolean shouldAddSharedImageView = !mIsReenteringFromViewer || currentPosition != oldPosition;

                final RecyclerView recyclerView = findMediaFragment1().getRecyclerView();
                if (shouldAddSharedImageView && recyclerView != null) {
                    View newSharedView = ((MediaAdapter.ViewHolder) recyclerView.findViewHolderForLayoutPosition(currentPosition)).image;
                    if (newSharedView != null) {
                        newSharedView = newSharedView.findViewById(R.id.image);
                        final String transName = newSharedView.getTransitionName();
                        names.clear();
                        names.add(transName);
                        sharedElements.clear();
                        sharedElements.put(transName, newSharedView);
                    }
                }

                if (!mIsReenteringFromViewer) {
                    getWindow().setStatusBarColor(primaryColorDark());
                }

                View decor = getWindow().getDecorView();
                View navigationBar = decor.findViewById(android.R.id.navigationBarBackground);
                View statusBar = decor.findViewById(android.R.id.statusBarBackground);

                if (navigationBar != null && !sharedElements.containsKey(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)) {
                    if (!names.contains(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)) {
                        names.add(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                    }
                    sharedElements.put(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, navigationBar);
                }

                View toolbarFrame = findViewById(R.id.toolbar_frame);
                if (toolbarFrame != null && !sharedElements.containsKey(toolbarFrame.getTransitionName())) {
                    if (!names.contains(toolbarFrame.getTransitionName())) {
                        names.add(toolbarFrame.getTransitionName());
                    }
                    sharedElements.put(toolbarFrame.getTransitionName(), toolbarFrame);
                }

                if (statusBar != null && !sharedElements.containsKey(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)) {
                    if (!names.contains(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)) {
                        names.add(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
                    }
                    sharedElements.put(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, statusBar);
                }

                logSharedElementTransition("=== names: " + names.toString(), mIsReenteringFromViewer);
                logSharedElementTransition("=== sharedElements: " + Utils.setToString(sharedElements.keySet()), mIsReenteringFromViewer);
            }

            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
                                             List<View> sharedElementSnapshots) {
                logSharedElementTransition("onSharedElementStart(List<String>, List<View>, List<View>)", mIsReenteringFromViewer);
                logSharedElementsInfo(sharedElementNames, sharedElements);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
                                           List<View> sharedElementSnapshots) {
                logSharedElementTransition("onSharedElementEnd(List<String>, List<View>, List<View>)", mIsReenteringFromViewer);
                logSharedElementsInfo(sharedElementNames, sharedElements);

                if (mIsReenteringFromViewer) {
                    View statusBar = getWindow().getDecorView().findViewById(android.R.id.statusBarBackground);
                    if (statusBar != null) {
                        statusBar.post(new Runnable() {
                            @Override
                            public void run() {
                                //For DrawerLayout transparency
                                getWindow().setStatusBarColor(Color.TRANSPARENT);
                            }
                        });
                    }
                }
            }

            private void logSharedElementsInfo(List<String> names, List<View> sharedElements) {
                logSharedElementTransition("=== names: " + names.toString(), mIsReenteringFromViewer);
                logSharedElementTransition("=== infos:", mIsReenteringFromViewer);
                for (View view : sharedElements) {
                    int[] loc = new int[2];
                    //noinspection ResourceType
                    view.getLocationInWindow(loc);
                    logSharedElementTransition("====== " + view.getTransitionName() + ": " + "(" + loc[0] + ", " + loc[1] + ")", mIsReenteringFromViewer);
                }
            }
        };
        setExitSharedElementCallback(mCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permission == -1) {
            new MaterialDialog.Builder(this)
                    .title(R.string.permission_needed)
                    .content(R.string.permission_needed_desc)
                    .cancelable(false)
                    .positiveText(android.R.string.ok)
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    }).show();
        } else {
            switchAlbum(null);
            //reloadNavDrawerAlbums();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Toast.makeText(this,"This apps need to access external storage for its critical functionality. Please appove it",Toast.LENGTH_LONG).show();

                }
                else
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permission);
                }
            }
        }

        setupSharedElementCallback();
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                //       .addTestDevice("0A6B37201CD257B683795D1FC30AB57C")

                //   .addTestDevice("5A569188355EF91D42C1982244A07859")
                .build();
        mAdView.loadAd(adRequest);
        // Toast.makeText(this,"Add loaded",Toast.LENGTH_LONG).show();
        btn= (FloatingActionButton) findViewById(R.id.fab);
        btn.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{primaryColor()}));
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent i=new Intent(SizeActivity.this,TagActivity.class);
                startActivity(i);

            }
        });
       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitle.equals("Overview")) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                                Toast.makeText(MainActivity.this, "This apps need to access camera for its critical functionality. Please appove it", Toast.LENGTH_LONG).show();

                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, permission);
                            }
                        }

                    }
                    String name = preferences.getString("name", null);
                    if (name == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                        builder.setTitle("Select your choice");
                        builder.setMessage("Pleas Enter the name with which you want to group the photo");
                        View name_view = getLayoutInflater().inflate(R.layout.name1, null);

                        final EditText notes_name = (EditText) name_view.findViewById(R.id.notes_name);


                        builder.setView(name_view);
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String name = notes_name.getText().toString();
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                                SharedPreferences.Editor edit = preferences.edit();
                                edit.putString("name", name);
                                edit.commit();
                                Intent i = new Intent(MainActivity.this, orient.class);
                                i.putExtra("name", name);
                                startActivity(i);

                            }
                        });
                        builder.setNegativeButton("Cancel", null);
                        builder.show();

                    } else {
                        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                        final String name1 = preferences.getString("name", null);
                        String msg = "Resume your previously created group " + name1 + " or create a new one";
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                        builder.setTitle("Select your choice");
                        builder.setMessage(msg);
                        //    builder.setMessage("Press yes for group photo");
                        builder.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(MainActivity.this, orient.class);
                                i.putExtra("name", name1);
                                startActivity(i);

                            }
                        });

                        builder.setNegativeButton("New", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                                builder.setTitle("Select your choice");
                                builder.setMessage("Pleas Enter the name with which you want to group the photo");
                                View name_view = getLayoutInflater().inflate(R.layout.name1, null);

                                final EditText notes_name = (EditText) name_view.findViewById(R.id.notes_name);


                                builder.setView(name_view);
                                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String name = notes_name.getText().toString();
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                                        SharedPreferences.Editor edit = preferences.edit();
                                        edit.putString("name", name);
                                        edit.commit();
                                        Intent i = new Intent(MainActivity.this, orient.class);
                                        i.putExtra("name", name);
                                        startActivity(i);

                                    }
                                });
                                builder.setNegativeButton("Cancel", null);
                                builder.show();

                            }
                        });
                        //   builder.setView(v);
                        builder.setNeutralButton("Cancel", null);
                        builder.show();






                    }

                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                                Toast.makeText(MainActivity.this, "This apps need to access camera for its critical functionality. Please appove it", Toast.LENGTH_LONG).show();

                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, permission);
                            }
                        }

                    }
                    Intent i = new Intent(MainActivity.this, orient.class);
                    i.putExtra("name", mTitle);
                    startActivity(i);
                }
            }

        });*/
        PackageManager pm = getPackageManager();
        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD &&
                        Camera.getNumberOfCameras() > 0);
        if (!hasACamera) {
            btn.setEnabled(false);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setSubtitleTextAppearance(this, R.style.ToolbarSubtitleStyle);
        setSupportActionBar(mToolbar);
        // findViewById(R.id.toolbar_frame).setBackgroundColor(primaryColor());

        processIntent(getIntent());

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if (!isSelectAlbumMode()) {
            // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            //     mAnimatedDrawerToggle = new AnimatedDrawerToggle();
            //   mAnimatedDrawerToggle.syncState();
            //  mAnimatedDrawerToggle.setOffset(0f);

           /* mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    Fragment nav = getFragmentManager().findFragmentByTag(NAV_DRAWER_FRAGMENT);
                    if (nav != null) {
                        ((NavDrawerFragment) nav).notifyClosed();
                    }
                }
            });*/
            // mDrawerLayout.setStatusBarBackgroundColor(primaryColorDark());

            //FrameLayout navDrawerFrame = (FrameLayout) findViewById(R.id.nav_drawer_frame);
            /*navDrawerFrame.setLayoutParams(new DrawerLayout.LayoutParams(Utils.getNavDrawerWidth(this),
                    DrawerLayout.LayoutParams.MATCH_PARENT, Gravity.START));
*/
            if (getIntent().getAction() != null &&
                    (getIntent().getAction().equals(Intent.ACTION_GET_CONTENT) ||
                            getIntent().getAction().equals(Intent.ACTION_PICK))) {
                mTitle = getTitle();
                setTitle(R.string.pick_something);
                mPickMode = true;
            }
        } else {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_discard);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // The drawer layout would handle this if album selection mode wasn't active
                getWindow().setStatusBarColor(primaryColorDark());
            }
        }

        mBreadCrumbLayout = (BreadCrumbLayout) findViewById(R.id.breadCrumbs);
        mBreadCrumbLayout.setCallback(new BreadCrumbLayout.SelectionCallback() {
            @Override
            public void onCrumbSelection(Crumb crumb, int index) {
                if (index == -1) {
                    onBackPressed();
                } else {
                    String activeFile = null;
                    if (mBreadCrumbLayout.getActiveIndex() > -1) {
                        activeFile = mBreadCrumbLayout.getCrumb(mBreadCrumbLayout.getActiveIndex()).getPath();
                    }
                    if (crumb.getPath() != null && activeFile != null &&
                            crumb.getPath().equals(activeFile)) {
                        Fragment frag = getFragmentManager().findFragmentById(R.id.content_frame);
                        ((MediaFragment1) frag).jumpToTop(true);
                    } else {
                        switchAlbum(crumb, crumb.getPath() == null, true);
                    }
                }
            }
        });

        if (savedInstanceState == null) {
            // Show initial page (overview)
            switchAlbum(null);
        } else {
            if (!isSelectAlbumMode()) {
                if (mTitle != null) {
                    getSupportActionBar().setTitle(mTitle);
                }
                mMediaCab1 = MediaCab1.restoreState(savedInstanceState, this);
            }
        }

        SortMemoryProvider.cleanup(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {


        super.onResume();
        invalidateExplorerMode();
    }

    public void invalidateExplorerMode() {
        final boolean explorerMode = PrefUtils.isExplorerMode(this);
        mBreadCrumbLayout.setVisibility(explorerMode ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMediaCab1 != null) {
            mMediaCab1.saveState(outState);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(final Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_SELECT_ALBUM)) {
            switch (intent.getIntExtra("mode", -1)) {
                default:
                    setSelectAlbumMode(SelectAlbumMode.CHOOSE);
                    break;
               /* case R.id.copyTo:
                    setSelectAlbumMode(SelectAlbumMode.COPY);
                    break;
                case R.id.moveTo:
                    setSelectAlbumMode(SelectAlbumMode.MOVE);
                    break;*/


            }
        }
    }

    public boolean isSelectAlbumMode() {
        return mSelectAlbumMode != SelectAlbumMode.NONE;
    }

    private void setSelectAlbumMode(SelectAlbumMode mode) {
        mSelectAlbumMode = mode;
        switch (mSelectAlbumMode) {
            default:
                setTitle(R.string.choose_album);
                break;
            case COPY:
                setTitle(R.string.copy_to);
                break;
            case MOVE:
                setTitle(R.string.move_to);
                break;
        }
        invalidateOptionsMenu();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (!mPickMode && mSelectAlbumMode == SelectAlbumMode.NONE && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        mIsReenteringFromViewer = true;
        mTmpState = new Bundle(data.getExtras());
        final int oldPosition = mTmpState.getInt(EXTRA_OLD_ITEM_POSITION);
        final int currentPosition = mTmpState.getInt(EXTRA_CURRENT_ITEM_POSITION);

        findMediaFragment1().getPresenter().updateAdapterEntries();

        final RecyclerView recyclerView = findMediaFragment1().getRecyclerView();
        if (recyclerView != null) {
            postponeEnterTransition();

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (oldPosition != currentPosition) {
                        recyclerView.scrollToPosition(currentPosition);
                    }


                    recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                            startPostponedEnterTransition();
                            return true;
                        }
                    });
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (mMediaCab1 != null) {
            mMediaCab1.finish();
            mMediaCab1 = null;
        } else {
        /*    if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else*/ if (mBreadCrumbLayout.popHistory()) {
                // Go to previous crumb in history
                final Crumb crumb = mBreadCrumbLayout.lastHistory();
                switchAlbum(crumb, false, false);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        invalidateMenuTint();

        switch(item.getItemId())
        {
            case android.R.id.home:  if (isSelectAlbumMode()) {
                finish();
                return true;
            }
            default:            return super.onOptionsItemSelected(item);
        }

    }

    public void openSettings() {

                startActivityForResult(new Intent(SizeActivity.this, SettingsActivity.class), REQUEST_SETTINGS);


    }

    public void showAboutDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.about_dialog_title, BuildConfig.VERSION_NAME))
                .positiveText(R.string.dismiss)
                .content(Html.fromHtml(getString(R.string.about_body)))
                .iconRes(R.mipmap.ic_launcher)
                .linkColor(accentColor())
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTINGS && resultCode == Activity.RESULT_OK) {
            MediaFragment1 content = findMediaFragment1();
            if (content != null) {
                content.getPresenter().reload();
            }
            //  reloadNavDrawerAlbums();
        }
    }

    private MediaFragment1 findMediaFragment1() {
        return (MediaFragment1) getFragmentManager().findFragmentById(R.id.content_frame);
    }

   /* public boolean navDrawerSwitchAlbum(String path) {
        //mDrawerLayout.closeDrawers();

        if (path.equals(LocalMediaFolderEntry.OVERVIEW_PATH) && PrefUtils.isExplorerMode(this)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        mBreadCrumbLayout.setTopPath(path);

        MediaFragment1 frag = findMediaFragment1();
        if (!path.equals(frag.getPresenter().getPath())) {
            mBreadCrumbLayout.clearHistory();
            Crumb crumb = new Crumb(this, path);
            switchAlbum(crumb, true, true);
            return true;
        }
        return false;
    }*/

    public void switchAlbum(String path) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }

        boolean initialCreate = (path == null);
        if (initialCreate) {
            // Initial directory
            path = PrefUtils.isExplorerMode(this) ?
                    Environment.getExternalStorageDirectory().getAbsolutePath() :
                    LocalMediaFolderEntry.OVERVIEW_PATH;
            mBreadCrumbLayout.setTopPath(path);
        }

        Crumb crumb = new Crumb(this, path);
        switchAlbum(crumb, initialCreate, true);
    }

    public void switchAlbum(Crumb crumb, boolean forceRecreate, boolean addToHistory) {
        if (forceRecreate) {
            // Rebuild artificial history, most likely first time load
            mBreadCrumbLayout.clearHistory();
            String path = crumb.getPath();
            while (path != null) {
                mBreadCrumbLayout.addHistory(new Crumb(this, path));
                if (mBreadCrumbLayout.isTopPath(path)) {
                    break;
                }
                path = new File(path).getParent();
            }
            mBreadCrumbLayout.reverseHistory();
        } else if (addToHistory) {
            mBreadCrumbLayout.addHistory(crumb);
        }
        mBreadCrumbLayout.setActiveOrAdd(crumb, forceRecreate);

        final String to = crumb.getPath();
        MediaFragment1 frag = findMediaFragment1();

        if (frag == null) {
            frag = MediaPresenter1.newInstance(to);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
        } else {
            MediaPresenter1 presenter = frag.getPresenter();
            String albumPath = presenter.getPath();
            if (!albumPath.equals(to)) {
                presenter.setPath(to);
            }
        }
    }

    @Override
    public void onFolderSelection(File folder) {
        IncludedFolderProvider.add(this, folder);
        // reloadNavDrawerAlbums();
    }



    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        invalidateMenuTint();
        invalidateMenuIcons(menu);
        //    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        return super.onPrepareOptionsMenu(menu);
    }

    public void invalidateMenuIcons(final Menu menu) {
        if (menu != null && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Field field = menu.getClass().getDeclaredField("mOptionalIconsVisible");
                field.setAccessible(true);
                field.setBoolean(menu, true);

                final boolean darkMode = isDarkTheme();
                final int textColorPrimary = Utils.resolveColor(this, android.R.attr.textColorSecondary);

                mToolbar.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < menu.size(); i++) {
                            MenuItemImpl item = (MenuItemImpl) menu.getItem(i);
                            int color = darkMode || item.isActionButton() ? Color.WHITE : textColorPrimary;
                            if (item.getIcon() != null) {
                                item.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void invalidateMenuTint() {
        mToolbar.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field f1 = Toolbar.class.getDeclaredField("mMenuView");
                    f1.setAccessible(true);
                    ActionMenuView actionMenuView = (ActionMenuView) f1.get(mToolbar);

                    Field f2 = ActionMenuView.class.getDeclaredField("mPresenter");
                    f2.setAccessible(true);

                    //Actually ActionMenuPresenter
                    BaseMenuPresenter presenter = (BaseMenuPresenter) f2.get(actionMenuView);

                    Field f3 = presenter.getClass().getDeclaredField("mOverflowPopup");
                    f3.setAccessible(true);
                    MenuPopupHelper overflowMenuPopupHelper = (MenuPopupHelper) f3.get(presenter);
                    setTintForMenuPopupHelper(overflowMenuPopupHelper);

                    Field f4 = presenter.getClass().getDeclaredField("mActionButtonPopup");
                    f4.setAccessible(true);
                    MenuPopupHelper subMenuPopupHelper = (MenuPopupHelper) f4.get(presenter);
                    setTintForMenuPopupHelper(subMenuPopupHelper);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTintForMenuPopupHelper(MenuPopupHelper menuPopupHelper) {
        if (menuPopupHelper != null) {
            final ListView listView = menuPopupHelper.getPopup().getListView();
            listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        Field checkboxField = ListMenuItemView.class.getDeclaredField("mCheckBox");
                        checkboxField.setAccessible(true);
                        Field radioButtonField = ListMenuItemView.class.getDeclaredField("mRadioButton");
                        radioButtonField.setAccessible(true);

                        for (int i = 0; i < listView.getChildCount(); i++) {
                            View v = listView.getChildAt(i);
                            if (!(v instanceof ListMenuItemView)) {
                                continue;
                            }
                            ListMenuItemView iv = (ListMenuItemView) v;

                            CheckBox check = (CheckBox) checkboxField.get(iv);
                            if (check != null) {
                                MDTintHelper.setTint(check, ThemeSingleton.get().widgetColor);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    check.setBackground(null);
                                }
                            }

                            RadioButton radioButton = (RadioButton) radioButtonField.get(iv);
                            if (radioButton != null) {
                                MDTintHelper.setTint(radioButton, ThemeSingleton.get().widgetColor);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    radioButton.setBackground(null);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        //noinspection deprecation
                        listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public enum SelectAlbumMode {
        NONE,
        COPY,
        MOVE,
        CHOOSE
    }

    /* private class AnimatedDrawerToggle extends ActionBarDrawerToggle {

         private float mOffset;

 /*        public AnimatedDrawerToggle() {
             super(MainActivity.this, MainActivity.this.mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         }

         public float getOffset() {
             return mOffset;
         }

         public void setOffset(float slideOffset) {
             super.onDrawerSlide(null, slideOffset);
             mOffset = slideOffset;
         }
     }*/
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls

    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        switch(position)
        {
            case 0:

                // Toast.makeText(MainActivity.this,"intnet",Toast.LENGTH_LONG).show();
                this.finish();
                Intent i=new Intent(App.context,MainActivity.class);
                startActivity(i);
                break;

            case 3:

                mBreadCrumbLayout.setVisibility(View.GONE);

                btn.hide();
                mToolbar.setTitle("Media Rescan");
                mBreadCrumbLayout.setTopPath("");

                transaction.replace(R.id.content_frame,new fragment_MediaScan());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 2:
               // mAdView.setVisibility(View.GONE);
                mBreadCrumbLayout.setVisibility(View.GONE);


                btn.hide();
                mToolbar.setTitle("HeadSet Connect Settings");
                //mBreadCrumbLayout.setTopPath("");

                transaction.replace(R.id.content_frame,new HeadSetFrament());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:

                // Toast.makeText(MainActivity.this,"intnet",Toast.LENGTH_LONG).show();
                this.finish();
                Intent i1=new Intent(App.context,SizeActivity.class);
                startActivity(i1);
                break;
            case 4: openSettings();
                break;
            case 5:


                showAboutDialog();
                break;

        }
      /*  Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(
        mDrawerList);*/
    }

}