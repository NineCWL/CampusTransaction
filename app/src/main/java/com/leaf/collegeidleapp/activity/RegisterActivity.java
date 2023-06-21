package com.leaf.collegeidleapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leaf.collegeidleapp.MainActivity;
import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.bean.User;
import com.leaf.collegeidleapp.util.UserDbHelper;

import java.util.LinkedList;


public class RegisterActivity extends AppCompatActivity {

    EditText tvStuNumber,tvStuPwd,tvStuConfirmPwd;
    LinkedList<User> users = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnCancel = findViewById(R.id.btn_cancel);
        //返回到登录界面
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStuNumber = findViewById(R.id.et_username);
        tvStuPwd = findViewById(R.id.et_password);
        tvStuConfirmPwd = findViewById(R.id.et_confirm_password);
        //注册点击事件
        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先确保不为空
                if(CheckInput()) {
                    boolean flag = false;
                    User user = new User();
                    user.setUsername(tvStuNumber.getText().toString());
                    user.setPassword(tvStuPwd.getText().toString());
                    UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(),UserDbHelper.DB_NAME,null,1);
                    users = dbHelper.readUsers();
                    for(User user1 : users) {
                        //如果可以找到,则输出登录成功,并跳转到主界面
                        if(user1.getUsername().equals(tvStuNumber.getText().toString()) ) {
                            flag = true;
                            Toast.makeText(RegisterActivity.this,"注册失败！！该用户名已经被注册",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            dbHelper.addUser(user);
                            Toast.makeText(RegisterActivity.this,"恭喜你注册成功!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    //销毁当前界面
                    finish();
                }
            }
        });
    }

    //判断输入是否符合规范
    public boolean CheckInput() {
        String username = tvStuNumber.getText().toString();
        String password = tvStuPwd.getText().toString();
        String confirm_password = tvStuConfirmPwd.getText().toString();
        if(username.trim().equals("")) {
            Toast.makeText(RegisterActivity.this,"用户名不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.trim().equals("")) {
            Toast.makeText(RegisterActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(confirm_password.trim().equals("")) {
            Toast.makeText(RegisterActivity.this,"确认密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.trim().equals(confirm_password.trim())) {
            Toast.makeText(RegisterActivity.this,"两次密码输入不一致!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
