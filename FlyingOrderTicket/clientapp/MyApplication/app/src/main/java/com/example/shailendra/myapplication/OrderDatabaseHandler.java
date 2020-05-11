
package com.example.shailendra.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class OrderDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "restaurent";

    // Contacts table name
    public static final String TABLE_NAME = "Details";
   public static final String BACKUP_TABLE_NAME="Backup_Details";
    // Contacts Table Columns names
    public static final String KEY_ID = "Item_Code";
    public static final String KEY_ITEMQUANTITY = "ItemQuantity";
    public static final String KEY_ITEMNAME = "Name";
    public static final String KEY_TABLE_NO = "tableno";
    public static final String ColumnNames[]={
            " ","Item_Code","ItemType","Visible","ShortName","Name","Resale","Brand_Code","Group_Code","MenuGroup_Code","Unit_Symbol","Unit_FormalName","Unit_Dec","Sub_Unit_Symbol","Sub_Unit_FormalName","Sub_Unit_Stock_Maintan","Size","Bottle_Per_Case","Bottle_Peg","Item_Consumption_1","Item_Consumption_2","SRate1","SRate2","SRate3","SRate4","SRate5","SRate6","SRate7","SRate8","SRate9","SRate10","SRate_Parcel","SRate_Bill","MRP","Applicable","Apply","Type","Tax_Disc","Allows","ReorderLevel","CounterReorderLevel","OS_1","OS_2","OS_3","OS_4","COS_1","COS_2","COS_3","COS_4","COS_5","COS_6","COS_7","COS_8","COS_9","COS_10","CS_1","CS_2","CS_3","CS_4","CS_5","CS_6","CS_7","CS_8","CS_9","CS_10","PurchaseRate","Supplier_ID","Com_ID","Lock","VAT_Apply","Dummy_1","Dummy_2","Dummy_3","Dummy_4","Dummy_5","Dummy_6","Dummy_7","Dummy_8","Dummy_9","Dummy_10","Costing_Rs","GroupName","Selected","Store_OB","StockValuationRate","C_O","C_R","C_S","C_C","GP","KPG","Production_OB","C2_O","C2_R","C2_S","C2_C"


    };
    private final ArrayList<Items> contact_list = new ArrayList<Items>();

    public OrderDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override





















    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("

                + KEY_ID +" TEXT,"+ KEY_ITEMNAME +" TEXT,"
                + KEY_ITEMQUANTITY +" TEXT,"+ KEY_TABLE_NO +" TEXT"


                + ")";
        Log.v("ODH",CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
      //  db.execSQL(CREATE_CONTACTS_TABLE1);
        Log.v("ODH","Table Created");
    }

    public void onCreate1(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE1 = "CREATE TABLE " + BACKUP_TABLE_NAME + "("

                + KEY_ID +" TEXT,"+ KEY_ITEMNAME +" TEXT,"
                + KEY_ITEMQUANTITY +" TEXT,"+ KEY_TABLE_NO +" TEXT"


                + ")";

        db.execSQL(CREATE_CONTACTS_TABLE1);
        Log.v("ODH","Table Created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BACKUP_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**

     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Items
    public void Add_Contact(Items item,int table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getID()); // Items Name
        values.put(KEY_ITEMNAME, item.getName()); // Items Phone
        values.put(KEY_ITEMQUANTITY,item.getPhoneNumber());

        //    values.put(KEY_EMAIL, Items.getEmail()); // Items Email
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.insert(BACKUP_TABLE_NAME,null,values);
        db.close(); // Closing database connection
    }
    // Getting All Items
    public ArrayList<Items> Get_Contacts() {
        try {
            contact_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Items contact = new Items();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));

                    // Adding contact to list
                    contact_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return contact_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return contact_list;
    }
    public ArrayList<Items> Get_Backup_Table() {
        try {
            contact_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + BACKUP_TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Items contact = new Items();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));

                    // Adding contact to list
                    contact_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return contact_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return contact_list;
    }

    public ArrayList<Items> Get_Contacts_group(int group) {
        try {
            contact_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME+" WHERE MenuGroup_Code = "+group;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Items contact = new Items();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));

                    // Adding contact to list
                    contact_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return contact_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return contact_list;
    }
   /*
        // Updating single contact
        public int Update_Contact(Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());
            values.put(KEY_EMAIL, contact.getEmail());

            // updating row
            return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(contact.getID()) });
        }
*/
        // Deleting single contact
        public void Delete_Contact(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });
            db.delete(BACKUP_TABLE_NAME, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });

            db.close();
        }
    public void Delete_BAckupContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BACKUP_TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });

        db.close();
    }
   Contact Get_Contact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_ITEMNAME, KEY_ITEMQUANTITY,KEY_TABLE_NO}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return Items
        cursor.close();
        db.close();

        return contact;
    }
/*
    public int Update_Contact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEMNAME, contact.getName());
        values.put(KEY_ITEMQUANTITY, contact.getPhoneNumber());
        values.put(KEY_TABLE_NO, contact.getEmail());

        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }*/

    public int Update_Contact(String id,String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE Backup_Details SET ItemQuantity='"+quantity+"' WHERE Item_Code='"+id+"'");
        return 1;
    }
    public String getquantity(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT ItemQuantity FROM Backup_Details WHERE Item_Code ='" + id+"'" ;
        Cursor  cursor =db.rawQuery(query,null);
        String itemname=null;
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                itemname =  cursor.getString(cursor.getColumnIndex("ItemQuantity"));
                break;
            }
        }
        return itemname;
    }

    // Getting Items Count
    public int Get_Total_Contacts() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count= cursor.getCount();
        cursor.close();
        return  count;
    }

    public int droptable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
        return 1;
    }
    public int dropbackuptable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + BACKUP_TABLE_NAME);

        // Create tables again
        onCreate1(db);
        return 1;
    }
    public int createtable()
    {

        SQLiteDatabase db = this.getWritableDatabase();

      //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
        return 1;
    }
    public int droptable1()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        return 1;
    }
    public void replicate()
    {

    }
    public String getNamefromId(int id) {
        String name="";
        try {
            String  selectQuery = "SELECT Name FROM " + TABLE_NAME+" WHERE Item_Code = "+id;
            // Select All Query

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    name=cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return name;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return name;
    }
    public String getNamefromIdBACKUP(int id) {
        String name="";
        try {
            String  selectQuery = "SELECT Name FROM " + BACKUP_TABLE_NAME+" WHERE Item_Code = "+id;
            // Select All Query

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    name=cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return name;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return name;
    }
}
