package com.hundsun.jerry.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hundsun.jerry.R;
import com.hundsun.jerry.activity.perinfosetdetails.UMailSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UNameSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UQQSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UTelSetActivity;
import com.hundsun.jerry.activity.perinfosetdetails.UTruenameSetActivity;
import com.hundsun.jerry.bean.WidgetBean.PerInfoListItemBean;
import com.hundsun.jerry.dao.UserInfoDao;
import com.hundsun.jerry.library.PerInfoListAdapter;
import com.hundsun.jerry.library.wheel.widget.AddressPickerDialog;
import com.hundsun.jerry.library.wheel.widget.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PerInfoSetActivity extends Activity {

    ImageView imageView;
    ListView listView;
    UserInfoDao userInfoDao;


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

        /*********************************设置ListView选项*******************************************/
        //1.构建数据,创建实体类，把实体类放到集合
        listView= (ListView) findViewById(R.id.item_list);
        final List<PerInfoListItemBean> list = new ArrayList<PerInfoListItemBean>();
        list.add(new PerInfoListItemBean("头像",""));
        list.add(new PerInfoListItemBean("呢称",""));
        list.add(new PerInfoListItemBean("性别",""));
        list.add(new PerInfoListItemBean("年龄",""));
        list.add(new PerInfoListItemBean("真实姓名",""));
        list.add(new PerInfoListItemBean("生日",""));
        list.add(new PerInfoListItemBean("星座",""));
        list.add(new PerInfoListItemBean("电话",""));
        list.add(new PerInfoListItemBean("QQ",""));
        list.add(new PerInfoListItemBean("邮箱",""));
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
        PerInfoListAdapter perInfoListAdapter = new PerInfoListAdapter
                (this,list,R.layout.perinfo_list_item,
                        new String[]{"item_option","item_setting"},
                        new int[]{R.id.item_option,R.id.item_setting});

        //3.绑定适配器
        listView.setAdapter(perInfoListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(list.get(i).getItemOption().equals("头像")){

                    /**设置头像的响应事件**/
                    /**
                     * 尚未写逻辑
                     */

                }else if(list.get(i).getItemOption().equals("呢称")){

                    /**设置呢称的响应事件**/
                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UNameSetActivity.class);
                    startActivity(intent);

                    Intent accIntent = getIntent();
                    String userName=accIntent.getStringExtra("userName");
                    list.get(i).setItemValue(userName);



                }else if(list.get(i).getItemOption().equals("性别")){

                    /**设置性别的响应事件**/

                    // 自定义对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(PerInfoSetActivity.this);
                    builder.setTitle("请选择性别");
                    final String[] sexArr = {"男", "女"};
                    builder.setItems(sexArr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0){
                                //选择男
                                list.get(i).setItemOption("男");

                            }else {
                                //选择女
                                list.get(i).setItemOption("女");

                            }
                        }
                    });


                }else if(list.get(i).getItemOption().equals("年龄")){

                    /**设置年龄的响应事件**/
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

                    Intent accIntent = getIntent();
                    String userTrueName=accIntent.getStringExtra("userTrueName");
                    list.get(i).setItemValue(userTrueName);

                }else if(list.get(i).getItemOption().equals("生日")){

                    /**设置生日的响应事件**/

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

                    Intent accIntent = getIntent();
                    String tel=accIntent.getStringExtra("tel");
                    list.get(i).setItemValue(tel);


                }else if(list.get(i).getItemOption().equals("QQ")){

                    /**设置QQ的响应事件**/
                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UQQSetActivity.class);
                    startActivity(intent);

                    Intent accIntent = getIntent();
                    String qqNum=accIntent.getStringExtra("qqNum");
                    list.get(i).setItemValue(qqNum);

                }else if(list.get(i).getItemOption().equals("邮箱")){

                    /**设置邮箱的响应事件**/
                    Intent intent=new Intent();
                    intent.setClass(PerInfoSetActivity.this, UMailSetActivity.class);
                    startActivity(intent);

                    Intent accIntent = getIntent();
                    String mail=accIntent.getStringExtra("mail");
                    list.get(i).setItemValue(mail);


                }else if(list.get(i).getItemOption().equals("地址")){

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
}
