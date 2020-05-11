package com.example.shailendra.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainMenu extends Activity {
Button mot;
    Button exit;
    EditText table_no;
    Button cancel;
    Button print;

    public static String Table_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mot=(Button)findViewById(R.id.button3);
        table_no=(EditText)findViewById(R.id.editText2);
        print=(Button)findViewById(R.id.print_btn);
        mot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(table_no.getText().toString()!=null&& Integer.parseInt(table_no.getText().toString())>0) {
                   Table_no=table_no.getText().toString();
                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);

                    SharedPreferences.Editor edit1 = preferences1.edit();
                    edit1.putString("order_type", "new");
                    edit1.commit();
                    Intent i = new Intent(MainMenu.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainMenu.this,"Please Enter Table name ",Toast.LENGTH_LONG).show();
                }
            }
        });
        /*
        view=(Button)findViewById(R.id.view_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);

                SharedPreferences.Editor edit1 = preferences1.edit();
                edit1.putString("order_type", "old");
                edit1.commit();

            }
        });*/
        cancel=(Button)findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainMenu.this,CancelActivity.class);
                startActivity(i);

            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this, epos2_printer.MainActivity.class);
                startActivity(intent);
                      }
        });
        exit=(Button)findViewById(R.id.button4);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu.this.finish();
            }
        });




    }
}
