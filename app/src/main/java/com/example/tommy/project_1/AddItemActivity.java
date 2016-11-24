package com.example.tommy.project_1;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.add_activity_container);
            @Override
            public void onClick(View v) {
                String name = ((TextView)findViewById(R.id.name)).getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(coordinatorLayout, "名字为空，请完善", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                DB db = new DB(AddItemActivity.this);
                if (db.hasName(name)) {
                    Snackbar.make(coordinatorLayout, "名字重复啦，请核查", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                DatePicker datePicker = ((DatePicker)findViewById(R.id.birthday));
                String present = ((TextView)findViewById(R.id.present)).getText().toString();
                db.addItem(name,
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth(),
                        present);
                finish();
            }
        });
    }
}
