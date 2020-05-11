package com.example.shailendra.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main_Screen extends ActionBarActivity {
    Button add_btn;
    ListView Contact_listview;
    ArrayList<Items> contact_data = new ArrayList<Items>();
    Contact_Adapter cAdapter;
    ItemDatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	try {
	    Contact_listview = (ListView) findViewById(R.id.list);
	    Contact_listview.setItemsCanFocus(false);
		Contact_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle=new Bundle();

			};
		});


		Set_Referash_Data();

	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("some error", "" + e);
	}

    }
	public void Search_Refreash_Data(String text) {
		contact_data.clear();
		db = new ItemDatabaseHandler(this);
		ArrayList<Items> contact_array_from_db = db.Get_Refreshed_Contacts(text);

		for (int i = 0; i < contact_array_from_db.size(); i++) {

			int tidno = contact_array_from_db.get(i).getID();
			String name = contact_array_from_db.get(i).getName();
			String mobile = contact_array_from_db.get(i).getPhoneNumber();

			Items cnt = new Items();
			cnt.setID(tidno);
			cnt.setName(name);

			cnt.setPhoneNumber(mobile);

			contact_data.add(cnt);
		}
		db.close();
		cAdapter = new Contact_Adapter(Main_Screen.this, R.layout.listview_row,
				contact_data);
		Contact_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
		Toast.makeText(this,"Data Loaded", Toast.LENGTH_LONG).show();
	}

    public void Set_Referash_Data() {
	contact_data.clear();
	db = new ItemDatabaseHandler(this);
	ArrayList<Items> contact_array_from_db = db.Get_Contacts();

	for (int i = 0; i < contact_array_from_db.size(); i++) {

	    int tidno = contact_array_from_db.get(i).getID();
	    String name = contact_array_from_db.get(i).getName();
	    String mobile = contact_array_from_db.get(i).getPhoneNumber();

	    Items cnt = new Items();
	    cnt.setID(tidno);
	    cnt.setName(name);

	    cnt.setPhoneNumber(mobile);

	    contact_data.add(cnt);
	}
	db.close();
	cAdapter = new Contact_Adapter(Main_Screen.this, R.layout.listview_row,
		contact_data);
	Contact_listview.setAdapter(cAdapter);
	cAdapter.notifyDataSetChanged();
		Toast.makeText(this,"Data Loaded", Toast.LENGTH_LONG).show();
    }

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Set_Referash_Data();

    }



	public class Contact_Adapter extends ArrayAdapter<Items> {
	Activity activity;
	int layoutResourceId;
	Items user;
	ArrayList<Items> data = new ArrayList<Items>();

	public Contact_Adapter(Activity act, int layoutResourceId,
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
	    holder.name.setText(user.getName());

	    holder.number.setText(user.getPhoneNumber());


	    return row;

	}

	class 	UserHolder {
	    TextView name;
	    TextView number;

	}

    }
@Override
	public boolean onCreateOptionsMenu(Menu menu)
{
	// Associate searchable configuration with the SearchView
	MenuInflater inflater = getMenuInflater();
	// Inflate menu to add items to action bar if it is present.
	inflater.inflate(R.menu.menu_main, menu);

	SearchManager searchManager =
			(SearchManager) getSystemService(Context.SEARCH_SERVICE);
	android.support.v7.widget.SearchView searchView =
			(android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
	searchView.setSearchableInfo(
			searchManager.getSearchableInfo(getComponentName()));

	return true;
}
}
