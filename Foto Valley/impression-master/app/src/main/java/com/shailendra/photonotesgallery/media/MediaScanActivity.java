package com.shailendra.photonotesgallery.media;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shailendra.photonotesgallery.App;
import com.shailendra.photonotesgallery.R;

import org.w3c.dom.Text;

import java.io.File;



public class MediaScanActivity extends Activity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button mediaScan;

    private Button filechooser;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragment__media_scan);
        mediaScan=(Button)findViewById(R.id.btn_mediascan);

        mediaScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error=0;
                int error1=0;
                try
                {
                    MediaScannerWrapper msw=new MediaScannerWrapper(App.context,Uri.parse("file://"+ Environment.getExternalStorageDirectory()).toString(),"*/*");
                    msw.scan();
                    error=0;
                }
                catch(Exception e1)
                {
                    error=1;
                }
                try
                {
                    if (Build.VERSION.SDK_INT < 17) {
                        MediaScanActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                        error = 0;
                    }
                }
                catch(Exception e)
                {
                    error=1;
                }
                try
                {
                    Runtime.getRuntime().exec("am broadcast -a android.intent.action.MEDIA_MOUNTED -d file://"+Environment.getExternalStorageDirectory());
                    error1=0;
                }
                catch(Exception e4)
                {
                    error1=1;
                }
                try
                {
                    if (Build.VERSION.SDK_INT < 17) {
                       MediaScanActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///Removable")));
                        MediaScanActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///Removable/SD")));
                        MediaScanActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///Removable/MicroSD")));
                        MediaScanActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///mnt")));
                        MediaScanActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///storage")));


                        error1 = 0;
                    }
                }
                catch(Exception e2)
                {
                    error1=1;
                }
                if(error==0&&error1==0)
                {
                    Toast.makeText(MediaScanActivity.this,"Media Scan Successfully completed",Toast.LENGTH_LONG).show();
                }
                else if(error==0&&error1==1)
                {
                    Toast.makeText(MediaScanActivity.this,"Media Scan Successfully completed : Some files may not have been scanned please add files individually",Toast.LENGTH_LONG).show();


                }
                else{
                    Toast.makeText(MediaScanActivity.this,"Media Scan Failed : Try adding files individually",Toast.LENGTH_LONG).show();


                }
            }
        });
        filechooser=(Button)findViewById(R.id.btn_file);
        filechooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FileChooser(MediaScanActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        if (Build.VERSION.SDK_INT >= 17)
                            MediaScannerConnection.scanFile(MediaScanActivity.this,
                                    new String[] { file.getAbsolutePath() }, null,
                                    new MediaScannerConnection.OnScanCompletedListener() {
                                        @Override
                                        public void onScanCompleted(String path, Uri uri) {
                                            //     Log.v("MediaScanWork", "file " + path
                                            //           + " was scanned seccessfully: " + uri);
                                        }
                                    });
                        else
                        {

                            String saveAs = file.toString();
                            Uri contentUri = Uri.fromFile(new File(saveAs));
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
                            App.context.sendBroadcast(mediaScanIntent);
                        }
                        Toast.makeText(MediaScanActivity.this,"Media Scan successfull for file "+file,Toast.LENGTH_LONG).show();


                    }
                }).showDialog();
            }
        });

    }




}
