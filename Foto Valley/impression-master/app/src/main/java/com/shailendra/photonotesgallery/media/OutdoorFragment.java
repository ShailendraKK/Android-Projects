package com.shailendra.photonotesgallery.media;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shailendra.photonotesgallery.R;

/**
 * Created by shailendra on 29-05-2016.
 */
public class OutdoorFragment extends Fragment {
    private Button landscape;
    private Button Macro;
    private Button Flowers_and_Plants;
    private Button Spring;
    private Button Summer;
    private Button Fall;
    private Button Winter;
    private Button WildLife;
    private Button People;
    private Button Selected_tag=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.outdoor_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        landscape=(Button)view.findViewById(R.id.btn_landscape);
        landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=landscape;
                landscape.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Landscape";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Macro=(Button)view.findViewById(R.id.btn_macro);
        Macro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Macro;
                Macro.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Macro";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Flowers_and_Plants=(Button)view.findViewById(R.id.btn_Flowers);
        Flowers_and_Plants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagActivity.Tag="Flowers and Plants";
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Flowers_and_Plants;
                Flowers_and_Plants.setBackgroundResource(R.drawable.pressed);
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Spring=(Button)view.findViewById(R.id.btn_Spring);
        Spring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Spring;
                Spring.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Spring";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Summer=(Button)view.findViewById(R.id.btn_summer);
        Summer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Summer;
                Summer.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Summer";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Fall=(Button)view.findViewById(R.id.btn_fall);
        Fall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Fall;
                Fall.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Fall";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Winter=(Button)view.findViewById(R.id.btn_winter);
        Winter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Winter;
                Winter.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Winter";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        WildLife=(Button)view.findViewById(R.id.btn_wildlife);
        WildLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=WildLife;
                WildLife.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="WildLife";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        People=(Button)view.findViewById(R.id.btn_people);
        People.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=People;
                People.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="People";
                TagActivity.continue_button.setEnabled(true);
            }
        });

    }
}
