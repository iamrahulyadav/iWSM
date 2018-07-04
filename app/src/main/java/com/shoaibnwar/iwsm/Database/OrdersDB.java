package com.shoaibnwar.iwsm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gold on 7/4/2018.
 */

public class OrdersDB extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "orders.db";
    private static final int DatabaseVersion = 1;
    public static final String NAME_OF_TABLE = "orders";
    public static final String Col_1 = "id";
    public static final String Col_2 = "assignerid";
    public static final String Col_3 = "salesmanid";
    public static final String Col_4 = "totalprice";
    public static final String Col_5 = "totalitems";
    public static final String Col_6 = "startDate";
    public static final String Col_7 = "startTime";


    public static final String NAME_OF_TABLE2 = "ordersdetail";
    public static final String Col_Detail_1 = "id";
    public static final String Col_Detail_2 = "orderid";
    public static final String Col_Detail_3 = "itemname";
    public static final String Col_Detail_4 = "itencode";
    public static final String Col_Detail_5 = "unitprice";
    public static final String Col_Detail_6 = "quantities";
    public static final String Col_Detail_7 = "discount";
    public static final String Col_Detail_8 = "totalprice";

    String CREATE_TABLE_CALL = "CREATE TABLE " + NAME_OF_TABLE + "(" + Col_1 + " integer PRIMARY KEY AUTOINCREMENT," + Col_2 + " TEXT, " + Col_3 + " TEXT, " + Col_4 + " TEXT, " + Col_5 + " TEXT, " + Col_6 + " TEXT, " + Col_7 + " TEXT " + ")";
    String CREATE_TABLE_CALL2 = "CREATE TABLE " + NAME_OF_TABLE2 + "(" + Col_Detail_1 + " integer PRIMARY KEY AUTOINCREMENT," + Col_Detail_2 + " TEXT, " + Col_Detail_3 + " TEXT, " + Col_Detail_4 + " TEXT, " + Col_Detail_5 + " TEXT, " + Col_Detail_6 + " TEXT, " + Col_Detail_7 + " TEXT, " +
             Col_Detail_8 + " TEXT " +")";


    public OrdersDB(Context context) {
        super(context, DATABASE_NAME, null, DatabaseVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CALL);
        db.execSQL(CREATE_TABLE_CALL2);
        //db.execSQL(Create_Virtual_Table_Call);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_OF_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Create_Virtual_Table_Call);

    }

    //inserting post in databse
    public long insertDatatoOrderDb(OrdersHelper helper) {
        long result;


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Col_1, post.getId());
        values.put(Col_2, helper.getAssignerId());
        values.put(Col_3, helper.getSalemanId());
        values.put(Col_4, helper.getTotalPrice());
        values.put(Col_5, helper.getTotalItems());
        values.put(Col_6, helper.getStartDate());
        values.put(Col_7, helper.getStartTime());

        //inserting valuse into table columns
        result = db.insert(NAME_OF_TABLE, null, values);
        db.close();
        return result;

    }


    /* fetching records from Database Table*/
    public ArrayList<OrdersHelper> getAllOrders() {
        String query = "SELECT * FROM " + NAME_OF_TABLE;
        ArrayList<OrdersHelper> addingToList = new ArrayList<OrdersHelper>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                OrdersHelper myHelper = new OrdersHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String assignerID = c.getString(c.getColumnIndex(Col_2));
                String salemaneId = c.getString(c.getColumnIndex(Col_3));
                String totalPrice = c.getString(c.getColumnIndex(Col_4));
                String totalItems = c.getString(c.getColumnIndex(Col_5));
                String startDate = c.getString(c.getColumnIndex(Col_6));
                String startTime = c.getString(c.getColumnIndex(Col_7));


                myHelper.setId(id);
                myHelper.setAssignerId(assignerID);
                myHelper.setSalemanId(salemaneId);
                myHelper.setTotalPrice(totalPrice);
                myHelper.setTotalItems(totalItems);
                myHelper.setStartDate(startDate);
                myHelper.setStartTime(startTime);

                //adding data to array list
                addingToList.add(myHelper);

            }
        }

        db.close();
        return addingToList;

    }


    //fetch single record
    public ArrayList<OrdersHelper> getSingleRecord(String salemanID) {
        String query = "SELECT * FROM " + NAME_OF_TABLE +" WHERE salemanid="+salemanID;
        Log.e("TAg", "the id is: " + query);
        ArrayList<OrdersHelper> addingToList = new ArrayList<OrdersHelper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                OrdersHelper myHelper = new OrdersHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String assignerID = c.getString(c.getColumnIndex(Col_2));
                String salementID = c.getString(c.getColumnIndex(Col_3));
                String itemName = c.getString(c.getColumnIndex(Col_4));
                String itemCode = c.getString(c.getColumnIndex(Col_5));
                String unitPrice = c.getString(c.getColumnIndex(Col_6));
                String quantities = c.getString(c.getColumnIndex(Col_7));



                myHelper.setId(id);
                myHelper.setAssignerId(assignerID);
                myHelper.setSalemanId(salemanID);
                myHelper.setItemName(itemName);
                myHelper.setItemCode(itemCode);
                myHelper.setUnitPrice(unitPrice);
                myHelper.setQuantity(quantities);

                //adding data to array list
                addingToList.add(myHelper);

            }
        }

        db.close();
        return addingToList;

    }

    //dateWise items

    //fetch single record
    public ArrayList<OrdersHelper> getAllCurrentDateRecord(String currentDate) {
        String query = "SELECT * FROM " + NAME_OF_TABLE +" WHERE "+Col_7+"="+"'"+currentDate+"'";
        Log.e("TAg", "the id is: " + query);
        ArrayList<OrdersHelper> addingToList = new ArrayList<OrdersHelper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                OrdersHelper myHelper = new OrdersHelper();
                String id = c.getString(c.getColumnIndex(Col_1));
                String assignerID = c.getString(c.getColumnIndex(Col_2));
                String salemanId = c.getString(c.getColumnIndex(Col_3));
                String itemName = c.getString(c.getColumnIndex(Col_4));
                String itemCode = c.getString(c.getColumnIndex(Col_5));
                String unitPrice = c.getString(c.getColumnIndex(Col_6));
                String quantity = c.getString(c.getColumnIndex(Col_7));



                myHelper.setId(id);
                myHelper.setAssignerId(assignerID);
                myHelper.setSalemanId(salemanId);
                myHelper.setItemName(itemName);
                myHelper.setItemCode(itemCode);
                myHelper.setUnitPrice(unitPrice);
                myHelper.setQuantity(quantity);
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

    //inserting post in databse
    public long insertDatatoOrderDetailDb(OrdersHelper helper) {
        long result;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Col_1, post.getId());
        values.put(Col_Detail_2, helper.getOrderId());
        values.put(Col_Detail_3, helper.getItemName());
        values.put(Col_Detail_4, helper.getItemCode());
        values.put(Col_Detail_5, helper.getUnitPrice());
        values.put(Col_Detail_6, helper.getQuantity());
        values.put(Col_Detail_7, helper.getDiscount());
        values.put(Col_Detail_8, helper.getTotalPrice());

        //inserting valuse into table columns
        result = db.insert(NAME_OF_TABLE2, null, values);
        db.close();
        return result;

    }

    //fetch single record
    public ArrayList<OrdersHelper> getAllItemsFrom(String orderID) {
        String query = "SELECT * FROM " + NAME_OF_TABLE2 +" WHERE "+Col_Detail_2+"="+"'"+orderID+"'";
        Log.e("TAg", "the id is: " + query);
        ArrayList<OrdersHelper> addingToList = new ArrayList<OrdersHelper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {

                OrdersHelper myHelper = new OrdersHelper();
                String id = c.getString(c.getColumnIndex(Col_Detail_1));
                String orderId = c.getString(c.getColumnIndex(Col_Detail_2));
                String itemName = c.getString(c.getColumnIndex(Col_Detail_3));
                String itemCode = c.getString(c.getColumnIndex(Col_Detail_4));
                String unitPrice = c.getString(c.getColumnIndex(Col_Detail_5));
                String quantity = c.getString(c.getColumnIndex(Col_Detail_6));
                String discount = c.getString(c.getColumnIndex(Col_Detail_7));
                String totalPrice = c.getString(c.getColumnIndex(Col_Detail_8));

                myHelper.setId(id);
                myHelper.setOrderId(orderId);
                myHelper.setItemName(itemName);
                myHelper.setItemCode(itemCode);
                myHelper.setUnitPrice(unitPrice);
                myHelper.setQuantity(quantity);
                myHelper.setDiscount(discount);
                myHelper.setTotalPrice(totalPrice);
                //adding data to array list
                addingToList.add(myHelper);

            }
        }

        db.close();
        return addingToList;

    }


}

