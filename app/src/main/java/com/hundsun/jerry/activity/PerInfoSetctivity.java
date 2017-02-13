package com.hundsun.jerry.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hundsun.jerry.R;
import com.hundsun.jerry.bean.WidgetBean.PerInfoListItemBean;
import com.hundsun.jerry.library.PerInfoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PerInfoSetctivity extends AppCompatActivity {

    ImageView imageView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_info_setctivity);

        imageView= (ImageView) findViewById(R.id.logo_back);
        listView= (ListView) findViewById(R.id.item_list);

        /*********************************设置返回事件*******************************************/

        //设置返回事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PerInfoSetctivity.this,OperatorActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /**************************************************************************************/





        /*********************************设置ListView选项*******************************************/
        //1.构建数据,创建实体类，把实体类放到集合
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

        //2.创建适配器
        PerInfoListAdapter perInfoListAdapter = new PerInfoListAdapter(this,list);

        //3.绑定适配器
        listView.setAdapter(perInfoListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list.get(i).getItemOption().equals("头像")){

                    //设置头像的响应事件

                }else if(list.get(i).getItemOption().equals("呢称")){

                    //设置呢称的响应事件

                }else if(list.get(i).getItemOption().equals("性别")){

                    //设置性别的响应事件

                }else if(list.get(i).getItemOption().equals("年龄")){

                    //设置年龄的响应事件

                }else if(list.get(i).getItemOption().equals("真实姓名")){

                    //设置真实姓名的响应事件

                }else if(list.get(i).getItemOption().equals("生日")){

                    //设置生日的响应事件

                }else if(list.get(i).getItemOption().equals("星座")){

                    //设置星座的响应事件

                }else if(list.get(i).getItemOption().equals("电话")){

                    //设置电话的响应事件

                }else if(list.get(i).getItemOption().equals("QQ")){

                    //设置QQ的响应事件

                }else if(list.get(i).getItemOption().equals("邮箱")){

                    //设置邮箱的响应事件

                }else if(list.get(i).getItemOption().equals("地址")){

                    //设置地址的响应事件

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
