package com.example.tommy.project_1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tommy.project_1.db.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  load extra button
        setContentView(R.layout.activity_detail);
        String[] options = {"编辑联系人", "分享联系人", "加入黑名单", "删除联系人"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.ditail_more_options, options);
        ((ListView)findViewById(R.id.detail_lower)).setAdapter(arrayAdapter);

        //  get contact info
        if (getIntent().getExtras() != null) {
            Contact contact = Contact.gernarateContactFromStringArray(
                    getIntent().getExtras().getStringArray("selected_contact"));
            loadContactData(contact);
        }

        //  set back button
        findViewById(R.id.detail_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //  set star button
        findViewById(R.id.detail_star).setOnClickListener(new View.OnClickListener() {
            boolean starred = false;
            @Override
            public void onClick(View v) {
                if (!starred) {
                    ((ImageView)v).setImageResource(R.mipmap.full_star);
                } else {
                    ((ImageView)v).setImageResource(R.mipmap.empty_star);
                }
                starred = !starred;
            }
        });
    }

    private void loadContactData(Contact contact) {
        setViewText(R.id.detail_name, contact.getName());
        setViewText(R.id.detail_phone, contact.getTel());
        setViewText(R.id.detail_extra, contact.getTelType() + " " + contact.getAddress());
        findViewById(R.id.detail_upper).setBackgroundColor(
                Color.parseColor(contact.getBackgroundColor()));
    }
    private void setViewText(int id, String text) {
        ((TextView)findViewById(id)).setText(text);
    }
}
