package com.shailendra.photonotesgallery.media;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileObserver;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import com.shailendra.photonotesgallery.App;
import com.shailendra.photonotesgallery.providers.ExcludedFolderProvider;

import java.io.File;

public class MyFileObserver extends FileObserver {
    public String absolutePath;
    String filecreated="";
    boolean created=false;
    public MyFileObserver(String path) {
        super(path, FileObserver.ALL_EVENTS);
        absolutePath = path;
    }

    @Override
    public void onEvent(int event, String path) {
        if (path == null) {
            return;
        }
        //a new file or subdirectory was created under the monitored directory
        if ((FileObserver.CREATE & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is created\n";
            created=true;
            filecreated=path;
        }
        //a file or directory was opened
        if ((FileObserver.OPEN & event)!=0) {
            //	FileAccessLogStatic.accessLogMsg += path + " is opened\n";
        }
        //data was read from a file
        if ((FileObserver.ACCESS & event)!=0) {
            //	FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is accessed/read\n";
        }
        //data was written to a file
        if ((FileObserver.MODIFY & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is modified\n";
        }
        //someone has a file or directory open read-only, and closed it
        if ((FileObserver.CLOSE_NOWRITE & event)!=0) {
            FileAccessLogStatic.accessLogMsg += path + " is closed\n";
            String Sdcard= Environment.getExternalStorageDirectory().getAbsolutePath();
            SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(App.context);

            final String name1 = preferences1.getString("name1", null);

            if(created==true&&filecreated.equals(path))
            {
                created=false;
                File srcabs = new File(absolutePath + "/" + path);
                File srcloc = new File(Sdcard + "/DCIM/Camera/" + path);
                File Filetemp=srcloc;
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                   String extStorageDirectory= Environment.getExternalStorageDirectory().toString();
                    extStorageDirectory+="/PhotoNotesData/"+name1+"/";
                    Log.v("Folder",extStorageDirectory);
               File fileDirectory=new File(extStorageDirectory);
                    if(ExcludedFolderProvider.contains(App.context,fileDirectory.getPath()))
                    {
                        ExcludedFolderProvider.remove(App.context,fileDirectory.getPath());
                    }
                    fileDirectory.mkdirs();
                    //CopyAssest();

                }
                File tarabs = new File(Sdcard + "/PhotoNotesData/"+name1);
                File tarloc = new File(tarabs.getAbsolutePath() + "/" + path);

                Log.v("Myob", "source loc" + srcloc);
                Log.v("Myob", "target loc" + tarloc);
                try {
                    if (srcloc.renameTo(tarloc)) {
                        Log.v("Myob", "Move file successfull");
                    } else {
                        Log.v("Myob", "Mobe file failed");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 17)
                    MediaScannerConnection.scanFile(App.context,
                            new String[] { tarloc.getAbsolutePath() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                        Log.v("MediaScanWork", "file " + path
                                               + " was scanned seccessfully: " + uri);
                                }
                            });
                else
                {

                    String saveAs = tarloc.toString();
                    Uri contentUri = Uri.fromFile(new File(saveAs));
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
                    App.context.sendBroadcast(mediaScanIntent);
                }
                srcloc.delete();
                App.context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.Media.DATA + " = ?",
                        new String[]{srcloc.getAbsolutePath()});

            }


        }
        //someone has a file or directory open for writing, and closed it
        if ((FileObserver.CLOSE_WRITE & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is written and closed\n";
            String Sdcard= Environment.getExternalStorageDirectory().getAbsolutePath();
            SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(App.context);

            final String name1 = preferences1.getString("name1", null);

            if(created==true&&filecreated.equals(path))
            {
                created=false;
                File srcabs = new File(absolutePath + "/" + path);
                File srcloc = new File(Sdcard + "/DCIM/Camera/" + path);
                File Filetemp=srcloc;
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    String extStorageDirectory= Environment.getExternalStorageDirectory().toString();
                    extStorageDirectory+="/PhotoNotesData/"+name1+"/";
                    File fileDirectory=new File(extStorageDirectory);

                    if(ExcludedFolderProvider.contains(App.context,fileDirectory.getPath()))
                    {
                        ExcludedFolderProvider.remove(App.context,fileDirectory.getPath());
                    }
                    Log.v("Folder",extStorageDirectory);

                    fileDirectory.mkdirs();
                    //CopyAssest();

                }

                File tarabs = new File(Sdcard + "/PhotoNotesData/"+name1);
                File tarloc = new File(tarabs.getAbsolutePath() + "/" + path);

                Log.v("Myob", "source loc" + srcloc);
                Log.v("Myob", "target loc" + tarloc);
                try {
                    if (srcloc.renameTo(tarloc)) {
                        Log.v("Myob", "Move file successfull");
                    } else {
                        Log.v("Myob", "Mobe file failed");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 17)
                    MediaScannerConnection.scanFile(App.context,
                            new String[] { tarloc.getAbsolutePath() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                         Log.v("MediaScanWork", "file " + path
                                               + " was scanned seccessfully: " + uri);
                                }
                            });
                else
                {

                    String saveAs = tarloc.toString();
                    Uri contentUri = Uri.fromFile(new File(saveAs));
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
                    App.context.sendBroadcast(mediaScanIntent);
                }
                srcloc.delete();
                App.context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.Media.DATA + " = ?",
                        new String[]{srcloc.getAbsolutePath()});

            }
        }
        //[todo: consider combine this one with one below]
        //a file was deleted from the monitored directory
        if ((FileObserver.DELETE & event)!=0) {
            //for testing copy file
//			FileUtils.copyFile(absolutePath + "/" + path);
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is deleted\n";
        }
        //the monitored file or directory was deleted, monitoring effectively stops
        if ((FileObserver.DELETE_SELF & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + " is deleted\n";
        }
        //a file or subdirectory was moved from the monitored directory
        if ((FileObserver.MOVED_FROM & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is moved to somewhere " + "\n";
        }
        //a file or subdirectory was moved to the monitored directory
        if ((FileObserver.MOVED_TO & event)!=0) {
            FileAccessLogStatic.accessLogMsg += "File is moved to " + absolutePath + "/" + path + "\n";
            created=true;
            filecreated=path;
        }
        //the monitored file or directory was moved; monitoring continues
        if ((FileObserver.MOVE_SELF & event)!=0) {
            FileAccessLogStatic.accessLogMsg += path + " is moved\n";
        }
        //Metadata (permissions, owner, timestamp) was changed explicitly
        if ((FileObserver.ATTRIB & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is changed (permissions, owner, timestamp)\n";
        }
    }
}
