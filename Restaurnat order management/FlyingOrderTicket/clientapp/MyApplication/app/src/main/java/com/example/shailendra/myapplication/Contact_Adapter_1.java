package com.example.shailendra.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shailendra on 31-07-2016.
 */
public class Contact_Adapter_1 extends ArrayAdapter<Items> {
    Activity activity;
    int layoutResourceId;
    Items user;
    ArrayList<Items> data = new ArrayList<Items>();

    public Contact_Adapter_1(Activity act, int layoutResourceId,
                           ArrayList<Items> data) {
        super(act, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.activity = act;
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.name = (TextView) row.findViewById(R.id.user_name_txt);

            holder.number = (TextView) row.findViewById(R.id.user_mob_txt);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        user = data.get(position);
        holder.edit.setTag(user.getID());
        holder.delete.setTag(user.getID());
        holder.name.setText(user.getName());

        holder.number.setText(user.getPhoneNumber());

        return row;

    }

    class UserHolder {
        TextView name;
        TextView email;
        TextView number;
        Button edit;
        Button delete;
    }

}

