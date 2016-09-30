package com.example.tommy.project_1;

import android.content.DialogInterface;
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
                String username = ((EditText)findViewById(R.id.username_input)).getText().toString();
                String password = ((EditText)findViewById(R.id.password_input)).getText().toString();
                if (TextUtils.isEmpty(username)) {
                    showToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("密码不能为空");
                    return;
                }
                try {
                    database.login(username, password);
                    showDialog("登录成功");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    showDialog("登录失败");
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
                showToast(checkedButton.getText() + "身份注册功能尚未开启");
            }
        });

        RadioGroup roleGroup = (RadioGroup)findViewById(R.id.role_buttons);
        roleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String identity = ((RadioButton)findViewById(checkedId)).getText().toString();
                showToast(identity + "身份被选中");
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
                        showToast("对话框“确定”按钮被点击");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("对话框“取消”按钮被点击");
                    }
                })
                .create()
                .show();
    }
    final void showToast(String message) {
        showSnackBar(message);
//        if (onDisplayToast != null) {
//            onDisplayToast.cancel();
//        }
//        onDisplayToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
//        onDisplayToast.show();
    }
    final void showSnackBar(String message) {
        Snackbar.make(mainContainer, message, Snackbar.LENGTH_SHORT).show();
    }
}
