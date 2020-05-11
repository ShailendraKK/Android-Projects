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
 * Created by shailendra on 10-06-2016.
 */
public class fragment_Portriat extends Fragment {
    private Button outdoor;
    private Button Selected_tag;
    private Button Individuals;
    private Button Candid;
    private Button Kids;
    private Button Fashion;
    private Button Babies;
    private Button Pets;
    private Button Families;
    private Button HeadShots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_porttrait,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        outdoor=(Button)view.findViewById(R.id.btn_outdoor_portrait);
        outdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=outdoor;
                outdoor.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Outdoor";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Individuals=(Button)view.findViewById(R.id.btn_individuals);
        Individuals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Individuals;
                Individuals.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Individuals";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Candid=(Button)view.findViewById(R.id.btn_candid_portrit);
        Candid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagActivity.Tag="Candid";
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Candid;
                Candid.setBackgroundResource(R.drawable.pressed);
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Kids=(Button)view.findViewById(R.id.btn_kids);
        Kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Kids;
                Kids.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Kids";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Fashion=(Button)view.findViewById(R.id.btn_Fashion);
        Fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Fashion;
                Fashion.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Fashion";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Babies=(Button)view.findViewById(R.id.btn_babies);
        Babies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Babies;
                Babies.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Babies";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Pets=(Button)view.findViewById(R.id.btn_pets);
        Pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Pets;
                Pets.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Pets";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Families=(Button)view.findViewById(R.id.btn_families_portrait);
        Families.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Families;
                Families.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Families";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        HeadShots=(Button)view.findViewById(R.id.btn_headshots);
        HeadShots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=HeadShots;
                HeadShots.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="HeadShots";
                TagActivity.continue_button.setEnabled(true);
            }
        });

    }
}
