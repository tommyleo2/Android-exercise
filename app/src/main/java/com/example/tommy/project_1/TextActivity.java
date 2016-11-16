package com.example.tommy.project_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextActivity extends AppCompatActivity {

    private Toast currentToast;
    private static final String SAVED_FILE = "savedFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        final EditText editText = (EditText)findViewById(R.id.TEXT);

        findViewById(R.id.CLEAR_TEXT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        findViewById(R.id.SAVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileOutputStream fileOutputStream = openFileOutput(SAVED_FILE, MODE_PRIVATE)){
                    fileOutputStream.write(editText.getText().toString().getBytes());
                    showToast("Save successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.LOAD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileInputStream fileInputStream = openFileInput(SAVED_FILE)) {
                    byte[] text = new byte[fileInputStream.available()];
                    fileInputStream.read(text);
                    editText.setText(new String(text));
                    editText.setSelection(editText.getText().length());
                    showToast("Load successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Fail to load file");
                }
            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    void showToast(String text) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
