package com.example.tommy.project_1.db;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.*;

/**
 * Created by tommy on 9/29/16.
 */

public class DB {
    private DatabaseHandler db_handler;
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PH_TYPE = "phone_type";
    private static final String KEY_ADDR = "address";
    private static final String KEY_COLOR = "bg_color";
    public DB(Context context) {
        db_handler = new DatabaseHandler(context);
    }

    public Contact queryContact(int id) {
        Contact contact = null;
        SQLiteDatabase db = db_handler.getReadableDatabase();
        String SQL_query_contact = "select * from contacts where id == ?";
        Cursor cursor = db.rawQuery(SQL_query_contact, new String[] {Integer.toString(id)});
        if (cursor != null) {
            cursor.moveToFirst();
//            System.out.println(cursor.getCount());
            contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5));
            cursor.close();
        }
        return contact;
    }
    public int getListSize() {
        SQLiteDatabase db = db_handler.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select count(*) from " + TABLE_CONTACTS, new String[]{});
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }
}

class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PH_TYPE = "phone_type";
    private static final String KEY_ADDR = "address";
    private static final String KEY_COLOR = "bg_color";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_create_table = "create table if not exists " + TABLE_CONTACTS + "("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_NAME + " varchar(20), " + KEY_PH_NO + " char(11), "
                + KEY_PH_TYPE + " varchar(10), " + KEY_ADDR + " varchar(20), "
                + KEY_COLOR + " char(6)" + ")";
        db.execSQL(SQL_create_table);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}