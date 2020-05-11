package com.example.shailendra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ItemDetials extends AppCompatActivity {

    TextView TableNo;
    TextView ItemName;
    EditText quantity;
    EditText Instruction;
    Button Add;
    Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detials);
        Intent i=getIntent();
        final String Name=i.getStringExtra("Name");
       int Id=i.getIntExtra("item_code",0);

        Id++;
        TableNo=(TextView)findViewById(R.id.table_no);
        ItemName=(TextView)findViewById(R.id.item_name);
        quantity=(EditText)findViewById(R.id.txt_quantity);
        Instruction=(EditText)findViewById(R.id.txt_inst);
        TableNo.setText(MainMenu.Table_no+"");
        ItemName.setText(Name);
        Add=(Button)findViewById(R.id.btn_add);
        Cancel=(Button)findViewById(R.id.btn_cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ItemDetials.this.finish();
            }
        });
        final int finalId = Id;
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ItemDetials.this,OrderDetails.class);
              Bundle bundle=new Bundle();
                bundle.putString("Name",Name);

                bundle.putInt("Id", finalId);
                bundle.putString("quantity",quantity.getText().toString());
                bundle.putBoolean("isnew",true);
                i.putExtras(bundle);
                startActivity(i);
                ItemDetials.this.finish();

            }
        });

    }
}
