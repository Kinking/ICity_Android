package com.hundsun.jerry.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hundsun.jerry.R;
import com.hundsun.jerry.activity.perinfosetdetails.UMailSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UNameSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UQQSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UTelSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UTruenameSetActivity;
import com.hundsun.jerry.bean.WidgetBean.PerInfoListItemBean;
import com.hundsun.jerry.dao.UserInfoDao;
import com.hundsun.jerry.library.CircleImageView;
import com.hundsun.jerry.library.PerInfoListAdapter;
import com.hundsun.jerry.library.wheel.widget.AddressPickerDialog;
import com.hundsun.jerry.library.wheel.widget.DatePickerDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PerInfoSetActivity extends Activity {

    //拍照的要求码
    private static int CAMERA_REQUEST_CODE = 1;

    //打开相册的请求码
    private static int GALLERY_REQUEST_CODE = 2;

    //裁剪请求码
    private static int CROP_REQUEST_CODE = 3;


    ImageView imageView;
    ListView listView;
    UserInfoDao userInfoDao;
    List<PerInfoListItemBean> list = new ArrayList<PerInfoListItemBean>();

    CircleImageView iv_set_head = null;  //头像

    String[] sexArr = new String[]{"男","女"};


    private static final String BROADCAST_GET_USERNAME = "org.jerry.broadcast.action.GET_USERNAME";


    private BroadcastReceiver mBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_info_setctivity);

        imageView= (ImageView) findViewById(R.id.logo_back);
        /***接受广播内容***/
//        mBroadcastReceiver = new MyBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BROADCAST_GET_USERNAME);
//        registerReceiver(mBroadcastReceiver,intentFilter);
        /****************/

        /***用Sharedpreferenced获取注册的用户名***/
        SharedPreferences sharedPreferences = this.getSharedPreferences("logindata", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        String userName=sharedPreferences.getString("account","");

        /*********************************设置返回事件*******************************************/
        //设置返回事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PerInfoSetActivity.this,OperatorActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /**************************************************************************************/

        //看看有没有Intent传来的数据
        Intent userNameAccIntent = getIntent();
        String accUserName=userNameAccIntent.getStringExtra("userName");
        Intent userTrueNameaccIntent = getIntent();
        String accUserTrueName=userTrueNameaccIntent.getStringExtra("userTrueName");
        Intent phoneAccIntent = getIntent();
        String accTel=phoneAccIntent.getStringExtra("tel");
        Intent QQAccIntent = getIntent();
        String accQqNum=QQAccIntent.getStringExtra("qqNum");
        Intent mailAccIntent = getIntent();
        String accMail=mailAccIntent.getStringExtra("mail");


        /****设置头像事件****/
        iv_set_head = (CircleImageView) findViewById(R.id.head_set_pic);
        iv_set_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog();
            }
        });


        /*********************************设置ListView选项*******************************************/

        //1.构建数据,创建实体类，把实体类放到集合
        listView= (ListView) findViewById(R.id.item_list);

        if(accUserName == null){
            list.add(new PerInfoListItemBean("呢称",""));
        }else {
            list.add(new PerInfoListItemBean("呢称",accUserName));
        }

        list.add(new PerInfoListItemBean("性别",""));
        list.add(new PerInfoListItemBean("年龄",""));

        if(accUserTrueName == null){
            list.add(new PerInfoListItemBean("真实姓名",""));
        }else {
            list.add(new PerInfoListItemBean("真实姓名",accUserTrueName));
        }

        list.add(new PerInfoListItemBean("生日",""));
        list.add(new PerInfoListItemBean("星座",""));

        if(accTel == null){
            list.add(new PerInfoListItemBean("电话",""));
        }else {
            list.add(new PerInfoListItemBean("电话",accTel));
        }

        if(accQqNum == null){
            list.add(new PerInfoListItemBean("电话",""));
        }else {
            list.add(new PerInfoListItemBean("QQ",accQqNum));
        }

        if(accMail == null){
            list.add(new PerInfoListItemBean("邮箱",""));
        }else {
            list.add(new PerInfoListItemBean("邮箱",accMail));
        }

        list.add(new PerInfoListItemBean("地址",""));


        /*****接下来要根据userName来获取Realm中的数据，周日写****/
        /**
         * 暂时先忽略realm的逻辑
         */
//        try {
//            UserInfo userInfo = userInfoDao.findByUsername(userName);
//            list.add(new PerInfoListItemBean("头像",""));
//            list.add(new PerInfoListItemBean("呢称",userInfo.getUserNickname()));
//            list.add(new PerInfoListItemBean("性别",userInfo.getSex()));
//            list.add(new PerInfoListItemBean("年龄",userInfo.getAge().toString()));
//            list.add(new PerInfoListItemBean("真实姓名",userInfo.getUserTrueName()));
//            list.add(new PerInfoListItemBean("生日",userInfo.getBirthday()));
//            list.add(new PerInfoListItemBean("星座",userInfo.getConstellation()));
//            list.add(new PerInfoListItemBean("电话",userInfo.getTel()));
//            list.add(new PerInfoListItemBean("QQ",userInfo.getQqNumber()));
//            list.add(new PerInfoListItemBean("邮箱",userInfo.getEmail()));
//            list.add(new PerInfoListItemBean("地址",userInfo.getAddress()));
//
//        } catch (SQLException e) {
//            System.out.println("未找到此用户名对应的信息");
//            e.printStackTrace();
//        }

        //2.创建适配器
        PerInfoListAdapter perInfoListAdapter = new PerInfoListAdapter(this,list);


        //3.绑定适配器
        listView.setAdapter(perInfoListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(list.get(i).getItemOption().equals("呢称")){

                    /**设置呢称的响应事件**/
                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UNameSetActivity.class);
                    startActivity(intent);

                }else if(list.get(i).getItemOption().equals("性别")){

                    /**设置性别的响应事件**/
                    /**逻辑暂时有问题**/

                   Toast.makeText(PerInfoSetActivity.this,"实现逻辑暂时有问题",Toast.LENGTH_LONG).show();

//                    // 自定义对话框
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PerInfoSetActivity.this);
//                    builder.setTitle("请选择性别");
//                    builder.setItems(sexArr, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if(which==0){
//                                //选择男
//                                list.get(i).setItemOption("男");
//
//                            }else {
//                                //选择女
//                                list.get(i).setItemOption("女");
//
//                            }
//                        }
//                    });


                }else if(list.get(i).getItemOption().equals("年龄")){

                    /**设置年龄的响应事件**/

                    /**
                     * 逻辑暂时有问题
                     */

                    /**
                     * 思路：遍历list获得生日日期，然后来判断
                     */
                    for(int j=0;j<list.size();j++){
                        if (list.get(j).getItemOption().equals("生日")){
                            String year = list.get(j).getItemValue().substring(0,3);
                            Integer y=Integer.parseInt(year);
                            Calendar c = Calendar.getInstance();
                            Integer age=c.get(Calendar.YEAR)-y;
                            list.get(i).setItemValue(age.toString());
                        }
                    }


                }else if(list.get(i).getItemOption().equals("真实姓名")){

                    /**设置真实姓名的响应事件**/

                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UTruenameSetActivity.class);
                    startActivity(intent);

                }else if(list.get(i).getItemOption().equals("生日")){

                    /**设置生日的响应事件**/

                    /**
                     * 逻辑暂时有问题
                     */

                    DatePickerDialog mChangeBirthDialog = new DatePickerDialog(PerInfoSetActivity.this);
                    mChangeBirthDialog.setDate(1991, 01, 01);
                    mChangeBirthDialog.setDatePickListener(new DatePickerDialog.OnDatePickListener() {
                        @Override
                        public void onClick(String year, String month, String day) {
                            list.get(i).setItemOption(year + "-" + month + "-" + day);
                        }
                    });


                }else if(list.get(i).getItemOption().equals("星座")){

                    /**设置星座的响应事件**/
                    /**
                     * 尚未写逻辑
                     * 思路：遍历list获得生日日期，然后来判断
                     */

                }else if(list.get(i).getItemOption().equals("电话")){

                    /**设置电话的响应事件**/

                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UTelSetActivity.class);
                    startActivity(intent);

                }else if(list.get(i).getItemOption().equals("QQ")){

                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UQQSetActivity.class);
                    startActivity(intent);

                }else if(list.get(i).getItemOption().equals("邮箱")){

                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UMailSetActivity.class);
                    startActivity(intent);

                }else if(list.get(i).getItemOption().equals("地址")){

                    /**
                     * 逻辑暂时有问题
                     */

                    //设置地址的响应事件
                    AddressPickerDialog mChangeAddressDialog = new AddressPickerDialog(PerInfoSetActivity.this);
                    mChangeAddressDialog.setAddress("四川", "自贡");
                    mChangeAddressDialog.setAddresskListener(new AddressPickerDialog.OnAddressCListener() {
                        @Override
                        public void onClick(String province, String city) {
                            list.get(i).setItemOption(province + "-" + city);
                        }
                    });
                }
            }
        });
        /**************************************************************************************/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


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

        final android.app.AlertDialog.Builder normalDialog = new android.app.AlertDialog.Builder(PerInfoSetActivity.this);
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
            ImageView imageView = (ImageView) findViewById(R.id.head_set_pic);
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
        client.post("http://172.30.0.195:8080/ICity/PicController", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                //上传成功
                Toast.makeText(PerInfoSetActivity.this,"Upload Success!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                //上传失败
                Toast.makeText(PerInfoSetActivity.this,"Upload Fail!",Toast.LENGTH_LONG).show();
            }
        });
    }
    /******传送头像相关方法********/
}
