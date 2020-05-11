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

/**
 * Created by shailendra on 01-08-2016.
 */
public class ActivitySigning extends Activity{
    Button signin;
    Button exit;
    EditText username;
    EditText password;
    Button authorize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username=(EditText)findViewById(R.id.field_username);
        password=(EditText)findViewById(R.id.field_password);
        signin=(Button)findViewById(R.id.button_sign_in);
        exit=(Button)findViewById(R.id.button_sign_up);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySigning.this.finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((username.getText().toString()!=null)&&(password.getText().toString().toString()!=null))
                {
                    new Thread(new ClientThread(username.getText().toString().trim(),password.getText().toString().trim())).start();

                }
                else
                {
                    Toast.makeText(ActivitySigning.this,"Please Enter Username and password",Toast.LENGTH_LONG).show();
                }
            }
        });
        authorize=(Button)findViewById(R.id.btn_authorize);
        authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivitySigning.this,Main2Activity.class);
                startActivity(intent);


            }
        });

    }
    class ClientThread implements Runnable {
        Socket socket,cook;
        String username="";
        String password="";
        public ClientThread(String a,String b)
        {
            username=a;
            password=b;
        }
        //public static String Time;
        //Socket_Client sc=new Socket_Client();
        public void run() {


            try {
                //   textView.setText("In Thread");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ActivitySigning.this);
                String ip=preferences.getString("ip",null);
                InetAddress serverAddr = InetAddress.getByName(ip);
          //      socket = new Socket(serverAddr, 6017);    //previous socket num

                socket = new Socket(serverAddr, 6016);
                // Toast.makeText(Main2Activity.this,"Connected",Toast.LENGTH_LONG).show();

                PrintWriter printwriter =  new PrintWriter(socket.getOutputStream(),true);

                printwriter.println("login");
                printwriter.println(username);
                printwriter.println(password);
                //    Toast.makeText(Main2Activity.this,"Request Send",Toast.LENGTH_LONG).show();
                //     textView.setText("Request Send");
                printwriter.flush();
                ActivitySigning.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(ActivitySigning.this,"Successful Connected  ..Please Wait",Toast.LENGTH_LONG).show();

                    }
                });

//
//     Toast.makeText(Main2Activity.this,"Reqeust Send",Toast.LENGTH_LONG).show();
               InputStream is=socket.getInputStream();
                 InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);

                 String Message=br.readLine();
                printwriter.close();
//
                socket.close();

                if(Message.equals("success"))
                {
                    ActivitySigning.this.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            Toast.makeText(ActivitySigning.this,"Signing Successful",Toast.LENGTH_LONG).show();

                        }
                    });

                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(ActivitySigning.this);

                    SharedPreferences.Editor edit1 = preferences1.edit();
                    edit1.putString("user", username);
                    edit1.commit();
                    Intent i=new Intent(ActivitySigning.this,MainMenu.class);
                    startActivity(i);
                    ActivitySigning.this.finish();
                }
                else
                {

                    ActivitySigning.this.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            Toast.makeText(ActivitySigning.this,"Incorrect username or password",Toast.LENGTH_LONG).show();

                        }
                    });

                    // Toast.makeText(ActivitySigning.this,"Incorrect Username or Password",Toast.LENGTH_LONG).show();
                }

            } catch (UnknownHostException e1) {
//                Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();

                e1.printStackTrace();
                ActivitySigning.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(ActivitySigning.this,"Connection Problem",Toast.LENGTH_LONG).show();

                    }
                });

                //   textView.setText(e1.toString());

            } catch (IOException e1) {
                //              Toast.makeText(Main2Activity.this,"Exception "+e1.toString(),Toast.LENGTH_LONG).show();

                // textView.setText(e1.toString());
                e1.printStackTrace();
                ActivitySigning.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(ActivitySigning.this,"Connection Problem",Toast.LENGTH_LONG).show();

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
