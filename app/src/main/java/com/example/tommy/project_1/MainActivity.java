package com.example.tommy.project_1;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Storage Permissions variables
    private static final int REQUEST_READ_CONTACTS = 1;
    private static String[] PERMISSIONS_CONTACTS = {
            Manifest.permission.READ_CONTACTS
    };

    //permission method.
    public static void verifyContactsPermissions(Activity activity) {
        // Check if we have read
        int readPermission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_CONTACTS[0]);

        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_CONTACTS,
                    REQUEST_READ_CONTACTS
            );
        }
    }

    DB db;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyContactsPermissions(MainActivity.this);

        db = new DB(MainActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        adapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.content_item,
                db.getAll(),
                new String[] {DB.KEY_NAME, DB.KEY_BIRTHDAY, DB.KEY_PRESENT},
                new int[] {R.id.name, R.id.birthday, R.id.present},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView content_list = (ListView)findViewById(R.id.content_list);
        content_list.setAdapter(adapter);

        content_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, final long id) {
                Log.i("Short click", Long.toString(id));

                final View dialogView = LayoutInflater.
                        from(MainActivity.this).
                        inflate(R.layout.dialog_modify, null);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        })
                        .setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatePicker datePicker = ((DatePicker)dialogView.findViewById(R.id.birthday));
                                db.updateItemById(id,
                                        datePicker.getYear(),
                                        datePicker.getMonth(),
                                        datePicker.getDayOfMonth(),
                                        ((TextView)dialogView.findViewById(R.id.present)).getText().toString());
                                adapter.changeCursor(db.getAll());
                            }
                        });

                String name = ((TextView)view.findViewById(R.id.name)).getText().toString();
                ((TextView)dialogView.findViewById(R.id.name)).setText(name);

                String temp = ((TextView)view.findViewById(R.id.birthday))
                        .getText().toString();
                String[] date = temp.split("-");
                ((DatePicker)dialogView.findViewById(R.id.birthday)).updateDate(
                        Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]));

                ((TextView)dialogView.findViewById(R.id.present)).setText(
                        ((TextView)view.findViewById(R.id.present)).getText().toString());


                TextView phoneView = (TextView)dialogView.findViewById(R.id.phone);

                try (Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                        new String[]{name},
                        null)) {
                    cursor.moveToNext();
                    Log.i("Contacts", Integer.toString(cursor.getCount()));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i("Phone", phone);
                    phoneView.setText(phone);
                    cursor.close();
                } catch (Exception e) {
                    phoneView.setText("无");
                }

                alertDialog
                        .setView(dialogView)
                        .show();
            }
        });

        content_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                Log.i("Long click ", Long.toString(id));
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage("是否删除" + ((TextView)view.findViewById(R.id.name)).getText() + "的记录？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteItemById(id);
                                adapter.changeCursor(db.getAll());
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.changeCursor(db.getAll());
    }
}
