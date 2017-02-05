package com.hundsun.jerry.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hundsun.jerry.R;
import com.hundsun.jerry.bean.User;
import com.hundsun.jerry.library.CircleImageView;
import com.hundsun.jerry.service.Operation;
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

public class RegisterActivity extends AppCompatActivity {

    ImageView iv_back; //返回

    ImageView iv_pic;  //头像

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




        /***********  点击头像设置图片的事件  ****************/
        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                builder.setIcon(R.drawable.ic_launcher);  设置图标
                builder.setTitle("请选择照片");
                //    指定下拉列表的显示数据
                final String[] pic_options = {"图库", "摄像头"};
                //    设置一个下拉的列表选择项
                builder.setItems(pic_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==1){
                            //新建一个Intent用来打开拍照界面
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //定义一个静态变量来保存请求码
                            startActivityForResult(intent,CAMERA_REQUEST_CODE);//打开新界面用来拍照
                        }else {
                            //新建一个Intent用来打开图库界面
                            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                            //此intent选择的文件类型为图像类型
                            intent.setType("image/*");
                            startActivityForResult(intent,GALLERY_REQUEST_CODE);
                        }
                    }
                });
                builder.show();
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
                                Operation operaton=new Operation();
                                String result= operaton.upData("RegisterServlet", jsonString);
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
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
//            dialog.dismiss();

            try {

                String msgobj=msg.obj.toString();
                if(msgobj.equals("t"))
                {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.setClass(RegisterActivity.this, Operation.class);
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
    /*********************************************/




    /*********************  图片操作一系列方法  ************************/
    private Uri saveBitmap(Bitmap bm){
        //将Bitmap数据类型保存到SD卡中
        //返回值为一个File类型的Uri

        //获得要保存文件的图像文件的路径
        File tmpDir = new File(Environment.getExternalStorageDirectory()+"/com.hundsun.jerry.gallery");
        if(!tmpDir.exists()){
            //先判断该路径是否存在，若不存在就创建一个此路径
            tmpDir.mkdir();
        }

        //创建要保存的图像文件的对象
        File img = new File(tmpDir.getAbsolutePath()+"avater.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG,85,fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Contentleix的Uri不能进行复制粘贴等操作，需要进行类型转换成File类型的Uri
    private Uri convertUri(Uri uri){
        InputStream is =null;
        try {
            is = getContentResolver().openInputStream(uri);//从Uri中获取流
            //用一个工厂类从流中生成一个Bitmap数据类型
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //启动图像裁剪界面
    private void startImageZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");//同时设置数据和类型,数据通过uri传给图像裁剪界面，类型为image类型，告诉图像裁剪界面我们要裁剪的是图像
        //intent中添加参数
        intent.putExtra("crop","true");//设置在开启的intent中显示的view为可裁剪的
        //两个aspect设置裁剪的宽高比例都为1
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectX",1);
        //两个output设置裁剪图片的宽和高
        intent.putExtra("outputX",150);
        intent.putExtra("outputX",150);
        //设置裁剪后的数据通过intent返回回来
        intent.putExtra("return-data",true);

        startActivityForResult(intent,CROP_REQUEST_CODE);

    }

    //重写对拍照事件的处理方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            //如果是这个请求码，则进行拍照数据的处理

            //1.判断用户是点击了"拍照"还是"取消"，若取消则不应该进行操作
            //通过判断data是否为空
            if(data == null){
                return;//直接返回，不再进行处理
            }else{
                //点击"拍照按钮"，从data中取出数据
                Bundle extras = data.getExtras();
                if(extras != null){
                    //创建一个bitmap变量用来保存用户拍照的数据
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    //打开裁剪界面
                    startImageZoom(uri);

                }
            }
        }else if(requestCode == GALLERY_REQUEST_CODE){
            if(data == null){
                return;
            }
            Uri uri; //统一资源定位符
            uri = data.getData();
//            //查看uri是哪种类型的，用Toast来显示uri
//            Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_LONG).show();

            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);

            //以上从摄像头界面&&图库界面返回后启动裁剪界面

        }else if(requestCode == CROP_REQUEST_CODE){
            //是否为从图像裁剪界面返回
            //判断用户点击的是保存还是取消

            //点击取消
            if(data == null){
                return;
            }

            //点击保存
            Bundle extras = data.getExtras();
            Bitmap bm = extras.getParcelable("data");
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            imageView.setImageBitmap(bm);
            sendImage(bm);
        }
    }


    //添加方法用来发送图像数据
    private void sendImage(Bitmap bm){
        //将Bitmap转化成为byte数组类型
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,60,stream);
        byte[] bytes = stream.toByteArray();

        //将以上byte数组编码成为base64编码的数据，将其发送给服务器
        //以下进行base64编码
        String img = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
        //将以上String发送到服务器，然后服务器解码得到原始数据

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();//保存要传出的参数
        params.add("img",img);
        client.post("http://192.168.1.104:8080/I_City/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                Toast.makeText(RegisterActivity.this,"Upload Success!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(RegisterActivity.this,"Upload Fail!",Toast.LENGTH_LONG).show();
            }
        });
    }
    /************************************************************/
}

