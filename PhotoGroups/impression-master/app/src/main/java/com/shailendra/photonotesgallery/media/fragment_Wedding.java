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
public class fragment_Wedding extends Fragment{
    private Button Bride;
    private Button Selected_tag;
    private Button BlackandWhite;
    private Button Candid;
    private Button Groom;
    private Button Pre_Ceremony;
    private Button Ceremony;
    private Button Family;
    private Button Reception;
    private Button HeadShots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_wedding,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bride=(Button)view.findViewById(R.id.btn_bride);
        Bride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Bride;
                Bride.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Bride";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        BlackandWhite=(Button)view.findViewById(R.id.btn_black_and_white);
        BlackandWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=BlackandWhite;
                BlackandWhite.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Black and White";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Candid=(Button)view.findViewById(R.id.btn_Candid_wedding);
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
        Groom=(Button)view.findViewById(R.id.btn_groom);
        Groom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Groom;
                Groom.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Groom";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Pre_Ceremony=(Button)view.findViewById(R.id.btn_preceremony);
        Pre_Ceremony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Pre_Ceremony;
                Pre_Ceremony.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Pre-Ceremony";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Ceremony=(Button)view.findViewById(R.id.btn_ceremony);
        Ceremony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Ceremony;
                Ceremony.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Ceremony";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Family=(Button)view.findViewById(R.id.btn_Family_wedding);
        Family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Family;
                Family.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Family";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Reception=(Button)view.findViewById(R.id.btn_reception);
        Reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Reception;
                Reception.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Reception";
                TagActivity.continue_button.setEnabled(true);
            }
        });

    }
}
