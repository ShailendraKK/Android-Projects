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
public class GeneralFragment extends Fragment {
    private Button indoor;
    private Button Animals;
    private Button Artistic;
    private Button Family;
    private Button Food;
    private Button Friends;
    private Button Fun;
    private Button Happy;
    private Button Holiday;
    private Button Kids;
    private Button Me;
    private Button Mobile;
    private Button Music;
    private Button Nature;
    private Button People;
    private Button Places;
    private Button Professional;
    private Button Sports;
    private Button Travel;
    private Button Work;
    private Button Selected_tag=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmetn_general,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        indoor=(Button)view.findViewById(R.id.btn_indoor);
        indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=indoor;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Indoor";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Animals=(Button)view.findViewById(R.id.btn_Animals);
        Animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Animals;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Animals";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Artistic=(Button)view.findViewById(R.id.btn_artistic);
        Artistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Artistic;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Artistic";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Family=(Button)view.findViewById(R.id.btn_Family);
        Family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Family;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Family";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Food=(Button)view.findViewById(R.id.btn_food);
        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Food;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Food";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Friends=(Button)view.findViewById(R.id.btn_friends);
        Friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Friends;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Friends";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Fun=(Button)view.findViewById(R.id.Fun);
        Fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Fun;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Fun";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Happy=(Button)view.findViewById(R.id.btn_happy);
        Happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Happy;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Happy";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Holiday=(Button)view.findViewById(R.id.btn_holiday);
        Holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Holiday;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Holiday";
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
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Kids";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Me=(Button)view.findViewById(R.id.btn_me);
       Me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Me;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Me";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Mobile=(Button)view.findViewById(R.id.mobile);
        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {

                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Mobile;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Mobile";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Music=(Button)view.findViewById(R.id.btn_music);
        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Music;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Music";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Nature=(Button)view.findViewById(R.id.nature);
        Nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Nature;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Nature";
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
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="People";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Places=(Button)view.findViewById(R.id.places);
        Places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Places;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Places";
                TagActivity.continue_button.setEnabled(true);

            }
        });
      Professional=(Button)view.findViewById(R.id.btn_professional);
        Professional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Professional;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Professional";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Sports=(Button)view.findViewById(R.id.btn_sports);
        Sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Sports;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Sports";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Travel=(Button)view.findViewById(R.id.btn_travel);
        Travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Travel;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Travel";
                TagActivity.continue_button.setEnabled(true);
            }
        });
        Work=(Button)view.findViewById(R.id.btn_work);
        Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected_tag!=null)
                {
                    Selected_tag.setBackgroundResource(R.drawable.mytag);

                }
                Selected_tag=Work;
                Selected_tag.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag="Work";
                TagActivity.continue_button.setEnabled(true);
            }
        });
    }

}
