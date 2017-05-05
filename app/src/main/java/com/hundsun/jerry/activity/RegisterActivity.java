package com.hundsun.jerry.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

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

    //打开相册的请求码
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
                showNormalDialog();
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


    /**
     * 头像弹出框
     */
    private void showNormalDialog(){
        /**
         * @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

        final android.app.AlertDialog.Builder normalDialog = new android.app.AlertDialog.Builder(RegisterActivity.this);
//        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(RegisterActivity.this);
        normalDialog.setTitle("请选择图片选取方式");
        normalDialog.setMessage("您要通过哪种方式选取图片呢？");
        normalDialog.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //新建一个Intent用来打开拍照界面
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //打开新的拍照界面
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });

        normalDialog.setNegativeButton("图库", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //新建一个Intent用来打开图库界面
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//设定要选择的类型为图像类型
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        });
        normalDialog.show();
    }


    /******传送头像相关方法********/
    /**
     * 此方法在startActivityForResult调用结束后启动此方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE){
            /**
             * 如果是选择照相机的请求
             */
            if(data == null){
                //用户点击取消(没有选择照片)直接返回
                return;
            }
            else {
                //直接返回数据
                Bundle extras = data.getExtras();
                if(extras != null){
                    //用Bitmap对象用来保存用户拍摄的数据
                    Bitmap bm = extras.getParcelable("data");
                    /***摄像仪拍摄完照片后调用裁剪方法，注释原来单纯显示在上面的方法***/
                    //     ImageView imageView = (ImageView)findViewById(R.id.imageView);
                    //将Bitmap数据显示在ImageView中
                    //     imageView.setImageBitmap(bm );

                    //获得bm变量的uri
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        }else if(requestCode == GALLERY_REQUEST_CODE){
            /**
             * 如果是选择图库的请求
             */
            if(data == null){
                //用户点击取消(没有选择照片)直接返回
                return;
            }
            //若是选择了一个照片，则data中必然包含一个Uri，为图片Uri
            Uri uri;
            uri = data.getData();
            //  Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_LONG).show();//测试显示这图片是什么类型的Uri
            /***摄像仪拍摄完照片后调用裁剪方法，注释原来单纯显示在上面的方法***/
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        }else if(requestCode == CROP_REQUEST_CODE){
            //判断是否从图像裁剪界面返回
            //再判断点击的是保存还是取消
            if(data == null){
                //用户点击取消(没有选择照片)直接返回
                return;
            }
            //若是点击保存，则取回data中存储的数据
            Bundle extras = data.getExtras();
            Bitmap bm = extras.getParcelable("data");
            //将图像数据显示在imageView中
            ImageView imageView = (ImageView) findViewById(R.id.head_pic);
            imageView.setImageBitmap(bm);
            /***图像裁剪完后调用上传图片到服务器的方法***/
            sendImage(bm);
        }

    }

    /**
     * 上方法中Uri为测试后证实为content类型，需转换成File类型，用以下方法
     */
    private Uri convertUri(Uri uri){
        InputStream is = null;
        try {
            //获取刘
            is = getContentResolver().openInputStream(uri);
            //用Bitmap工厂类的方法直接从InputStream中生成Bitmap数据，这样就可以直接从InputStream中生成Bitmap数据
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

    /**
     * 讲一个Bitmap数据类型保存到SD卡中，并返回一个File类型的Uri
     */
    private Uri saveBitmap(Bitmap bm){
        File tmpDir = new File(Environment.getExternalStorageDirectory()+"/com.icity.avater");
        //先获取一个路径，若该路径存在则直接用；若不存在则创建一个该路径
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }
        //创建要保存图像稳健的对象
        File img = new File(tmpDir.getAbsolutePath() + "avater.png");

        try {
            FileOutputStream fos = new FileOutputStream(img);
            //将图像数据写入该数据流中
            bm.compress(Bitmap.CompressFormat.PNG,85,fos);//Png格式,质量85，写出的输出流为fos
            fos.flush();
            fos.close();
            //产生一个File类型的返回值
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 启动图像裁剪界面的方法,用uri向此界面传递数据
     *
     * 调用此方法：
     * 1.使用摄像头拍摄完一张照片后立即进行
     * 2.在图库中选择了一张照片后立即进行
     *
     * 备注：此裁剪在Android 4.4.4功能中可以使用，但是在 Android6.0.0功能中无法使用
     */
    private void startImageZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*"); //同时设置数据和类型
        intent.putExtra("crop","true"); //设置在开启的intent中显示的view为可裁剪的
        intent.putExtra("aspectX",1); //裁剪宽高比例1:1
        intent.putExtra("aspectY",1); //
        intent.putExtra("outputX",150); //裁剪输出图片的宽和高
        intent.putExtra("outputY",150); //
        intent.putExtra("return-data",true); //裁剪之后的数据通过intent返回回来的
        startActivityForResult(intent,CROP_REQUEST_CODE);
    }

    /**
     * 发送图像数据的方法，将Bitmap数据发送到指定服务器
     */
    public void sendImage(Bitmap bm){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,60,stream);
        byte[] bytes = stream.toByteArray();  //将Bitmap转化成Byte数组类型
        //进行BASE64编码，将Bitmap转换成String类型，然后发送到服务器
        String img = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
        //       String url = "http://172.20.10.7:8080/ICity/GetMomentInfoController";   //本机测试url
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("img",img);
        client.post("http://172.30.6.210:8080/ICity/PicController", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                //上传成功
                Toast.makeText(RegisterActivity.this,"Upload Success!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //上传失败
                Toast.makeText(RegisterActivity.this,"Upload Fail!",Toast.LENGTH_LONG).show();
            }
        });
    }
    /******传送头像相关方法********/

}
