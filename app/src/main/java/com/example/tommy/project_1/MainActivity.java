package com.example.tommy.project_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.tommy.project_1.db.Contact;
import com.example.tommy.project_1.db.DB;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        //  put database file
        createDBFile();

        final ListView contactList = (ListView)findViewById(R.id.contact_list);

        //  load database data
        MainPageAdaptor ad = new MainPageAdaptor(MainActivity.this);
        contactList.setAdapter(ad);

        //  set short click listener
        contactList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i("Click ", Long.toString(id));
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("selected_contact",
                        ((Contact)contactList.getItemAtPosition((int)id)).getStringArray());
                startActivity(intent);
            }
        });

        // set long click listener
        contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                Log.i("Long click ", Long.toString(id));
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("删除联系人")
                        .setMessage("确定删除联系人" +
                                ((Contact)contactList.getItemAtPosition((int)id)).getName() + "?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DB db = DB.getInstance(MainActivity.this);
                                db.deleteContact((int)id);
                                ((BaseAdapter)contactList.getAdapter()).notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            }
        });
    }
    private void createDBFile() {
        try {
            OutputStream out = new FileOutputStream(getDatabasePath("contacts.db"));
            InputStream in = getResources().openRawResource(R.raw.contacts);
            byte[] buffer = new byte[1024];
            int read;
            try {
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.flush();
            } catch (IOException e) {
                Log.e("Write db file: ", e.getMessage());
            }
        } catch (FileNotFoundException e) {
            Log.e("Open db file: ", e.getMessage());
        }
    }
}
