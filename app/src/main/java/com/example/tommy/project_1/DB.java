package com.example.tommy.project_1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CursorAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tommy on 11/19/16.
 */

class DB {
    private static final String TABLE_BIRTHDAY = "birthday";
    private static final String KEY_ID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_BIRTHDAY= "birthday";
    static final String KEY_PRESENT = "present";

    private DatabaseHandler databaseHandler;
    private Context context;
    private SQLiteDatabase writableDB, readableDB;

    DB(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
        writableDB = databaseHandler.getWritableDatabase();
        readableDB = databaseHandler.getReadableDatabase();
    }

    Cursor getAll() {
        return readableDB.rawQuery("select * from " + TABLE_BIRTHDAY,
                new String[] {});
    }

    void deleteItemById(long id) {
        writableDB.execSQL(
                "delete from " + TABLE_BIRTHDAY +
                        " where " + KEY_ID + " = " + Long.toString(id),
                new String[] {});
    }

    void updateItemById(long id, int year, int month, int day, String present) {
        String birthday = getDate(year, month, day);
        Log.d("updateById: ", Long.toString(id) + " " + birthday + " " + present);
        writableDB.execSQL(
                "update " + TABLE_BIRTHDAY + " set " +
                        KEY_BIRTHDAY + " = ?, " +
                        KEY_PRESENT + " = ? " +
                        " where " + KEY_ID + " = " + Long.toString(id),
                new String[] {birthday, present});
    }

    void addItem(String name, int year, int month, int day, String present) {
        String birthday = getDate(year, month, day);
        Log.d("addItem: ", name + " " + birthday + " " + present);
        writableDB.execSQL(
                "insert into " + TABLE_BIRTHDAY + " ( " +
                        KEY_NAME + ", " + KEY_BIRTHDAY + ", " + KEY_PRESENT + " ) " +
                        "values (?, ?, ?)",
                new String[] {name, birthday, present});
    }

    boolean hasName(String name) {
        Cursor cursor = readableDB.rawQuery(
                "select _id from " + TABLE_BIRTHDAY +
                        " where " + KEY_NAME + " like ?",
                new String[] {name}
        );
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    private String getDate(int year, int month, int day) {
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }

    private class DatabaseHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "project_8.db";

        DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String SQL_create_table =
                    "create table if not exists " + TABLE_BIRTHDAY + " (" +
                            KEY_ID + " integer primary key autoincrement, " +
                            KEY_NAME + " varchar(20), " +
                            KEY_BIRTHDAY+ " date, " +
                            KEY_PRESENT + " varchar(100) )";
            db.execSQL(SQL_create_table);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }
}

