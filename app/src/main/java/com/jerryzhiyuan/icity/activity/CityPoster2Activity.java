package com.jerryzhiyuan.icity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hundsun.jerryzhiyuan.R;


public class CityPoster2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_poster2);
        //线程自动控制，睡眠两秒钟
        Thread thread = new Thread(){
            @Override
            public void run() {//run表示方法
                // TODO Auto-generated method stub
                super.run();
                //上面的super报错点击修改自动弹出try...catch
                try {
                    Thread.sleep(2000);//睡眠两秒钟
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //intent相当于跳转操作
                Intent i = new Intent();
                i.setClass(CityPoster2Activity.this, LoginActivity.class);
                startActivity(i);
                finish();//销毁操作
            }
        };
        thread.start();
    }

    @Override
    protected void onStop() {
        LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.activity_city_poster2, null);
        super.onStop();
    }

}
