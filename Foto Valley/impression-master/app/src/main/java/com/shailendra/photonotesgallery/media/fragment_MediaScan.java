package com.shailendra.photonotesgallery.media;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_MediaScan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_MediaScan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_MediaScan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button mediaScan;

    private Button filechooser;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragment_MediaScan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_MediaScan.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_MediaScan newInstance(String param1, String param2) {
        fragment_MediaScan fragment = new fragment_MediaScan();
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
        View v=inflater.inflate(R.layout.fragment_fragment__media_scan, container, false);
        mediaScan=(Button)v.findViewById(R.id.btn_mediascan);

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
                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
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
                        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///Removable")));
                        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///Removable/SD")));
                        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///Removable/MicroSD")));
                        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///mnt")));
                        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file:///storage")));


                        error1 = 0;
                    }
                }
                catch(Exception e2)
                {
                                error1=1;
                }
            if(error==0&&error1==0)
            {
              Toast.makeText(getActivity(),"Media Scan Successfully completed",Toast.LENGTH_LONG).show();
            }
                else if(error==0&&error1==1)
            {
                Toast.makeText(getActivity(),"Media Scan Successfully completed : Some files may not have been scanned please add files individually",Toast.LENGTH_LONG).show();


            }
             else{
                Toast.makeText(getActivity(),"Media Scan Failed : Try adding files individually",Toast.LENGTH_LONG).show();


            }
            }
        });
        filechooser=(Button)v.findViewById(R.id.btn_file);
        filechooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FileChooser(getActivity()).setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        if (Build.VERSION.SDK_INT >= 17)
                            MediaScannerConnection.scanFile(getActivity(),
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
                        Toast.makeText(getActivity(),"Media Scan successfull for file "+file,Toast.LENGTH_LONG).show();


                    }
                }).showDialog();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
