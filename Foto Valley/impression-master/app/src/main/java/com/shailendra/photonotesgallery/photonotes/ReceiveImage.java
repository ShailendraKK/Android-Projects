package com.shailendra.photonotesgallery.photonotes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
//import android.util.Log;

import com.shailendra.photonotesgallery.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiveImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri imageUri;;

        setContentView(R.layout.activity_receive_image);
       final Intent intent=getIntent();
        String action=intent.getAction();
        String type=intent.getType();
       if(Intent.ACTION_SEND_MULTIPLE.equals(action)&&type!=null)
       {
           {
               AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveImage.this, R.style.MyAlertDialogStyle);
               builder.setTitle("Select your choice");
               builder.setMessage("Pleas Enter the name with which you want to group the photo");
               View name_view = getLayoutInflater().inflate(R.layout.name1, null);

               final EditText notes_name = (EditText) name_view.findViewById(R.id.notes_name);


               builder.setView(name_view);
               builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       String name = notes_name.getText().toString();
                       ArrayList<Uri> filesListTemp=new ArrayList<Uri>( );
                       filesListTemp=intent.getExtras().getParcelableArrayList(Intent.EXTRA_STREAM);
                      int i1=0;
                       for(Uri fileURI:filesListTemp)
                       {
                             File source_file=new File(fileURI.getPath());
                               String destination=Environment.getExternalStorageDirectory().toString()+"/PhotoNotesData/"+name+"/";
                               File fileDirectory=new File(destination);
                               fileDirectory.mkdirs();
                               String filename=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+i1+".jpg";
                         i1++;

                               File dest_file=new File(destination+filename);


                               try {

                                   InputStream in = new FileInputStream(source_file);
                                   OutputStream out = new FileOutputStream(dest_file);
                                   byte[] buf = new byte[1024];
                                   int len;
                                   while ((len = in.read(buf)) > 0) {
                                       out.write(buf, 0, len);
                                   }
                                   in.close();
                                   out.close();
                                   if (Build.VERSION.SDK_INT >= 17)
                                       MediaScannerConnection.scanFile(ReceiveImage.this,
                                               new String[] { dest_file.getAbsolutePath() }, null,
                                               new MediaScannerConnection.OnScanCompletedListener() {
                                                   @Override
                                                   public void onScanCompleted(String path, Uri uri) {
                                                       //             Log.v("MediaScanWork", "file " + path
                                                       //                   + " was scanned seccessfully: " + uri);
                                                   }
                                               });
                                   else
                                   {

                                       String saveAs = dest_file.toString();
                                       Uri contentUri = Uri.fromFile(new File(saveAs));
                                       Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
                                       sendBroadcast(mediaScanIntent);
                                   }


                               } catch (FileNotFoundException e) {
                                   e.printStackTrace();
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }


                       }
                       Intent i=new Intent(ReceiveImage.this,com.shailendra.photonotesgallery.media.MainActivity.class);
                       startActivity(i);
                       ReceiveImage.this.finish();

                   }
               });
               builder.setNegativeButton("Cancel", null);
               builder.show();

           }

       }
        if(Intent.ACTION_SEND.equals(action)&&type!=null){
            if(type.startsWith("image/")){
                        handleSentImage(intent);

                Intent i=new Intent(ReceiveImage.this,com.shailendra.photonotesgallery.media.MainActivity.class);
                startActivity(i);
                ReceiveImage.this.finish();
            }
        }
    }

    private void handleSentImage(Intent intent) {
        Uri imageUri=(Uri)intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if(imageUri!=null)
        {   File source_file=new File(imageUri.getPath());
            String destination=Environment.getExternalStorageDirectory().toString()+"/PhotoNotesData/imp/";
            File fileDirectory=new File(destination);
            fileDirectory.mkdirs();
            String filename=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";

            File dest_file=new File(destination+filename);


            try {

                InputStream in = new FileInputStream(source_file);
                OutputStream out = new FileOutputStream(dest_file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                if (Build.VERSION.SDK_INT >= 17)
                    MediaScannerConnection.scanFile(ReceiveImage.this,
                            new String[] { dest_file.getAbsolutePath() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                       //             Log.v("MediaScanWork", "file " + path
                         //                   + " was scanned seccessfully: " + uri);
                                }
                            });
                else
                {

                    String saveAs = dest_file.toString();
                    Uri contentUri = Uri.fromFile(new File(saveAs));
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
                    sendBroadcast(mediaScanIntent);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
