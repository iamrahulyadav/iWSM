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

public class AssignmentesDB extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "assignment.db";
    private static final int DatabaseVersion = 1;
    public static final String NAME_OF_TABLE = "assignment";
    public static final String Col_1 = "id";
    public static final String Col_2 = "assinerid";
    public static final String Col_3 = "assignername";
    public static final String Col_4 = "assignercontact";
    public static final String Col_5 = "assignercompany";
    public static final String Col_6 = "assigneraddress";
    public static final String Col_7 = "startdate";
    public static final String Col_8 = "starttime";
    public static final String Col_9 = "salemanname";
    public static final String Col_10 = "salemanecontact";
    public static final String Col_11 = "salemanaddress";
    public static final String Col_12 = "salemanid";




    String CREATE_TABLE_CALL = "CREATE TABLE " + NAME_OF_TABLE + "(" + Col_1 + " integer PRIMARY KEY AUTOINCREMENT," + Col_2 + " TEXT, " + Col_3 + " TEXT, " + Col_4 + " TEXT, " + Col_5 + " TEXT, " + Col_6 + " TEXT, " + Col_7 + " TEXT, " + Col_8 + " TEXT, "
            + Col_9 + " TEXT, " + Col_10 + " TEXT, " + Col_11 + " TEXT, " + Col_12 + " TEXT " + ")";


    public AssignmentesDB(Context context) {
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
    public long insertDatatoDb(AssignmentDbHelper helper) {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Col_1, post.getId());
        values.put(Col_2, helper.getAssignerID());
        values.put(Col_3, helper.getAssignerName());
        values.put(Col_4, helper.getAssignerContact());
        values.put(Col_5, helper.getAssignerCompany());
        values.put(Col_6, helper.getAssignerAddress());
        values.put(Col_7, helper.getStartDate());
        values.put(Col_8, helper.getStartTime());
        values.put(Col_9, helper.getSalemanName());
        values.put(Col_10, helper.getSalemanContact());
        values.put(Col_11, helper.getSalemanAddress());
        values.put(Col_12, helper.getSalemanId());

        //inserting valuse into table columns
        result = db.insert(NAME_OF_TABLE, null, values);
        db.close();
        return result;

    }



    /* fetching records from Database Table*/
    public ArrayList<AssignmentDbHelper> getAllAsignments() {
        String query = "SELECT * FROM " + NAME_OF_TABLE;

        ArrayList<AssignmentDbHelper> addingToList = new ArrayList<AssignmentDbHelper>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                AssignmentDbHelper myHelper = new AssignmentDbHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String assignerID = c.getString(c.getColumnIndex(Col_2));
                String assignerName = c.getString(c.getColumnIndex(Col_3));
                String assignerContact = c.getString(c.getColumnIndex(Col_4));
                String assignerCompany = c.getString(c.getColumnIndex(Col_5));
                String assignerAddress = c.getString(c.getColumnIndex(Col_6));
                String startDate = c.getString(c.getColumnIndex(Col_7));
                String startTime = c.getString(c.getColumnIndex(Col_8));
                String salemanName = c.getString(c.getColumnIndex(Col_9));
                String salemanContact = c.getString(c.getColumnIndex(Col_10));
                String salemanAddress = c.getString(c.getColumnIndex(Col_11));
                String SalemanId = c.getString(c.getColumnIndex(Col_12));

                myHelper.setId(id);
                myHelper.setAssignerID(assignerID);
                myHelper.setAssignerName(assignerName);
                myHelper.setAssignerContact(assignerContact);
                myHelper.setAssignerCompany(assignerCompany);
                myHelper.setAssignerAddress(assignerAddress);
                myHelper.setStartDate(startDate);
                myHelper.setStartTime(startTime);
                myHelper.setSalemanName(salemanName);
                myHelper.setSalemanContact(salemanContact);
                myHelper.setSalemanAddress(salemanAddress);
                myHelper.setSalemanId(SalemanId);

                //adding data to array list
                addingToList.add(myHelper);

            }
        }

        db.close();
        return addingToList;

    }


    //fetch single record
    public ArrayList<AssignmentDbHelper> getSingleRecord(String salemanID) {
        String query = "SELECT * FROM " + NAME_OF_TABLE +" WHERE salemanid="+salemanID;
        Log.e("TAg", "the id is: " + query);
        ArrayList<AssignmentDbHelper> addingToList = new ArrayList<AssignmentDbHelper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                AssignmentDbHelper myHelper = new AssignmentDbHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String assignerID = c.getString(c.getColumnIndex(Col_2));
                String assignerName = c.getString(c.getColumnIndex(Col_3));
                String assignerContact = c.getString(c.getColumnIndex(Col_4));
                String assignerCompany = c.getString(c.getColumnIndex(Col_5));
                String assignerAddress = c.getString(c.getColumnIndex(Col_6));
                String startDate = c.getString(c.getColumnIndex(Col_7));
                String startTime = c.getString(c.getColumnIndex(Col_8));
                String salemanName = c.getString(c.getColumnIndex(Col_9));
                String salemanContact = c.getString(c.getColumnIndex(Col_10));
                String salemanAddress = c.getString(c.getColumnIndex(Col_11));
                String SalemanId = c.getString(c.getColumnIndex(Col_12));
                Log.e("TAg", "the id is isd: " + SalemanId);

                myHelper.setId(id);
                myHelper.setAssignerID(assignerID);
                myHelper.setAssignerName(assignerName);
                myHelper.setAssignerContact(assignerContact);
                myHelper.setAssignerCompany(assignerCompany);
                myHelper.setAssignerAddress(assignerAddress);
                myHelper.setStartDate(startDate);
                myHelper.setStartTime(startTime);
                myHelper.setSalemanName(salemanName);
                myHelper.setSalemanContact(salemanContact);
                myHelper.setSalemanAddress(salemanAddress);
                myHelper.setSalemanId(SalemanId);

                //adding data to array list
                addingToList.add(myHelper);

            }
        }

        db.close();
        return addingToList;

    }

    //dateWise items

    //fetch single record
    public ArrayList<AssignmentDbHelper> getAllCurrentDateRecord(String currentDate) {
        String query = "SELECT * FROM " + NAME_OF_TABLE +" WHERE "+Col_7+"="+"'"+currentDate+"'";
        Log.e("TAg", "the id is: " + query);
        ArrayList<AssignmentDbHelper> addingToList = new ArrayList<AssignmentDbHelper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                AssignmentDbHelper myHelper = new AssignmentDbHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String assignerID = c.getString(c.getColumnIndex(Col_2));
                String assignerName = c.getString(c.getColumnIndex(Col_3));
                String assignerContact = c.getString(c.getColumnIndex(Col_4));
                String assignerCompany = c.getString(c.getColumnIndex(Col_5));
                String assignerAddress = c.getString(c.getColumnIndex(Col_6));
                String startDate = c.getString(c.getColumnIndex(Col_7));
                String startTime = c.getString(c.getColumnIndex(Col_8));
                String salemanName = c.getString(c.getColumnIndex(Col_9));
                String salemanContact = c.getString(c.getColumnIndex(Col_10));
                String salemanAddress = c.getString(c.getColumnIndex(Col_11));
                String SalemanId = c.getString(c.getColumnIndex(Col_12));
                Log.e("TAg", "the id is isd: " + SalemanId);

                myHelper.setId(id);
                myHelper.setAssignerID(assignerID);
                myHelper.setAssignerName(assignerName);
                myHelper.setAssignerContact(assignerContact);
                myHelper.setAssignerCompany(assignerCompany);
                myHelper.setAssignerAddress(assignerAddress);
                myHelper.setStartDate(startDate);
                myHelper.setStartTime(startTime);
                myHelper.setSalemanName(salemanName);
                myHelper.setSalemanContact(salemanContact);
                myHelper.setSalemanAddress(salemanAddress);
                myHelper.setSalemanId(SalemanId);

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

    //

    public int getCount(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + NAME_OF_TABLE;
        return db.rawQuery(query, null).getCount();
    }

}

