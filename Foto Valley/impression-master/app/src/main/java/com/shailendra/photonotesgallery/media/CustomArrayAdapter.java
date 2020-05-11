package com.shailendra.photonotesgallery.media;

import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.shailendra.photonotesgallery.App;
import com.shailendra.photonotesgallery.R;

/** An array adapter that knows how to render views when given CustomData classes */
public class CustomArrayAdapter extends ArrayAdapter<CustomData> {
    private LayoutInflater mInflater;
    public static String ItemName;
    public static View previousview;
    public CustomArrayAdapter(Context context, CustomData[] values) {
        super(context, R.layout.mycustomlayout, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.mycustomlayout, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.textView = (Button) convertView.findViewById(R.id.btn_list_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        holder.textView.setText(getItem(position).getText());

        /*
        if(Custom_fragment.pos==position)
            holder.textView.setBackgroundResource(R.drawable.pressed);
        else*/
            holder.textView.setBackgroundResource(R.drawable.mytag);
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomArrayAdapter.previousview!=null)
                CustomArrayAdapter.previousview.setBackgroundResource(R.drawable.mytag);
                CustomArrayAdapter.previousview=v;

                v.setBackgroundResource(R.drawable.pressed);
                TagActivity.Tag=holder.textView.getText().toString();
                TagActivity.continue_button.setEnabled(true);

            }
        });
        // Set the color
//        convertView.setBackgroundColor(getItem(position).getBackgroundColor());

        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public Button textView;
    }
}
