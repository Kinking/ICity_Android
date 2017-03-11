package com.hundsun.jerry.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hundsun.jerry.R;
import com.hundsun.jerry.service.Operation;

public class LoginActivity extends AppCompatActivity {

    //使用SharedPreferences进行读取
    private SharedPreferences pref;
    //使用SharedPreferences.Editor进行存储
    private SharedPreferences.Editor editor;

    Button bt_login;
    EditText et_username;
    EditText et_password;
    String username;
    String password;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        //制定SharedPreference的文件名为data
        pref=getSharedPreferences("data",MODE_PRIVATE);
        editor=pref.edit();

        et_username=(EditText) findViewById(R.id.et_username);
        et_password=(EditText) findViewById(R.id.et_password);

        bt_login=(Button) findViewById(R.id.bt_login);

        //查看app中是否已经存储过账号密码，有的话直接显示出来
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            et_username.setText(account);
            et_password.setText(password);
            //记住密码选项
            //checkbox.setChecked(true);
        }

        //登录的逻辑,还未做记忆功能
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**登录验证**/
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

                /**保存账号密码到Sharedpreferenced中**/
                editor.putBoolean("remember_password",true);
                editor.putString("account",username);
                editor.putString("password",password);

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

                        /**提交editor数据**/
                        editor.commit();
                        /***跳转到主界面，同时将userName以广播的形式传给PerInfoSetActivity，周六完成***/
                        Intent intent = new Intent();
                        intent.putExtra("userName",username);
                        sendBroadcast(intent);

                        intent.setClass(LoginActivity.this, OperatorActivity.class);
                        startActivity(intent);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_LONG).show();
                        /**清空输入的账号和密码**/
                        editor.clear();
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
