package com.shailendra.photonotesgallery.media;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeadSetFrament.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeadSetFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeadSetFrament extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    private OnFragmentInteractionListener mListener;
     String mode1="";
    private Button help;
    public HeadSetFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeadSetFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static HeadSetFrament newInstance(String param1, String param2) {
        HeadSetFrament fragment = new HeadSetFrament();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_head_set_frament, container, false);
      //  btn_app_choose = (Button) v.findViewById(R.id.btn_choose_app);
       // appName = (TextView) v.findViewById(R.id.app_name);
       // png_launch=(Button)v.findViewById(R.id.btn_png_launch);
        headset_auto=(Switch)v.findViewById(R.id.switch1);
        foto_valley=(Switch)v.findViewById(R.id.switch2);
        otherapp=(Switch)v.findViewById(R.id.switch3);
        help=(Button)v.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                builder.setTitle("Help");
                builder.setMessage(R.string.help);
                builder.setPositiveButton(android.R.string.ok,null);

                builder.show();
*/
                new MaterialDialog.Builder(getActivity())
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
             SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
                    Intent appIntent = new Intent(getActivity(), Chooser.class);
                    startActivityForResult(appIntent, 1);
                                 }
                else
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor edit = preferences.edit();

                    edit.putString("launch_mode","nootherapp");
                    edit.commit();
                }

            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

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
              /*  HeadSetEnabled.setText("Click to Enable HeadSetDetection");
                png_launch.setEnabled(false);
                btn_app_choose.setEnabled(false);
                png_launch.setText("Foto Valley Launch is Disabled");
                btn_app_choose.setText("Other App Launch is Disabled");*/
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
         /*   HeadSetEnabled.setText("Click to Enable HeadSetDetection");
            png_launch.setEnabled(false);
            btn_app_choose.setEnabled(false);
            png_launch.setText("Foto Valley Launch is Disabled");
            btn_app_choose.setText("Other App Launch is Disabled");
            */
            headset_auto.setChecked(false);
            foto_valley.setEnabled(false);
            otherapp.setEnabled(false);
        }
        /*
        png_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor edit = preferences.edit();

                if(mode==null)
                {
                    edit.putString("launch_mode","myapp");
                    png_launch.setText("Foto Valley Launch is Enabled");
                    btn_app_choose.setText("Other App Launch is Disabled");

                    edit.commit();
                }

               else if(mode.equals("myapp"))
                {
                    edit.putString("launch_mode","otherapp");
                    png_launch.setText("Foto Valley Launch is Disabled");
                    btn_app_choose.setText("Other App Launch is Enabled");

                    edit.commit();

                }
                else if(mode.equals("otherapp"))
                {
                    edit.putString("launch_mode","myapp");
                    png_launch.setText("Foto Valley Launch is Enabled");
                    btn_app_choose.setText("Other App Launch is Disabled");

                    edit.commit();

                }


            }
        });
        btn_app_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(getActivity(), Chooser.class);
                startActivityForResult(appIntent, 1);

            }
        });*/
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            if (requestCode == 1) {
                //Do somethingString
                String message = data.getStringExtra("MESSAGE_package_name");
                   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("app",message);

                edit.commit();
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                edit = preferences.edit();
                edit.putString("launch_mode","otherapp");
                 edit.commit();
                Toast.makeText(getActivity(),message + "app selected",Toast.LENGTH_LONG).show();
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
