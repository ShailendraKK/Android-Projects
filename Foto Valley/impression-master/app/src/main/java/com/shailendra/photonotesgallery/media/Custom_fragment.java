package com.shailendra.photonotesgallery.media;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.shailendra.photonotesgallery.App;
import com.shailendra.photonotesgallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shailendra on 04-06-2016.
 */

public class Custom_fragment extends Fragment {
 private String[] mSimpleListValues=loadArray("tag_list",App.context);
    private HorizontalListView mHlvSimpleList;
    public static HorizontalListView mHlvCustomList;
private long selectedWordId;
    public static int pos=-1;
    private CustomData[] mCustomData = new CustomData[get_no_of_tags()];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.horizgrid,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<get_no_of_tags();i++)
        {
            mCustomData[i]=new CustomData(mSimpleListValues[i]);
        }
     //   mHlvSimpleList = (HorizontalListView) view.findViewById(R.id.hlvSimpleList);
      // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, mSimpleListValues);

        // Assign adapter to the HorizontalListView
        //mHlvSimpleList.setAdapter(adapter);
        mHlvCustomList = (HorizontalListView) view.findViewById(R.id.hlvCustomList);
        final CustomArrayAdapter adapter = new CustomArrayAdapter(App.context, mCustomData);

        // Assign adapter to HorizontalListView
        mHlvCustomList.setAdapter(adapter);
        registerForContextMenu(mHlvCustomList);

    }
    public static String[] loadArray(String arrayName,Context mContext)
    {

        String array[]=new String[get_no_of_tags()];
        for(int i=0;i<array.length;i++)
            array[i]="";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);

        for(int i=0;i<get_no_of_tags();i++)
        {
            array[i]=preferences.getString(arrayName+"_"+i,null);

        }
        return array;

    }
    public static int get_no_of_tags()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);

        return preferences.getInt("no_tags",0);
    }


}
