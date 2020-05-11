package com.shailendra.photonotesgallery.media;

import android.Manifest;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shailendra.photonotesgallery.App;
import com.shailendra.photonotesgallery.R;
import com.shailendra.photonotesgallery.photonotes.Final_Main_Activity;
import com.shailendra.photonotesgallery.photonotes.orient;


/**
 * Created by shailendra on 28-05-2016.
 */
public class TagActivity extends AppCompatActivity {
    private Button generalbtn;
    int permission;
    public static String Tag="";
    private static boolean started = false;
    public static Button continue_button;
    private Button outdoorbtn;
    private Button portraitbtn;
    private Button wedding;
    private Button lastbtn;

    private Button selectedbtn=null;
    private Button pngcamera;
    private Button defaultcamera;
    private String action="pngcamera";
    private Button newtag;
    public static int no_of_tags;
        private Button Custome;
    @Override
    public void onPause()
    {

        super.onPause();
    }
    @Override
    public void onResume()
    {
        if(started) {
            stopService(new Intent(TagActivity.this.getApplicationContext(), FileModificationService.class));
            started=false;
        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(TagActivity.this,Final_Main_Activity.class);
        startActivity(i);
        TagActivity.this.finish();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(TagActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(TagActivity.this, Manifest.permission.CAMERA)) {
                    Toast.makeText(TagActivity.this, "This apps need to access camera for its critical functionality. Please appove it", Toast.LENGTH_LONG).show();

                } else {
                    ActivityCompat.requestPermissions(TagActivity.this, new String[]{Manifest.permission.CAMERA}, permission);
                }
            }

        }

        lastbtn=(Button)findViewById(R.id.lasttag);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TagActivity.this);

        final String name1 = preferences.getString("name", null);
        if(name1==null)
        {
        lastbtn.setText("No recent tag");

            lastbtn.setEnabled(false);
        }
        else {
            lastbtn.setText(name1);
            lastbtn.setEnabled(true);

        }
        lastbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastbtn.setBackgroundResource(R.drawable.pressed);
                Tag=name1;
                continue_button.setEnabled(true);

            }
        });
        Custome=(Button)findViewById(R.id.custom);
        Custome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedbtn!=null)
                {
                    selectedbtn.setBackgroundResource(R.drawable.mytag);

                }
                selectedbtn=Custome;

                Custome.setBackgroundResource(R.drawable.pressed);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.place_holder,new Custom_fragment());
                ft.commit();

            }
        });

            generalbtn=(Button)findViewById(R.id.btn_general);
       generalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedbtn!=null)
                {
                  selectedbtn.setBackgroundResource(R.drawable.mytag);

                }
                selectedbtn=generalbtn;

                generalbtn.setBackgroundResource(R.drawable.pressed);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.place_holder,new GeneralFragment());
                ft.commit();

            }
        });
        continue_button=(Button)findViewById(R.id.continue_btn);
        continue_button.setEnabled(false);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action.equals("pngcamera")) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);

                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("name", Tag);
                    edit.commit();
                    Intent i = new Intent(TagActivity.this, orient.class);
                    i.putExtra("name", Tag);
                    startActivity(i);
                }
                else if(action.equals("defaultcamera"))
                {
                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(App.context);

                    SharedPreferences.Editor edit1 = preferences1.edit();
                    edit1.putString("name", Tag);
                    edit1.commit();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TagActivity.this);

                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("name1", Tag);
                    edit.commit();

                    Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startService(new Intent(TagActivity.this.getApplicationContext(), FileModificationService.class));
                started=true;
                    //      ob=new MyFileObserver(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
             //     ob.startWatching();
                    try
                    {
                        PackageManager pm=getPackageManager();
                        final ResolveInfo mInfo=pm.resolveActivity(i,0);
                        Intent intent=new Intent();
                        intent.setComponent(new ComponentName(mInfo.activityInfo.packageName,mInfo.activityInfo.name));
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        startActivity(intent);
                    }
                    catch(Exception ex)
                    {
        Log.i("Camera","Unable to launch camera"+ex);
                    }
                }

            }
        });
        outdoorbtn=(Button)findViewById(R.id.btn_Outdoor);
        outdoorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedbtn!=null)
                {
                    selectedbtn.setBackgroundResource(R.drawable.mytag);

                }
                selectedbtn=outdoorbtn;
                outdoorbtn.setBackgroundResource(R.drawable.pressed);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.place_holder,new OutdoorFragment());
                ft.commit();
            }
        });
        portraitbtn=(Button)findViewById(R.id.portrait);
        portraitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedbtn!=null)
                {
                    selectedbtn.setBackgroundResource(R.drawable.mytag);

                }
                selectedbtn=portraitbtn;
                selectedbtn.setBackgroundResource(R.drawable.pressed);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.place_holder,new fragment_Portriat());
                ft.commit();
            }
        });

        wedding=(Button)findViewById(R.id.wedding);
        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedbtn!=null)
                {
                    selectedbtn.setBackgroundResource(R.drawable.mytag);

                }
                selectedbtn=wedding;
                wedding.setBackgroundResource(R.drawable.pressed);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.place_holder,new fragment_Wedding());
                ft.commit();
            }
        });
        pngcamera=(Button)findViewById(R.id.btn_pv_camera);
        pngcamera.setBackgroundResource(R.drawable.pressed);
        pngcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultcamera.setBackgroundResource(R.drawable.mytag);
         pngcamera.setBackgroundResource(R.drawable.pressed);
            action="pngcamera";
            }
        });
    defaultcamera=(Button)findViewById(R.id.btn_default_camera);
        defaultcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultcamera.setBackgroundResource(R.drawable.pressed);
                pngcamera.setBackgroundResource(R.drawable.mytag);
            action="defaultcamera";
            }
        });

        newtag=(Button)findViewById(R.id.newtag);


        newtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this, R.style.MyAlertDialogStyle);
                builder.setTitle("Enter the Tag");
                builder.setMessage("Pleas Enter the name for new tag");
                View name_view = getLayoutInflater().inflate(R.layout.name1, null);

                final EditText notes_name = (EditText) name_view.findViewById(R.id.notes_name);


                builder.setView(name_view);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                      Tag = notes_name.getText().toString();
                        continue_button.setEnabled(true);
                        newtag.setText(Tag);
                        String[] tags=loadArray("tag_list",App.context);

                        tags[get_no_of_tags()]=Tag;
                        no_of_tags=get_no_of_tags()+1;
                        set_no_of_tags(no_of_tags);


                        savearray(tags,"tag_list",App.context);


                      }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

            }
        });
    }
  public static boolean savearray(String[] array,String arrayname,Context mContext)
  {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);
      SharedPreferences.Editor editor=preferences.edit();

      for(int i=0;i<get_no_of_tags();i++)
      {
          editor.putString(arrayname+"_"+i,array[i]);

      }
      return editor.commit();


  }
    public static String[] loadArray(String arrayName,Context mContext)
    {

        String array[]=new String[get_no_of_tags()+1];
       for(int i=0;i<array.length;i++)
           array[i]="";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);

        for(int i=0;i<get_no_of_tags();i++)
        {
            array[i]=preferences.getString(arrayName+"_"+i,null);

        }
        return array;

    }
   public static int get_no_of_tags()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);

        return preferences.getInt("no_tags",0);
    }
    public static void set_no_of_tags(int size)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.context);

        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("no_tags",size);
        editor.commit();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.hlvCustomList)
        {
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
            String[] menuItems={"Delete","Edit"};
            for(int i=0;i<menuItems.length;i++)
            {
                menu.add(Menu.NONE,i,i,menuItems[i]);
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex=item.getItemId();
        String[] menuItems={"Delete","Edit"};
        String menuItemName=menuItems[menuItemIndex];

        if(menuItemName.equals("Delete"))
        {              String[] temparray=loadArray("tag_list",App.context);

            int temp=get_no_of_tags();
            for(int i=0;i<temp;i++)
            {
                if(temparray[i].equals(TagActivity.Tag))
                {

                    for(int j=i;j<(temp-1);j++)
                    {
                        temparray[j]=temparray[j+1];
                    }

                    break;
                }
            }
            TagActivity.savearray(temparray,"tag_list",App.context);


        }
        else if(menuItemName.equals("Edit"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("Enter the Tag");
            builder.setMessage("Pleas Enter the nnew name for tag");
            View name_view =getLayoutInflater().inflate(R.layout.name1, null);

            final EditText notes_name = (EditText) name_view.findViewById(R.id.notes_name);


            builder.setView(name_view);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String[] temparray=loadArray("tag_list",App.context);
                    for(int i=0;i<get_no_of_tags();i++)
                    {
                        if(temparray[i].equals(TagActivity.Tag))
                        {
                            temparray[i]=notes_name.getText().toString();
                            TagActivity.savearray(temparray,"tag_list",App.context);
                            break;
                        }
                    }







                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }
        return super.onContextItemSelected(item);
    }
}