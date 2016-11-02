package com.example.tommy.project_1;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import layout.FruitWidget;

/**
 * Created by tommy on 10/21/16.
 */

public class FruitListActivity extends AppCompatActivity {
    String[] fruitsName = {"Apple", "Banana", "Cherry", "Coco", "Kiwi", "Orange", "Pear",
            "StrawBerry", "Watermelon"};
    int[] picId = {R.mipmap.apple, R.mipmap.banana, R.mipmap.cherry, R.mipmap.coco, R.mipmap.kiwi,
            R.mipmap.orange, R.mipmap.pear, R.mipmap.strawberry, R.mipmap.watermelon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_list);

        ListView listView = (ListView)findViewById(R.id.fruit_list);
        List<Map<String, Object>> bindFruits = new ArrayList<>();
        for (int i = 0; i < fruitsName.length; i++) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", fruitsName[i]);
            temp.put("picId", picId[i]);
            bindFruits.add(temp);
        }

        SimpleAdapter fruitListAdapter = new SimpleAdapter(this, bindFruits, R.layout.list_element,
                new String[] {"picId", "name"}, new int[] {R.id.fruit_pic, R.id.fruit_name});
        listView.setAdapter(fruitListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getString(R.string.static_broadcast));
                intent.putExtra("name", fruitsName[position]);
                intent.putExtra("picId", picId[position]);
                sendBroadcast(intent);
            }
        });
    }
}
