package com.example.tommy.project_1.db;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.database.sqlite.*;
import android.support.annotation.IntegerRes;
import android.util.Log;

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

    private Cursor currentCursor;

    private static DB m_instance = null;
    public static DB getInstance(Context context) {
        if (context == null) {
            return null;
        }
        if (m_instance == null) {
            m_instance = new DB(context);

        }
        return m_instance;
    }

    private DB(Context context) {
        db_handler = new DatabaseHandler(context);
        this.currentCursor = this.getAllContacts();
    }

    private Cursor getAllContacts() {
        SQLiteDatabase db = db_handler.getReadableDatabase();
        String SQL_query_contact = "select * from " + TABLE_CONTACTS;
        return db.rawQuery(SQL_query_contact, new String[] {});
    }

    public Contact queryContact(int id) {
        currentCursor.moveToPosition(id);
        Log.i("Query", Integer.toString(id));
        Contact contact;
        contact = new Contact(currentCursor.getString(1), currentCursor.getString(2),
                currentCursor.getString(3), currentCursor.getString(4), currentCursor.getString(5));
        return contact;
    }
    public void deleteContact(int id) {
        currentCursor.moveToPosition(id);
        String SQL_delete_contact = "delete from " + TABLE_CONTACTS + " where id == ?";
        SQLiteDatabase db = db_handler.getWritableDatabase();
        db.execSQL(SQL_delete_contact, new String[] {currentCursor.getString(0)});
        currentCursor.close();
        currentCursor = getAllContacts();
    }
    public int getListSize() {
        return currentCursor.getCount();
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
                + KEY_PH_TYPE + " varchar(10), " + KEY_ADDR + " varchar(30), "
                + KEY_COLOR + " char(6)" + ")";
        db.execSQL(SQL_create_table);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}