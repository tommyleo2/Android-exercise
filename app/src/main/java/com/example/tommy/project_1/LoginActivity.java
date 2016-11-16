package com.example.tommy.project_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Toast currentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences savedData = getSharedPreferences(
                getString(R.string.app_name), MODE_PRIVATE);

        final EditText editTextPassword = (EditText) findViewById(R.id.password1);
        final EditText editTextConfirmation = (EditText) findViewById(R.id.password2);

        findViewById(R.id.CLEAR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPassword.setText("");
                editTextConfirmation.setText("");
            }
        });

        if (savedData.getString("password", null) == null) {
            findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editTextPassword.getText().toString().equals("") ||
                            editTextConfirmation.getText().toString().equals("")) {
                        showToast("Password cannot be empty");
                    }
                    else if (!editTextPassword.getText().toString().
                            equals(editTextConfirmation.getText().toString())) {
                        showToast("Password Mismatched");
                    } else {
                        savedData.edit()
                                .putString("password", editTextPassword.getText().toString())
                                .commit();
                        Intent intent = new Intent(LoginActivity.this, TextActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            //editTextConfirmation.setVisibility(View.INVISIBLE);
            ((LinearLayout)editTextConfirmation.getParent()).removeView(editTextConfirmation);
            editTextPassword.setHint("Password");
            findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editTextPassword.getText().toString().
                            equals(savedData.getString("password", null))) {
                        Intent intent = new Intent(LoginActivity.this, TextActivity.class);
                        startActivity(intent);
                    }
                    else if (editTextPassword.getText().toString().equals("")) {
                      showToast("Password cannot be empty");
                    } else {
                        showToast("Invalid Password");
                    }
                }
            });
        }
    }
    void showToast(String text) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
