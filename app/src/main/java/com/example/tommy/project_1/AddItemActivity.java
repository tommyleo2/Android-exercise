package com.example.tommy.project_1;

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
            @Override
            public void onClick(View v) {
                String name = ((TextView)findViewById(R.id.name)).getText().toString();
                DatePicker datePicker = ((DatePicker)findViewById(R.id.birthday));
                String present = ((TextView)findViewById(R.id.present)).getText().toString();
                new DB(AddItemActivity.this).addItem(name,
                        datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        present);
                finish();
            }
        });
    }
}
