package com.shailendra.photonotesgallery.media;

import android.app.Activity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.shailendra.photonotesgallery.BuildConfig;
import com.shailendra.photonotesgallery.R;


/**
 * Created by shailendra on 26-02-2017.
 */

public class HeadSetActivity  extends Activity{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // private Button btn_app_choose;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //  private TextView appName;
    //  private Button png_launch;

    private Switch headset_auto;
    private Switch foto_valley;
    private Switch otherapp;
    private HeadSetFrament.OnFragmentInteractionListener mListener;
    String mode1="";
    private Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.fragment_head_set_frament);
        headset_auto=(Switch)findViewById(R.id.switch1);
        foto_valley=(Switch)findViewById(R.id.switch2);
        otherapp=(Switch)findViewById(R.id.switch3);
        help=(Button)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                builder.setTitle("Help");
                builder.setMessage(R.string.help);
                builder.setPositiveButton(android.R.string.ok,null);

                builder.show();
*/
                new MaterialDialog.Builder(HeadSetActivity.this)
                        .title("Help")
                        .positiveText(R.string.dismiss)
                        .content(Html.fromHtml(getString(R.string.help)))
                        .iconRes(R.mipmap.ic_launcher)
                        .show();
            }
        });
        headset_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HeadSetActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                if(isChecked){
                    foto_valley.setEnabled(true);
                    otherapp.setEnabled(true);
                    edit.putString("headset_detection","enabled");
                    edit.commit();

                }
                else
                {
                    foto_valley.setEnabled(false);
                    otherapp.setEnabled(false);
                    edit.putString("headset_detection","disabled");
                    edit.commit();

                }
            }
        });
        foto_valley.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HeadSetActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                if(isChecked)
                {
                    otherapp.setChecked(false);
                    edit.putString("launch_mode","myapp");
                    edit.commit();
                }
                else
                {
                    edit.putString("launch_mode","nomyapp");
                    edit.commit();
                }



            }
        });
        otherapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    foto_valley.setChecked(false);
                    Intent appIntent = new Intent(HeadSetActivity.this, Chooser.class);
                    startActivityForResult(appIntent, 1);
                }
                else
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HeadSetActivity.this);
                    SharedPreferences.Editor edit = preferences.edit();

                    edit.putString("launch_mode","nootherapp");
                    edit.commit();
                }

            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HeadSetActivity.this);

        final String mode = preferences.getString("launch_mode", null);
        final String enabled=preferences.getString("headset_detection",null);
        if(mode==null)
        {
            foto_valley.setChecked(false);
            otherapp.setChecked(false);
            // png_launch.setText("Foto Valley Launch is Disabled");
            // btn_app_choose.setText("Other App Launch is Disabled");
        }

        else if (mode.equals("myapp")) {
            mode1=mode;
            foto_valley.setChecked(true);
            //png_launch.setText("Foto Valley Launch is Enabled");
            //btn_app_choose.setText("Other App Launch is Disabled");
        } else if (mode.equals("otherapp")) {
            // png_launch.setText("Foto Valley Launch is Disabled");
            //btn_app_choose.setText("Other App Launch is Enabled");
            otherapp.setChecked(true);
        }
        if(enabled!=null) {
            if (enabled.equals("disabled")) {

                headset_auto.setChecked(false);
                foto_valley.setEnabled(false);
                otherapp.setEnabled(false);

            }
            if (enabled.equals("enabled")) {
                //  HeadSetEnabled.setText("Click to Disable HeadSetDetection");
                headset_auto.setChecked(true);
                foto_valley.setEnabled(true);
                otherapp.setEnabled(true);
                //png_launch.setEnabled(true);
                // btn_app_choose.setEnabled(true);
            }
        }
        else
        {

            headset_auto.setChecked(false);
            foto_valley.setEnabled(false);
            otherapp.setEnabled(false);
        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            if (requestCode == 1) {
                //Do somethingString
                String message = data.getStringExtra("MESSAGE_package_name");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HeadSetActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("app",message);

                edit.commit();
                preferences = PreferenceManager.getDefaultSharedPreferences(HeadSetActivity.this);
                edit = preferences.edit();
                edit.putString("launch_mode","otherapp");
                edit.commit();
                Toast.makeText(HeadSetActivity.this,message + "app selected",Toast.LENGTH_LONG).show();
                //  otherapp.setChecked(true);
              /*  if(mode1==null)
                {

                    edit.putString("launch_mode","otherapp");
                    png_launch.setText("Foto Valley Launch is Disabled");
                    btn_app_choose.setText("Other App Launch is Enabled");

                    edit.commit();
                }

                if(mode1.equals("myapp"))
                {
                    edit.putString("launch_mode","otherapp");
                    png_launch.setText("Foto Valley Launch is Disabled");
                    btn_app_choose.setText("Other App Launch is Enabled");

                    edit.commit();

                }
                else if(mode1.equals("otherapp"))
                {
                    edit.putString("launch_mode","myapp");
                    png_launch.setText("Foto Valley Launch is Enabled");
                    btn_app_choose.setText("Other App Launch is Disabled");

                    edit.commit();

                }*/
            }
        }
    }
}
