package com.example.shailendra.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main2Activity extends Activity {
public static TextView textView;
    private Button btn;
    private Button viewRecord;
    ItemDatabaseHandler db;
    public static EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

            btn=(Button)findViewById(R.id.button);
        edit=(EditText)findViewById(R.id.editText);
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this);
       String ip1= preferences1.getString("ip",null);
        if(ip1!=null)
        {
            edit.setText(ip1);
        }
        viewRecord=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new ClientThread(edit.getText().toString().trim())).start();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this);

                SharedPreferences.Editor edit1 = preferences.edit();
                edit1.putString("ip", edit.getText().toString());
                edit1.commit();

            }
        });
        viewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Main2Activity.this.finish();

            }
        });
        db = new ItemDatabaseHandler(this);
       // Toast.makeText(Main2Activity.this,"Starting Thread",Toast.LENGTH_LONG).show();



    }
    class ClientThread implements Runnable {
        Socket socket,cook;
        String ip="";
        public ClientThread(String a)
        {
         ip=a;
        }
        //public static String Time;
        //Socket_Client sc=new Socket_Client();
        public void run() {


            try {
         //   textView.setText("In Thread");
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr, 6016);

                // Toast.makeText(Main2Activity.this,"Connected",Toast.LENGTH_LONG).show();

                PrintWriter printwriter =  new PrintWriter(socket.getOutputStream(),true);


                printwriter.println("sink");
            //    Toast.makeText(Main2Activity.this,"Request Send",Toast.LENGTH_LONG).show();
           //     textView.setText("Request Send");
                printwriter.flush();
//                  Toast.makeText(Main2Activity.this,"Reqeust Send",Toast.LENGTH_LONG).show();
                Main2Activity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this,"Successfully connected...Please Wait..",Toast.LENGTH_LONG).show();

                    }
                });

                try {

         //            InputStream is=socket.getInputStream();
           //          InputStreamReader isr=new InputStreamReader(is);
             //       BufferedReader br=new BufferedReader(isr);


               //            int no_rows=br.read();

                    //Log.v("MainActivity2", row_String);
                    //int no_rows=Integer.parseInt(row_String);
                    ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                        String[][] items = (String[][]) ois.readObject();
                    int temp= db.droptable();
                    if(temp==1) {
                        db.Add_Items(items);

                        printwriter.close();

                        socket.close();

                        Main2Activity.this.runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                Toast.makeText(Main2Activity.this,"Data Sink successful",Toast.LENGTH_LONG).show();

                            }
                        });
                        Main2Activity.this.finish();

                    }
                    else
                    {
                        Toast.makeText(Main2Activity.this,"Connection problem",Toast.LENGTH_LONG).show();
                    }

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

                } catch (ClassNotFoundException e) {
       //             e.printStackTrace();
                    Main2Activity.this.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            Toast.makeText(Main2Activity.this,"Connection problem",Toast.LENGTH_LONG).show();

                        }
                    });

                }

                // InputStream is=socket.getInputStream();
               // InputStreamReader isr=new InputStreamReader(is);
                //BufferedReader br=new BufferedReader(isr);

              //  Message=br.readLine();
              //  timeconverted=Integer.parseInt(Message);


		 			   /* Socket_Client sc=new Socket_Client();
		 			    sc.StartTimer();*/



            } catch (UnknownHostException e1) {
//                Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();
                Main2Activity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this,"Connection problem",Toast.LENGTH_LONG).show();

                    }
                });
                //e1.printStackTrace();
             //   textView.setText(e1.toString());

            } catch (IOException e1) {
  //              Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();
                Main2Activity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this,"Connection problem",Toast.LENGTH_LONG).show();

                    }
                });
               // textView.setText(e1.toString());
                //e1.printStackTrace();

            }
            catch(Exception e)
            {
                Main2Activity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this,"Connection problem",Toast.LENGTH_LONG).show();

                    }
                });
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
