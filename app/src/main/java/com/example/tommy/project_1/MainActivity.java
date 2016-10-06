package com.example.tommy.project_1;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tommy.project_1.db.DB;

public class MainActivity extends AppCompatActivity {

    private DB database = new DB();
    private Toast onDisplayToast;
    private CoordinatorLayout mainContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        mainContainer = (CoordinatorLayout)findViewById(R.id.main_container);
        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout usernameWrapper = (TextInputLayout)findViewById(R.id.username_input_wrapper);
                TextInputLayout passwordWrapper = (TextInputLayout)findViewById(R.id.password_input_wrapper);
                usernameWrapper.setErrorEnabled(false);
                passwordWrapper.setErrorEnabled(false);
                String username = ((EditText)findViewById(R.id.username_input)).getText().toString();
                String password = ((EditText)findViewById(R.id.password_input)).getText().toString();
                if (TextUtils.isEmpty(username)) {
                    usernameWrapper.setErrorEnabled(true);
                    usernameWrapper.setError("用户名不能为空");
                }
                if (TextUtils.isEmpty(password)) {
                    passwordWrapper.setErrorEnabled(true);
                    passwordWrapper.setError("密码不能为空");
                }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) return;
                try {
                    database.login(username, password);
                    showSnackBar("登录成功");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    showSnackBar("登录失败");
                }
            }
        });

        Button signUpButton = (Button)findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup roleGroup = (RadioGroup)findViewById(R.id.role_buttons);
                int checkedButtonID = roleGroup.getCheckedRadioButtonId();
                RadioButton checkedButton = (RadioButton)findViewById(checkedButtonID);
                showSnackBar(checkedButton.getText() + "身份注册功能尚未开启");
            }
        });

        RadioGroup roleGroup = (RadioGroup)findViewById(R.id.role_buttons);
        roleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String identity = ((RadioButton)findViewById(checkedId)).getText().toString();
                showSnackBar(identity + "身份被选中");
            }
        });
    }
    final void showDialog(String message) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar("对话框“确定”按钮被点击");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackBar("对话框“取消”按钮被点击");
                    }
                })
                .create()
                .show();
    }
    final void showToast(String message) {
        if (onDisplayToast != null) {
            onDisplayToast.cancel();
        }
        onDisplayToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        onDisplayToast.show();
    }
    final void showSnackBar(String message) {
        Snackbar.make(mainContainer, message, Snackbar.LENGTH_SHORT)
                .setAction("按钮", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("Snackbar的按钮被点击了");
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
}
