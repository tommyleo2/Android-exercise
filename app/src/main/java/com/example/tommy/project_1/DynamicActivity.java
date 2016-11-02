package com.example.tommy.project_1;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tommy on 10/21/16.
 */

public class DynamicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamice_broadcast);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            boolean registered = false;
            BroadcastReceiver broadcastReceiver = new DynamicReceiver();
            IntentFilter dynamicFilter = new IntentFilter();
            @Override
            public void onClick(View v) {
                if (!registered) {
                    dynamicFilter.addAction(getString(R.string.dynamic_broadcast));
                    registerReceiver(broadcastReceiver, dynamicFilter);
                    ((Button)v).setText("Unregister Broadcast");
                    Log.i("Dynamic broadcast", "register");
                } else {
                    unregisterReceiver(broadcastReceiver);
                    ((Button)v).setText("Register Broadcast");
                    Log.i("Dynamic broadcast", "unregister");
                }
                registered = !registered;
            }
        });

        findViewById(R.id.send_broadcast).setOnClickListener(new View.OnClickListener() {
            TextView inputText = (TextView)findViewById(R.id.input_text);
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getString(R.string.dynamic_broadcast));
                intent.putExtra("text", inputText.getText().toString());
                Log.i("Input Text", inputText.getText().toString());
                sendBroadcast(intent);
            }
        });
    }
}
