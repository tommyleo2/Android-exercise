package com.example.tommy.project_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DB db = new DB(MainActivity.this);
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                ((TextView)dialogView.findViewById(R.id.name)).setText(
                        ((TextView)view.findViewById(R.id.name)).getText().toString());
                String temp = ((TextView)view.findViewById(R.id.birthday))
                        .getText().toString();
                String[] date = temp.split("-");
                Log.i("Date", temp);
                ((DatePicker)dialogView.findViewById(R.id.birthday)).updateDate(
                        Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]));
                ((TextView)dialogView.findViewById(R.id.present)).setText(
                        ((TextView)view.findViewById(R.id.present)).getText().toString());

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
