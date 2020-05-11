package com.shailendra.photonotesgallery.photonotes;

/**
 * Created by shailendra on 29-11-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
//import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.shailendra.photonotesgallery.R;
import com.shailendra.photonotesgallery.base.ThemedActivity;
import com.shailendra.photonotesgallery.providers.ExcludedFolderProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import com.shailendra.impression.navdrawer.NavDrawerFragment;
//import android.app.FragmentTransaction;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.app.FragmentManager;


public class orient extends ThemedActivity implements Camera.AutoFocusCallback{
    private OrientationEventListener mOrientationEventListener;
    private InterstitialAd mInterstitialAd;

    private int mOrientation =  -1;
    String notesname;
    private static final int ORIENTATION_PORTRAIT_NORMAL =  1;
    private static final int ORIENTATION_PORTRAIT_INVERTED =  2;
    private static final int ORIENTATION_LANDSCAPE_NORMAL =  3;
    private static final int ORIENTATION_LANDSCAPE_INVERTED =  4;
    private FloatingActionButton back_btn;
    private FloatingActionButton done_btn;
    private FloatingActionButton flash_btn;
    private FloatingActionButton button;
    private CircleImageView img;
    static int mStackLevel=0;
    private int camera_id=0;

    String photonotesfolder="";
    private Camera camera;
    String extStorageDirectory;
    private static final int REQUEST_NAME = 0;
    private static final String NAME = "name";
    int count=0;

    File f;
    private static final String TAG="orient";
    public static final String EXTRA_PHOTO_FILENAME="com.example.criminalintent.photo_filename";
    private SurfaceView surfaceView;
    View progressBarContainer;
    private Camera.ShutterCallback shutterCallback=new Camera.ShutterCallback() {

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub
            // progressBarContainer.setVisibility(View.VISIBLE);
        }
    };
    private Camera.PictureCallback pictureCallback=new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                extStorageDirectory= Environment.getExternalStorageDirectory().toString();
                extStorageDirectory+="/PhotoNotesData/"+photonotesfolder+"/";

                File fileDirectory=new File(extStorageDirectory);
                if(ExcludedFolderProvider.contains(orient.this,fileDirectory.getPath()))
                {
                    ExcludedFolderProvider.remove(orient.this,fileDirectory.getPath());
                }
                fileDirectory.mkdirs();
                //CopyAssest();

            }
            else
            {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
                                Toast.makeText(orient.this, "Sd card missing", Toast.LENGTH_LONG);

                }
            }
            String filename=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
            FileOutputStream os=null;
            String temp;
            boolean success=true;
            try
            {


                extStorageDirectory+=filename;
                f=new File(extStorageDirectory);
                //os=getActivity(*).openFileOutput(extStorageDirectory, Context.MODE_PRIVATE);
                os=new FileOutputStream(f);
                os.write(arg0);
                os.flush();
                if(mOrientation==1||mOrientation==2||mOrientation==4) {
                    Bitmap bmp = BitmapFactory.decodeFile(extStorageDirectory);
                    Matrix matrix = new Matrix();
                    if(mOrientation==1)
                        matrix.postRotate(90);
                    else if(mOrientation==4)
                        matrix.postRotate(180);
                    else
                        matrix.postRotate(270);

                    bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                    os = new FileOutputStream(f);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 85, os);
                    os.flush();
                    if (Build.VERSION.SDK_INT >= 17)
                    MediaScannerConnection.scanFile(orient.this,
                            new String[] { f.getAbsolutePath() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                               //     Log.v("MediaScanWork", "file " + path
                                 //           + " was scanned seccessfully: " + uri);
                                }
                            });
                    else
                    {

                        String saveAs = f.toString();
                        Uri contentUri = Uri.fromFile(new File(saveAs));
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
                        sendBroadcast(mediaScanIntent);
                    }
                }
            }
            catch(Exception e){

              //  Log.e(TAG, "Error writting to file", e);
                success=false;
            }
            finally{
                try{
                    if(os!=null)
                        os.close();
                }
                catch(Exception e){
                //    Log.e(TAG, "Error closing file", e);
                    success=false;
                }
            }
            if(success) {
                //Log.i(TAG, "JPEG saved as :"+filename);

           //     Toast.makeText(orient.this, "File saved successfully", Toast.LENGTH_LONG);
                Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(bmp);
                /*if (Build.VERSION.SDK_INT >= 14) {
                    MediaScannerConnection.scanFile(orient.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, null);
                } else {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                }*/

            }
            else
            {
                setResult(Activity.RESULT_CANCELED);
            }
            //
            // progressBarContainer.setVisibility(View.INVISIBLE);
            camera.startPreview();

            //getActivity().finish();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orient);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            photonotesfolder=extras.getString("name");
        }
        else
        {
            photonotesfolder="temp";
        }
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.interstertile_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // ViewerActivity.this.onBackPressed();
                Intent i=new Intent(orient.this,com.shailendra.photonotesgallery.media.MainActivity.class);
                startActivity(i);
                orient.this.finish();
            }
        });
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

        // textView=(TextView)findViewById(R.id.text);
        button=(FloatingActionButton)findViewById(R.id.btndemo);
        back_btn = (FloatingActionButton) findViewById(R.id.back_btn);
        back_btn.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{primaryColor()}));

        done_btn = (FloatingActionButton) findViewById(R.id.done);
        done_btn.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{primaryColor()}));

        button.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{primaryColor()}));

        flash_btn = (FloatingActionButton) findViewById(R.id.flash);
        flash_btn.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{primaryColor()}));


        img=(CircleImageView)findViewById(R.id.image_view);
        // progressBarContainer = (View) findViewById(R.id.crime_camera_progressContainer);
        //progressBarContainer.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //getActivity().finish();

                if (camera != null) {

                    button.setEnabled(false);

                    camera.takePicture(shutterCallback, null, pictureCallback);
                    button.setEnabled(true);
                    Camera.Parameters params=camera.getParameters();
                    if(params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                    }
                    camera.setParameters(params);

                }
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //getActivity().finish()==]=getActivity().finish();

                Intent i=new Intent(orient.this,com.shailendra.photonotesgallery.media.MainActivity.class);
                startActivity(i);
                orient.this.finish();

            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(orient.this);

                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("name",null);

                edit.commit();
                Intent i=new Intent(orient.this,com.shailendra.photonotesgallery.media.MainActivity.class);
                startActivity(i);
                orient.this.finish();

 /*               if(camera_id==0)
                {
                    camera_id=1;
                }
                else
                {
                    camera_id=0;
                }
                camera=Camera.open(camera_id);
*/

            }

        });


        flash_btn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                PackageManager pm = getApplicationContext().getPackageManager();
                final Camera.Parameters p = camera.getParameters();
                count++;
                if (count == 1) {


                    flash_btn.setImageResource(R.mipmap.flash_on);
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();

                } else if (count == 2) {
                    flash_btn.setImageResource(R.mipmap.flash_off);
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.startPreview();

                } else if (count == 3) {
                    flash_btn.setImageResource(R.mipmap.auto_flash);
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    count = 0;
                }
            }
        });
        surfaceView = (SurfaceView) findViewById(R.id.crime_camera_surfaceView);

        SurfaceHolder holder = surfaceView.getHolder();
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                Camera.Parameters params=camera.getParameters();
                if(params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

                }
                camera.setParameters(params);


                camera.autoFocus(orient.this);
            }
        });



        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

                if (camera != null) {
                    camera.stopPreview();

                }
            }


            @Override
            public void surfaceCreated(SurfaceHolder arg0) {
                // TODO Auto-generated method stub

                try {
                    if (camera != null)
                        camera.setPreviewDisplay(arg0);
                } catch (IOException e) {
                  //  Log.e(TAG, "Error setting up Preview Display", e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int w, int h) {
                // TODO Auto-generated method stub
                if(camera!=null)
                {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size s = getSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
                parameters.setPreviewSize(s.width, s.height);
                s = getSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
                parameters.setPictureSize(s.width, s.height);
                camera.setParameters(parameters);
                try {
                    camera.startPreview();

                    Camera.Parameters params = camera.getParameters();
                    if (params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                    }
                    camera.setParameters(params);


                } catch (Exception e) {
                   // Log.e(TAG, "Could not start preview", e);
                    camera.release();
                    camera = null;
                }}

            }
        });
    }

    private Camera.Size getSupportedSize(List<Camera.Size> sizes,int width,int height){
        Camera.Size bestSize=sizes.get(0);
        int largestSize=bestSize.height*bestSize.width;
        for(Camera.Size s :sizes){
            int area=s.height*s.width;
            if(area>largestSize){
                bestSize=s;
                largestSize=area;
            }
        }
        return bestSize;
    }

    @Override
    protected void onResume() {
        super.onResume();
      try {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
              camera = Camera.open(0);
          } else {
              camera = Camera.open();//android.hardware.Camera.open();
          }
      }
      catch(Exception e1)
      {
          Toast.makeText(this,"Cannot connect to camera",Toast.LENGTH_LONG);
      }
        if (mOrientationEventListener == null) {
            mOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {

                @Override
                public void onOrientationChanged(int orientation) {

                    // determine our orientation based on sensor response
                    int lastOrientation = mOrientation;

                    if (orientation >= 315 || orientation < 45) {
                        if (mOrientation != ORIENTATION_PORTRAIT_NORMAL) {
                            mOrientation = ORIENTATION_PORTRAIT_NORMAL;
                        }
                    }
                    else if (orientation < 315 && orientation >= 225) {
                        if (mOrientation != ORIENTATION_LANDSCAPE_NORMAL) {
                            mOrientation = ORIENTATION_LANDSCAPE_NORMAL;
                        }
                    }
                    else if (orientation < 225 && orientation >= 135) {
                        if (mOrientation != ORIENTATION_PORTRAIT_INVERTED) {
                            mOrientation = ORIENTATION_PORTRAIT_INVERTED;
                        }
                    }
                    else { // orientation <135 && orientation > 45
                        if (mOrientation != ORIENTATION_LANDSCAPE_INVERTED) {
                            mOrientation = ORIENTATION_LANDSCAPE_INVERTED;
                        }
                    }

                    if (lastOrientation != mOrientation) {
                        changeRotation(mOrientation, lastOrientation);
                    }
                }
            };
        }
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camera!=null){
            camera.release();
            camera=null;
        }
        mOrientationEventListener.disable();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_NAME) {

        }
    }

    /***
     * Performs required action to accommodate new orientation
     * @param orientation
     * @param lastOrientation
     */
    private void changeRotation(int orientation, int lastOrientation) {
        switch (orientation) {
            case ORIENTATION_PORTRAIT_NORMAL:
                button.setImageDrawable(getRotatedImage(R.mipmap.ic_action, 270));
                back_btn.setImageDrawable(getRotatedImage(R.mipmap.back, 270));
                done_btn.setImageDrawable(getRotatedImage(R.mipmap.done, 270));
                flash_btn.setImageDrawable(getRotatedImage(R.mipmap.auto_flash, 270));
                //img.setImageDrawable(getRotatedImage(R.mipmap.auto_flash, 270));
                if(img.VISIBLE== View.VISIBLE) {
                    img.setRotation(270);
                }
             //   Log.v("CameraActivity", "Orientation = 90");
                break;
            case ORIENTATION_LANDSCAPE_NORMAL:

                button.setImageResource(R.mipmap.ic_action);
                back_btn.setImageResource(R.mipmap.back);
                done_btn.setImageResource(R.mipmap.done);
                flash_btn.setImageResource(R.mipmap.auto_flash);
                if(img.VISIBLE== View.VISIBLE) {
                    img.setRotation(0);
                }
               // Log.v("CameraActivity", "Orientation = 0");
                break;
            case ORIENTATION_PORTRAIT_INVERTED:

                button.setImageDrawable(getRotatedImage(R.mipmap.ic_action, 90));
                back_btn.setImageDrawable(getRotatedImage(R.mipmap.back, 90));
                done_btn.setImageDrawable(getRotatedImage(R.mipmap.done, 90));
                flash_btn.setImageDrawable(getRotatedImage(R.mipmap.auto_flash, 90));
                if(img.VISIBLE== View.VISIBLE) {
                    img.setRotation(90);
                }
               // Log.v("CameraActivity", "Orientation = 270");
                break;
            case ORIENTATION_LANDSCAPE_INVERTED:

                button.setImageDrawable(getRotatedImage(R.mipmap.ic_action, 180));
                back_btn.setImageDrawable(getRotatedImage(R.mipmap.back, 180));
                done_btn.setImageDrawable(getRotatedImage(R.mipmap.done, 180));
                flash_btn.setImageDrawable(getRotatedImage(R.mipmap.auto_flash, 180));
                if(img.VISIBLE== View.VISIBLE) {
                    img.setRotation(180);
                }
               // Log.v("CameraActivity", "Orientation = 180");
                break;
        }
    }

    /**
     * Rotates given Drawable
     * @param drawableId    Drawable Id to rotate
     * @param degrees       Rotate drawable by Degrees
     * @return              Rotated Drawable
     */

    private Drawable getRotatedImage(int drawableId, int degrees) {
        Bitmap original = BitmapFactory.decodeResource(getResources(), drawableId);
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        Bitmap rotated = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
        return new BitmapDrawable(rotated);
    }
    final Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback(){

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            // TODO Auto-generated method stub
            if (arg0){
                button.setEnabled(true);
                camera.cancelAutoFocus();
            }
        }};


    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if(success)
        {
            button.setEnabled(true);
    //    camera.cancelAutoFocus();

        }

    }
    @Override
    public void onBackPressed() {
        showInterstitial();
        super.onBackPressed();
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

        } else {


            super.onBackPressed();
        }
    }
}
