package com.shoaibnwar.iwsm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gold on 6/29/2018.
 */

public class ContactDatabase extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "contacts.db";
    private static final int DatabaseVersion = 1;
    public static final String NAME_OF_TABLE = "contacttable";
    public static final String Col_1 = "id";
    public static final String Col_2 = "contactname";
    public static final String Col_3 = "contactnumber";
    public static final String Col_4 = "contactaddress";
    public static final String Col_5 = "contactlat";
    public static final String Col_6 = "contactlng";
    public static final String Col_7 = "contactbookstatus";



    String CREATE_TABLE_CALL = "CREATE TABLE " + NAME_OF_TABLE + "(" + Col_1 + " integer PRIMARY KEY AUTOINCREMENT," + Col_2 + " TEXT, " + Col_3 + " TEXT, " + Col_4 + " TEXT, " + Col_5 + " TEXT, " + Col_6 + " TEXT, " + Col_7 + " TEXT " + ")";


   public ContactDatabase(Context context) {
        super(context, DATABASE_NAME, null, DatabaseVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CALL);
        //db.execSQL(Create_Virtual_Table_Call);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_OF_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Create_Virtual_Table_Call);

    }

    //inserting post in databse
    public long insertDatatoDb(ContactDbHelper post) {
        long result;



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Col_1, post.getId());
        values.put(Col_2, post.getContactName());
        values.put(Col_3, post.getContactPhone());
        values.put(Col_4, post.getContactAddress());
        values.put(Col_5, post.getContactLat());
        values.put(Col_6, post.getContactLng());
        values.put(Col_7, post.getContactStatus());

        Log.e("TAg", "name name name  " + post.getContactName());
        //inserting valuse into table columns
        result = db.insert(NAME_OF_TABLE, null, values);
        db.close();
        return result;

    }



    /* fetching records from Database Table*/
    public ArrayList<ContactDbHelper> getContacts() {
        String query = "SELECT * FROM " + NAME_OF_TABLE;
        ArrayList<ContactDbHelper> addingToList = new ArrayList<ContactDbHelper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ContactDbHelper myHelper = new ContactDbHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String contactName = c.getString(c.getColumnIndex(Col_2));
                String contactPhone = c.getString(c.getColumnIndex(Col_3));
                String contactAddress = c.getString(c.getColumnIndex(Col_4));
                String contactLat = c.getString(c.getColumnIndex(Col_5));
                String contactLng = c.getString(c.getColumnIndex(Col_6));
                String contactStatus = c.getString(c.getColumnIndex(Col_7));



                myHelper.setId(id);
                myHelper.setContactName(contactName);
                myHelper.setContactPhone(contactPhone);
                myHelper.setContactAddress(contactAddress);
                myHelper.setContactLat(contactLat);
                myHelper.setContactLng(contactLng);
                myHelper.setContactStatus(contactStatus);
                //adding data to array list
                addingToList.add(myHelper);

            }
        }

        db.close();
        return addingToList;

    }

    //Updatating post
    public boolean updateTable(int id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_7,status);
        db.update(NAME_OF_TABLE, contentValues, "id = ?", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }

    //deleting post
    public boolean deleteFromTable(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NAME_OF_TABLE, Col_1 + "=" + rowId, null);
        db.close();

        return true;

    }

    public int getCount(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + NAME_OF_TABLE;
        return db.rawQuery(query, null).getCount();
    }

}

