package com.zero.nimo.wearhrm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jer on 4/26/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HeartLogs";
    // Contacts table name
    private static final String TABLE_Heart = "heart";
    // Shops Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_Date = "Date";
    public static final String KEY_HAverage = "Heart_Average";
    public static final String KEY_HTIMER = "Heart_Timer";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Heart + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Date + " TEXT,"
        + KEY_HAverage + " TEXT," + KEY_HTIMER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Heart);
// Creating tables again
        onCreate(db);
    }
    // Adding new shop
    public void addData(HeartLogs data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Date, data.getDate()); // Shop Name
        values.put(KEY_HAverage, data.getAverage()); // Shop Phone Number
        values.put(KEY_HTIMER,data.getTime());//timer

// Inserting Row
        db.insert(TABLE_Heart, null, values);
        db.close(); // Closing database connection
    }
    // Getting one shop
    public HeartLogs getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Heart, new String[]{KEY_ID,
                KEY_Date, KEY_HAverage, KEY_HTIMER}, KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        HeartLogs contact = new HeartLogs(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
// return shop
        return contact;
    }
    // Getting All Shops
    public List<HeartLogs> getAllShops() {
        List<HeartLogs> Hlist = new ArrayList<HeartLogs>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Heart;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HeartLogs datas = new HeartLogs();
                datas.setId(Integer.parseInt(cursor.getString(0)));
                datas.setDate(cursor.getString(1));
                datas.setAverage(cursor.getString(2));
                datas.setTime(cursor.getString(3));
// Adding contact to list
                Hlist.add(datas);
            } while (cursor.moveToNext());
        }

// return contact list
        return Hlist;
    }
    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + TABLE_Heart;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a shop
    public int updateShop(HeartLogs data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Date, data.getDate());
        values.put(KEY_HAverage, data.getAverage());
        values.put(KEY_HTIMER,data.getTime());

// updating row
        return db.update(TABLE_Heart, values, KEY_ID + " = ?",
        new String[]{String.valueOf(data.getId())});
    }

    // Deleting a shop
    public void deleteShop(HeartLogs data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Heart, KEY_ID + " = ?",
        new String[] { String.valueOf(data.getId()) });
        db.close();
    }
}


