package com.hundsun.jerry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hundsun.jerry.R;
import com.hundsun.jerry.bean.User;
import com.hundsun.jerry.library.CircleImageView;
import com.hundsun.jerry.service.UserOperation;
import com.hundsun.jerry.util.json.WriteJson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 只剩下一个问题，将url写入json数据包中
 * url相关操作在函数sendImg中
 */

public class RegisterActivity extends Activity {

    ImageView iv_back; //返回

    CircleImageView iv_pic = null;  //头像

    EditText et_user;  //用户名
    EditText et_pwd1;  //密码
    EditText et_pwd2;  //确认密码
    EditText et_phone; //电话号码
    EditText et_mail;  //邮箱

    Button register;  //提交按钮




    String jsonString=null;


    String username=null;//用户名
    String password=null;//密码
    String phone=null;//电话号码
    String email=null;//邮箱



    //拍照的要求码
    private static int CAMERA_REQUEST_CODE = 1;

    //相册的请求码
    private static int GALLERY_REQUEST_CODE = 2;

    //裁剪请求码
    private static int CROP_REQUEST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //绑定控件
        iv_back = (ImageView) findViewById(R.id.logo_back);  //返回

        iv_pic= (CircleImageView) findViewById(R.id.head_pic);  //头像

        et_user=(EditText) findViewById(R.id.et_register_username);  //用户名
        et_pwd1=(EditText) findViewById(R.id.et_register_pwd);  //密码
        et_pwd2=(EditText) findViewById(R.id.et_repeat_pwd);  //密码
        et_phone=(EditText) findViewById(R.id.et_phone_number);  //手机号码
        et_mail=(EditText) findViewById(R.id.et_mail);  //邮箱

        register=(Button) findViewById(R.id.confirm_register);

        /***********  点击返回图标的响应事件  ****************/
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        /*********************************************/
        /**
         * 点击头像暂时注释方便调试
         */
        /***********  点击头像设置图片的事件  ****************/
        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "查看点击事件是否能成功运行", Toast.LENGTH_LONG).show();
            }
        });
        /*********************************************/




        /***********  点击注册的响应事件  ****************/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用户名设置
                username=et_user.getText().toString().trim();
                if (username==null||"".equals(username))
                {
                    et_user.requestFocus();
                    et_user.setError("用户名不能为空");
                    return ;
                }

                //密码设置&一致验证
                password=et_pwd1.getText().toString().trim();
                String password2 = et_pwd2.getText().toString().trim();
                if (!password.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    return;
                }

                //手机号码啊
                phone=et_phone.getText().toString().trim();
                if (phone==null||"".equals(phone))
                {
                    et_mail.requestFocus();
                    et_mail.setError("手机号码不能为空");
                    return ;
                }

                //邮箱
                email=et_mail.getText().toString().trim();
                if (email==null||"".equals(email))
                {
                    et_mail.requestFocus();
                    et_mail.setError("邮箱不能为空");
                    return ;
                }


                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String register_date = formatter.format(curDate);



                if(username!=null&&password!=null&&phone!=null&&email!=null){

            //还有url没写
                    try {
                        User user=new User(111,username,password,phone,email,"",register_date);
                        //构造一个user对象
                        List<User> list=new ArrayList<User>();
                        list.add(user);

                        WriteJson writeJson=new WriteJson();
                        //将user对象写出json形式字符串
                        jsonString= writeJson.getJsonData(list);

                        System.out.println(jsonString);

                        new Thread(new Runnable() {
                            public void run() {
                                UserOperation operaton=new UserOperation();
                                String result= operaton.upData("RegisterController", jsonString);
                                Message msg=new Message();
                                msg.obj=result;
                                handler.sendMessage(msg);
                            }
                        }).start();

                    }catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "请将信息填写完整", Toast.LENGTH_LONG).show();
                }

                // dialog.show();
            }
        });
        /*********************************************/
    }
    /***********************************  onCreate外的方法  *********************************************/

    /******************* Handler **************************/
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            dialog.dismiss();

            try {

                String msgobj=msg.obj.toString();
                if(msgobj.equals("t"))
                {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.setClass(RegisterActivity.this, UserOperation.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                }
                super.handleMessage(msg);

            }catch (Exception e){
                Toast.makeText(RegisterActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    };

}

