package com.example.tommy.project_1;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tommy.project_1.db.Contact;
import com.example.tommy.project_1.db.DB;

/**
 * Created by tommy on 10/15/16.
 */

class MainPageAdaptor extends BaseAdapter {
    private DB db;
    private Context context;
    private int size;

    MainPageAdaptor(Context context) {
        super();
        this.context =context;
        this.db = new DB(context);
        size = db.getListSize();
    }
    @Override
    public int getCount() {
        return size;
    }
    @Override
    public Object getItem(int i) {
        return db.queryContact(i);
    }
    @Override
    public long getItemId(int i) {
        return i + 1;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        i = (int)getItemId(i);
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_element, null);
            viewHolder = new ViewHolder();
            viewHolder.photo = (TextView)view.findViewById(R.id.contact_photo);
            viewHolder.name = (TextView)view.findViewById(R.id.contact_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        System.out.println("Count: " + Integer.toString(i));
        Contact ct = (Contact)getItem(i);
        viewHolder.photo.setText(Character.toString(ct.getName().charAt(0)));
        viewHolder.name.setText(ct.getName());
        return view;
    }
}

class ViewHolder {
    TextView photo;
    TextView name;
}