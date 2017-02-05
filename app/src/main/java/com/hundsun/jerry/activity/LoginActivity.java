package com.hundsun.jerry.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hundsun.jerry.R;
import com.hundsun.jerry.service.Operation;

public class LoginActivity extends AppCompatActivity {

    Button bt_login;
    EditText et_username;
    EditText et_password;
    String username;
    String password;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_username=(EditText) findViewById(R.id.et_username);
        et_password=(EditText) findViewById(R.id.et_password);

        bt_login=(Button) findViewById(R.id.bt_login);


        //登录的逻辑,还未做记忆功能
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=et_username.getText().toString().trim();
                password=et_password.getText().toString().trim();

                if (username==null||"".equals(username))
                {
                    et_username.requestFocus();
                    et_username.setError("对不起，用户名不能为空");
                    return;
                }


                if (password==null||password.length()<=0)
                {
                    et_password.requestFocus();
                    et_password.setError("对不起，密码不能为空");
                    return;
                }

                new Thread(new Runnable() {
                    public void run() {
                        Operation operaton=new Operation();
                        String result=operaton.login("LoginController", username, password);
                        Message msg=new Message();
                        msg.obj=result;
                        handler.sendMessage(msg);
                    }
                }).start();
            }

            Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {

                    try{

                        String string=(String) msg.obj;
                        Toast.makeText(LoginActivity.this, string, Toast.LENGTH_LONG).show();
                        super.handleMessage(msg);
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, OperatorActivity.class);
                        startActivity(intent);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            };
        });


        //注册的逻辑
        tv_register=(TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
