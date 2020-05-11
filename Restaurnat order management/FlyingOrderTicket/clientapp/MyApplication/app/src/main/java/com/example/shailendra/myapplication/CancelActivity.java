package com.example.shailendra.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class CancelActivity extends Activity {
    TextView Tableno;
    Button back;
    ListView Contact_listview;
    OrderDatabaseHandler db;
    ArrayList<Items> contact_data = new ArrayList<Items>();
    Contact_Adapter cAdapter;
    public static ArrayList<Items> contact_list = new ArrayList<Items>();
    String[] orderdetails=new String[100];
    String[] items=new String[100];
    String[] quantity=new String[100];
    int index;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_activity);
        Tableno=(TextView)findViewById(R.id.canceltable_no);
        Tableno.setText(MainMenu.Table_no+"");
        back=(Button)findViewById(R.id.back_cancel);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CancelActivity.this.finish();

            }
        });
        try {
            Contact_listview = (ListView) findViewById(R.id.list_cancel);
            Contact_listview.setItemsCanFocus(false);

            Search_Refreash_Data();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }
            db=new OrderDatabaseHandler(CancelActivity.this);
            try {
                db.createtable();
            }
            catch(Exception e)
            {

            }



    }
    public void Search_Refreash_Data() {
        contact_data.clear();
        db = new OrderDatabaseHandler(this);
        ArrayList<Items> contact_array_from_db = db.Get_Backup_Table();
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

        cAdapter = new Contact_Adapter(CancelActivity.this, R.layout.listview_rownew,contact_data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//stuff that updates ui
                Contact_listview.setAdapter(cAdapter);
                cAdapter.notifyDataSetChanged();
                Toast.makeText(CancelActivity.this,"Data Loaded", Toast.LENGTH_LONG).show();

            }
        });
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
                holder.name = (TextView) row.findViewById(R.id.item_name);
                holder.number = (TextView) row.findViewById(R.id.item_quantity);
                holder.delete = (Button) row.findViewById(R.id.btn_delete);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            user = data.get(position);

            holder.delete.setTag(user.getID());
            holder.name.setText(user.getName());

            holder.number.setText(user.getPhoneNumber());


            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub


                    // show a message while loader is loading
                    Log.v("CancelActivity","Before deleting");
                    LayoutInflater li = LayoutInflater.from(CancelActivity.this);
                    View promptsView = li.inflate(R.layout.prompts, null);
                  final String idtemp=v.getTag().toString();
                  //  final int contactid=Integer.parseInt(idtemp);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            CancelActivity.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);
                    final EditText quantity1=(EditText)promptsView.findViewById(R.id.editTextquantity);
                    // set dialog message
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    Log.v("CancelActivity","On Cancel Click : id value: "+idtemp);
                                    String result = userInput.getText().toString();
                                    String quantity_temp = quantity1.getText().toString();
                                    int itemquantity=Integer.parseInt(db.getquantity(idtemp));
                                    if(Integer.parseInt(quantity_temp)==0){
                                        Toast.makeText(CancelActivity.this,"Quantity cannot be 0",Toast.LENGTH_LONG);


                                    }
                                    else if(Integer.parseInt(quantity_temp)>itemquantity)
                                    {
                                        Log.v("CancelActivity","Quantity to be reduced cannot be greater than order quantity");
                                    }
                                    else {
                                        Log.v("CancelActivity", "after refresh data");
                                        new Thread(new ClientThread(result, idtemp,itemquantity, quantity_temp)).start();
                                        Log.v("CancelActivity", "Completed");

                                        db.Update_Contact(idtemp,(itemquantity-(Integer.parseInt(quantity_temp)))+"");
                                        Log.v("CancelActivity", "after update contact");
                                        if((itemquantity-(Integer.parseInt(quantity_temp)))==0)
                                        {
                                            db.Delete_BAckupContact(Integer.parseInt(idtemp));
                                        }
                                        Search_Refreash_Data();

                                        Log.v("CancelActivity", "Data Refreshed");
                                    }
                                    //     result.setText(userInput.getText());
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }

            });
            return row;

        }

        class UserHolder {
            TextView name;
            TextView number;
            Button delete;
        }
        public void Search_Refreash_Data() {
            contact_data.clear();
            db = new OrderDatabaseHandler(CancelActivity.this);
            ArrayList<Items> contact_array_from_db = db.Get_Backup_Table();
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

            cAdapter = new Contact_Adapter(CancelActivity.this, R.layout.listview_rownew,contact_data);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

//stuff that updates ui
                    Contact_listview.setAdapter(cAdapter);
                    cAdapter.notifyDataSetChanged();
                    Toast.makeText(CancelActivity.this,"Data Loaded", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
    public String getNameFromId(int id)
    {
        ItemDatabaseHandler db = new ItemDatabaseHandler(this);
        String name=db.getNamefromId(id);
        db.close();
        return name;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Search_Refreash_Data();
    }

    public int getIndexfromId(int id)
    {
        for(int i=0;i<contact_list.size();i++)
        {
            if(contact_list.get(i)._id==id)
            {
                return i;
            }
        }
        return -1;
    }

    class ClientThread implements Runnable {
        Socket socket,cook;
        String ip="";
        String resultcode="";
        int quantity;
        int id;
        String idtemp;
        String quantity_temp;
        int itemquanity;
        public ClientThread(String resultcode,String id,int itemquantity,String quantity)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CancelActivity.this);
            ip=preferences.getString("ip",null);
           this.resultcode=resultcode;
            this.idtemp=id;
            this.quantity_temp=quantity;
        this.itemquanity=itemquantity;
        }
        //public static String Time;
        //Socket_Client sc=new Socket_Client();
        public void run() {


            try {
                //   textView.setText("In Thread");

                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr, 6016);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CancelActivity.this);
                String orderid=preferences.getString("orderid",null);
                // Toast.makeText(Main2Activity.this,"Connected",Toast.LENGTH_LONG).show();
                PrintWriter printwriter =  new PrintWriter(socket.getOutputStream(),true);

               printwriter.println("cancel");
             printwriter.println(resultcode);
                printwriter.println(orderid);

                printwriter.println(idtemp);
                Log.v("CancelActivity","before itemname");
                ItemDatabaseHandler idb=new ItemDatabaseHandler(CancelActivity.this);
                String itemname=idb.getName(idtemp);
                Log.v("CancelActivity","name value: "+itemname);
                printwriter.println(itemname);
                printwriter.println(quantity_temp);
                printwriter.println(MainMenu.Table_no);
                String user=preferences.getString("user",null);
                printwriter.println(user);

                printwriter.flush();




                printwriter.close();
                socket.close();
                //db=new OrderDatabaseHandler(CancelActivity.this);
                Log.v("CancelActivity","after db");

            //    OrderDatabaseHandler dBHandler = new OrderDatabaseHandler(
              //          CancelActivity.this.getApplicationContext());
               // dBHandler.Delete_BAckupContact(id);
             //   CancelActivity.this.onResume();



            } catch (UnknownHostException e1) {
//                Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();

                e1.printStackTrace();
                //   textView.setText(e1.toString());

            } catch (IOException e1) {
                e1.printStackTrace();

            }


        }
    }

}
