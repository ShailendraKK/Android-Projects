
package com.example.shailendra.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ItemDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "restaurent";

    // Contacts table name
    public static final String TABLE_NAME = "M_Items";

    // Contacts Table Columns names
    public static final String KEY_ID = "Item_Code";
    public static final String KEY_ITEMTYPE = "ItemType";
    public static final String KEY_ITEMVISIBLE = "Visible";
    public static final String KEY_SNAME = "ShortName";
    public static final String KEY_NAME = "Name";
    public static final String KEY_RESALE = "Resale";
    public static final String KEY_BCODE = "Brand_Code";
    public static final String KEY_GCODE = "Group_Code";
    public static final String KEY_MGCODE = "MenuGroup_Code";
    public static final String KEY_USYMBOL = "Unit_Symbol";
    public static final String KEY_UFNAME = "Unit_FormalName";
    public static final String KEY_UDEC = "Unit_Dec";
    public static final String KEY_SUSYMBOL = "Sub_Unit_Symbol";
    public static final String KEY_SUFNAME = "Sub_Unit_FormalName";
    public static final String KEY_SIZE = "Size";

    public static final String KEY_BPC = "Bottle_Per_Case";
    public static final String KEY_BPEG = "Bottle_Peg";
    public static final String KEY_IC1 = "Item_Consumption_1";
    public static final String KEY_IC2 = "Item_Consumption_2";
    public static final String KEY_SR1 = "SRate1";
    public static final String KEY_SR2 = "SRate2";
    public static final String KEY_SR3 = "SRate3";
    public static final String KEY_SR4 = "SRate4";
    public static final String KEY_SR5 = "SRate5";
    public static final String KEY_SR6 = "SRate6";
    public static final String KEY_SR7 = "SRate7";
    public static final String KEY_SR8 = "SRate8";
    public static final String KEY_SR9 = "SRate9";
    public static final String KEY_SR10 = "SRate10";
    public static final String KEY_SRPARCEL = "SRate_Parcel";
    public static final String KEY_SRBILL = "SRate_Bill";

    public static final String KEY_MRP = "MRP";
    public static final String KEY_APPLICABLE = "Applicable";
    public static final String KEY_APPLY = "Apply";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_T_DISC = "Tax_Disc";

    public static final String KEY_ALLOWS = "Allows";
    public static final String KEY_RO_LEVEL = "ReorderLevel";

    public static final String KEY_CR_LEVEL = "CounterReorderLevel";

    public static final String KEY_OS_1 = "OS_1";

    public static final String KEY_OS_2 = "OS_2";

    public static final String KEY_OS_3 = "OS_3";

    public static final String KEY_OS_4 = "OS_4";

    public static final String KEY_COS_1 = "COS_1";

    public static final String KEY_COS_2 = "COS_2";

    public static final String KEY_COS_3 = "COS_3";

    public static final String KEY_COS_4 = "COS_4";

    public static final String KEY_COS_5 = "COS_5";

    public static final String KEY_COS_6 = "COS_6";

    public static final String KEY_COS_7 = "COS_7";

    public static final String KEY_COS_8 = "COS_8";

    public static final String KEY_COS_9 = "COS_9";

    public static final String KEY_COS_10 = "COS_10";

    public static final String KEY_CS_1 = "CS_1";

    public static final String KEY_CS_2 = "CS_2";
    public static final String KEY_CS_3 = "CS_3";

    public static final String KEY_CS_4 = "CS_4";

    public static final String KEY_CS_5 = "CS_5";

    public static final String KEY_CS_6 = "CS_6";
    public static final String KEY_CS_7 = "CS_7";

    public static final String KEY_CS_8 = "CS_8";

    public static final String KEY_CS_9 = "CS_9";

    public static final String KEY_CS_10 = "CS_10";

    public static final String KEY_PR = "PurchaseRate";

    public static final String KEY_S_ID = "Supplier_ID";

    public static final String KEY_COM_ID = "Com_ID";

    public static final String KEY_LOCK = "LOCK";

    public static final String KEY_VAT_Apply = "VAT_Apply";

    public static final String KEY_DUMMY_1 = "Dummy_1";
    public static final String KEY_DUMMY_2 = "Dummy_2";

    public static final String KEY_DUMMY_3 = "Dummy_3";

    public static final String KEY_DUMMY_4 = "Dummy_4";

    public static final String KEY_DUMMY_5 = "Dummy_5";


    public static final String KEY_DUMMY_6 = "Dummy_6";

    public static final String KEY_DUMMY_7 = "Dummy_7";

    public static final String KEY_DUMMY_8 = "Dummy_8";

    public static final String KEY_DUMMY_9 = "Dummy_9";

    public static final String KEY_DUMMY_10 = "Dummy_10";

    public static final String KEY_COSTING = "Costing_Rs";

    public static final String KEY_GNAME = "GroupName";

    public static final String KEY_SELECTED = "Selected";

    public static final String KEY_STORE_OB = "Store_OB";

    public static final String KEY_SVR = "StockValuationRate";

    public static final String KEY_C_O = "C_O";

    public static final String KEY_C_R = "C_R";

    public static final String KEY_C_S = "C_S";

    public static final String KEY_C_C = "C_C";

    public static final String KEY_GP = "GP";

    public static final String KEY_KPG = "KPG";

    public static final String KEY_POB = "Production_OB";

    public static final String KEY_C2_O = "C2_O";

    public static final String KEY_C2_R = "C2_R";

    public static final String KEY_C2_S = "C2_S";

    public static final String KEY_C2_C = "C2_C";







    public static final String KEY_SUSTOCKMAINTAIN = "Sub_Unit_Stock_Maintan";  //correction to be reported
    public static final String KEY_VISIBLE = "Visible";

    public static final String ColumnNames[]={
           " ","Item_Code","ItemType","Visible","ShortName","Name","Resale","Brand_Code","Group_Code","MenuGroup_Code","Unit_Symbol","Unit_FormalName","Unit_Dec","Sub_Unit_Symbol","Sub_Unit_FormalName","Sub_Unit_Stock_Maintan","Size","Bottle_Per_Case","Bottle_Peg","Item_Consumption_1","Item_Consumption_2","SRate1","SRate2","SRate3","SRate4","SRate5","SRate6","SRate7","SRate8","SRate9","SRate10","SRate_Parcel","SRate_Bill","MRP","Applicable","Apply","Type","Tax_Disc","Allows","ReorderLevel","CounterReorderLevel","OS_1","OS_2","OS_3","OS_4","COS_1","COS_2","COS_3","COS_4","COS_5","COS_6","COS_7","COS_8","COS_9","COS_10","CS_1","CS_2","CS_3","CS_4","CS_5","CS_6","CS_7","CS_8","CS_9","CS_10","PurchaseRate","Supplier_ID","Com_ID","Lock","VAT_Apply","Dummy_1","Dummy_2","Dummy_3","Dummy_4","Dummy_5","Dummy_6","Dummy_7","Dummy_8","Dummy_9","Dummy_10","Costing_Rs","GroupName","Selected","Store_OB","StockValuationRate","C_O","C_R","C_S","C_C","GP","KPG","Production_OB","C2_O","C2_R","C2_S","C2_C"


    };










    private final ArrayList<Items> contact_list = new ArrayList<Items>();

    public ItemDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override






















    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER," + KEY_ITEMTYPE + " TEXT,"
                + KEY_ITEMVISIBLE + " TEXT,"+ KEY_SNAME + " TEXT,"
                + KEY_NAME +" TEXT,"+ KEY_RESALE +" TEXT,"
                + KEY_BCODE +" TEXT,"+ KEY_GCODE +" TEXT,"
                + KEY_MGCODE +" TEXT,"+ KEY_USYMBOL +" TEXT,"
                + KEY_UFNAME +" TEXT,"+ KEY_UDEC +" TEXT,"
                + KEY_SUSYMBOL +" TEXT,"+ KEY_SUFNAME +" TEXT,"
                + KEY_SUSTOCKMAINTAIN +" TEXT,"+ KEY_SIZE +" TEXT,"
                + KEY_BPC +" TEXT,"+ KEY_BPEG +" TEXT,"
                + KEY_IC1 +" TEXT,"+ KEY_IC2 +" TEXT,"
                + KEY_SR1 +" TEXT,"+ KEY_SR2 +" TEXT,"
                + KEY_SR3 +" TEXT,"+ KEY_SR4 +" TEXT,"
                + KEY_SR5 +" TEXT,"+ KEY_SR6 +" TEXT,"
                + KEY_SR7 +" TEXT,"+ KEY_SR8 +" TEXT,"
                + KEY_SR9 +" TEXT,"+ KEY_SR10 +" TEXT,"
                + KEY_SRPARCEL +" TEXT,"+ KEY_SRBILL +" TEXT,"
                + KEY_MRP +" TEXT,"+ KEY_APPLICABLE +" TEXT,"
                + KEY_APPLY +" TEXT,"+ KEY_TYPE +" TEXT,"
                + KEY_T_DISC +" TEXT,"+ KEY_ALLOWS +" TEXT,"
                + KEY_RO_LEVEL +" TEXT,"+ KEY_CR_LEVEL +" TEXT,"
                + KEY_OS_1 +" TEXT,"+ KEY_OS_2 +" TEXT,"
                + KEY_OS_3 +" TEXT,"+ KEY_OS_4 +" TEXT,"
                + KEY_COS_1 +" TEXT,"+ KEY_COS_2 +" TEXT,"
                + KEY_COS_3 +" TEXT,"+ KEY_COS_4 +" TEXT,"
                + KEY_COS_5 +" TEXT,"+ KEY_COS_6 +" TEXT,"
                + KEY_COS_7 +" TEXT,"+ KEY_COS_8 +" TEXT,"
                + KEY_COS_9 +" TEXT,"+ KEY_COS_10 +" TEXT,"
                + KEY_CS_1 +" TEXT,"+ KEY_CS_2 +" TEXT,"
                + KEY_CS_3+" TEXT,"+ KEY_CS_4 +" TEXT,"
                + KEY_CS_5 +" TEXT,"+ KEY_CS_6 +" TEXT,"
                + KEY_CS_7 +" TEXT,"+ KEY_CS_8 +" TEXT,"
                + KEY_CS_9 +" TEXT,"+ KEY_CS_10 +" TEXT,"
                + KEY_PR +" TEXT,"+ KEY_S_ID +" TEXT,"
                + KEY_COM_ID +" TEXT,"+ KEY_LOCK +" TEXT,"
                + KEY_VAT_Apply +" TEXT,"+ KEY_DUMMY_1 +" TEXT,"
                + KEY_DUMMY_2 +" TEXT,"+ KEY_DUMMY_3 +" TEXT,"

                + KEY_DUMMY_4 +" TEXT,"+ KEY_DUMMY_5 +" TEXT,"
                + KEY_DUMMY_6 +" TEXT,"+ KEY_DUMMY_7 +" TEXT,"
                + KEY_DUMMY_8+" TEXT,"+ KEY_DUMMY_9 +" TEXT,"
                + KEY_DUMMY_10 +" TEXT,"+ KEY_COSTING +" TEXT,"
                + KEY_GNAME +" TEXT,"+ KEY_SELECTED +" TEXT,"
                + KEY_STORE_OB +" TEXT,"+ KEY_SVR +" TEXT,"
                + KEY_C_O +" TEXT,"+ KEY_C_R +" TEXT,"
                + KEY_C_S +" TEXT,"+ KEY_C_C +" TEXT,"
                + KEY_GP +" TEXT,"+ KEY_KPG +" TEXT,"+KEY_POB +" TEXT,"
                + KEY_C2_O +" TEXT,"+ KEY_C2_R +" TEXT,"
                + KEY_C2_S +" TEXT,"+ KEY_C2_C +" TEXT"


            + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    public String getName(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT Name FROM M_Items WHERE Item_Code ='" + id+"'" ;
        Cursor  cursor =db.rawQuery(query,null);
        String itemname=null;
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                itemname =  cursor.getString(cursor.getColumnIndex("Name"));
                break;
            }
        }
        return itemname;
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**

     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Items
    public void Add_Contact(Items item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName()); // Items Name
        values.put(KEY_MRP, item.getPhoneNumber()); // Items Phone
    //    values.put(KEY_EMAIL, Items.getEmail()); // Items Email
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
    public void Add_Items(String items[][]) {

        SQLiteDatabase db = this.getWritableDatabase();
      //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //onCreate(db);
        ContentValues values = new ContentValues();
        int rows=items.length;
        int cols=items[1].length;
        for (int i = 1; i<rows ; i++) {
            for(int j=1;j<cols;j++)
            {
                values.put(ColumnNames[j],items[i][j]);

            }
            db.insert(TABLE_NAME, null, values);

        }

             db.close(); // Closing database connection
    }

    // Getting single Items
    Items Get_Contact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME, KEY_MRP}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Items contact = new Items(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return Items
        cursor.close();
        db.close();

        return contact;
    }
    // Getting All Items
    public ArrayList<Items> Get_Refreshed_Contacts(String text) {
        try {
            contact_list.clear();

          String selectQuery;
            if(Pattern.matches("[a-zA-Z]+",text)==false)
            {
                selectQuery = "SELECT * FROM " + TABLE_NAME+" WHERE Item_Code = "+text;

            }
            else
            {
                selectQuery = "SELECT  * FROM " + TABLE_NAME+" WHERE Name LIKE '%"+text+"%'";

            }
            // Select All Query

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Items contact = new Items();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(4));
                    contact.setPhoneNumber(cursor.getString(31));

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
                    contact.setName(cursor.getString(4));
                    contact.setPhoneNumber(cursor.getString(31));

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
                    contact.setName(cursor.getString(4));
                    contact.setPhoneNumber(cursor.getString(31));

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

    // Deleting single contact
    public void Delete_Contact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
*/
    // Getting Items Count
    public int Get_Total_Contacts() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public int droptable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
        return 1;
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
}
