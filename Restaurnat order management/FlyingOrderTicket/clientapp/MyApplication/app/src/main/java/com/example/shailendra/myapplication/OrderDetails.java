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

public class OrderDetails extends Activity {
    TextView Tableno;
    EditText Quantity;
    EditText Item_Code;
    Button add;
    Button send;
    Button back;
    Button itemlist;
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
        setContentView(R.layout.activity_order_details);
         Tableno=(TextView)findViewById(R.id.table_no);
        Tableno.setText(MainMenu.Table_no+"");
        Quantity=(EditText)findViewById(R.id.quantity);
        Item_Code=(EditText)findViewById(R.id.item_code);
        add=(Button)findViewById(R.id.button_add);
        send=(Button)findViewById(R.id.send);
        back=(Button)findViewById(R.id.back);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        itemlist=(Button)findViewById(R.id.item_list);
        itemlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OrderDetails.this,MainActivity.class);
                startActivity(i);
                OrderDetails.this.finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            OrderDetails.this.finish();
                //    OrderDetails.this.onSaveInstanceState(savedInstanceState);
            //  savedInstanceState.putParcelableArrayList("order",contact_list);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
               // items[index]=Item_Code.getText().toString();
                // quantity[index]=Quantity.getText().toString();
               // orderdetails[index]=quantity[index]+"  :-  "+items[index];
               db=new OrderDatabaseHandler(OrderDetails.this);
             try {
                 db.createtable();
             }
             catch(Exception e)
             {

             }
                Items contact = new Items();
                int id=Integer.parseInt(Item_Code.getText().toString());
                contact.setID(id);
                contact.setName(getNameFromId(id));
                contact.setPhoneNumber(Quantity.getText().toString());
                db.Add_Contact(contact,Integer.parseInt(MainMenu.Table_no));
                // Adding contact to list
                contact_list.add(contact);
                Log.v("Order", "Addedd");
                Search_Refreash_Data();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new ClientThread()).start();

            }
        });
        try {
            Contact_listview = (ListView) findViewById(R.id.list_order);
            Contact_listview.setItemsCanFocus(false);

            Search_Refreash_Data();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }
        if(bundle!=null&&bundle.getBoolean("isnew",false))
        {
            String name=bundle.getString("Name",null);
            int id=bundle.getInt("Id",0);
            String quantity=bundle.getString("quantity",null);
            db=new OrderDatabaseHandler(OrderDetails.this);
            try {
                db.createtable();

            }
            catch(Exception e)
            {
           Log.v("OrderDetials",e.toString());
            }
            try {
                db.dropbackuptable();

            }
            catch(Exception e)
            {
                Log.v("OrderDetials",e.toString());
            }

            Items contact = new Items();

            contact.setID(id);
            contact.setName(name);
            contact.setPhoneNumber(quantity);
            db.Add_Contact(contact, Integer.parseInt(MainMenu.Table_no));
            // Adding contact to list
            contact_list.add(contact);
            Log.v("Order", "Addedd");
            Search_Refreash_Data();
        }
        else
        {
            db=new OrderDatabaseHandler(OrderDetails.this);
            try {
                db.createtable();

            }
            catch(Exception e)
            {
                Log.v("OrderDetials",e.toString());
            }
            try {
                db.dropbackuptable();

            }
            catch(Exception e)
            {
                Log.v("OrderDetials",e.toString());
            }

        }


    }
    public void Search_Refreash_Data() {
        contact_data.clear();
        db = new OrderDatabaseHandler(this);
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

        cAdapter = new Contact_Adapter(OrderDetails.this, R.layout.listview_rownew,contact_data);
        Contact_listview.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Data Loaded", Toast.LENGTH_LONG).show();
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

                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete ");
                    final int user_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    OrderDatabaseHandler dBHandler = new OrderDatabaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.Delete_Contact(user_id);
                                    OrderDetails.this.onResume();



                                   // contact_list.remove(getIndexfromId(user_id));

                                }
                            });
                    adb.show();
                }

            });
            return row;

        }

        class UserHolder {
            TextView name;
            TextView number;
            Button delete;
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
        public ClientThread()
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OrderDetails.this);
            ip=preferences.getString("ip",null);
        }
        //public static String Time;
        //Socket_Client sc=new Socket_Client();
        public void run() {


            try {
                //   textView.setText("In Thread");
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr, 6016);

                // Toast.makeText(Main2Activity.this,"Connected",Toast.LENGTH_LONG).show();
                OrderDatabaseHandler db2=new OrderDatabaseHandler(OrderDetails.this);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OrderDetails.this);

                PrintWriter printwriter =  new PrintWriter(socket.getOutputStream(),true);
                printwriter.println("order");
                 String otype=preferences.getString("order_type","new");
                printwriter.println(otype);
                printwriter.println(db2.Get_Total_Contacts());
                db2.close();
                printwriter.flush();
                String user=preferences.getString("user",null);
                for(int i=0;i<contact_list.size();i++)
                {
                    printwriter.println(contact_list.get(i)._id);
                    printwriter.println(contact_list.get(i)._name);
                    printwriter.println(contact_list.get(i)._phone_number);
                    printwriter.println(MainMenu.Table_no);
                    printwriter.println(user);
                    printwriter.flush();
                }

                try {


                        //contact_list.clear();
                    InputStream is=socket.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader br=new BufferedReader(isr);

                    String id=br.readLine();
                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(OrderDetails.this);

                    SharedPreferences.Editor edit1 = preferences1.edit();
                    edit1.putString("orderid",id);
                    edit1.commit();
                    OrderDatabaseHandler db1=new OrderDatabaseHandler(OrderDetails.this);
                    db1.droptable();

                    contact_list.clear();
                    //Log.v("MainActivity2", row_String);
                    //int no_rows=Integer.parseInt(row_String);

                    /*    String data = "";
                    int rows=items.length;
                    int cols=items[1].length;
                        for (int i = 1; i<rows ; i++) {
                            for(int j=1;j<cols;j++)
                            data += items[i][j];
                            Log.v("MainActivity2", data);
                            data="";
                            //       textView.setText(data);
                        }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // InputStream is=socket.getInputStream();
                // InputStreamReader isr=new InputStreamReader(is);
                //BufferedReader br=new BufferedReader(isr);

                //  Message=br.readLine();
                //  timeconverted=Integer.parseInt(Message);

                printwriter.close();
//
                socket.close();
                OrderDetails.this.finish();
                Intent i=new Intent(OrderDetails.this,MainMenu.class);
                startActivity(i);

		 			   /* Socket_Client sc=new Socket_Client();
		 			    sc.StartTimer();*/



            } catch (UnknownHostException e1) {
//                Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();

                e1.printStackTrace();
                //   textView.setText(e1.toString());

            } catch (IOException e1) {
                //              Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();

                // textView.setText(e1.toString());
                e1.printStackTrace();

            }
		          /*  try {
		 			Thread.currentThread();
		 			Thread.sleep(1000);
		 		} catch (InterruptedException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		}
		 */


        }
    }

}
