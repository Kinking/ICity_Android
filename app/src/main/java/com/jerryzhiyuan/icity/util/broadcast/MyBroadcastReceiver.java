package com.jerryzhiyuan.icity.util.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by huangzhiyuan on 2017/3/11.
 * 用于PerInfoSetActivity中获取广播内容
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    public static final String TAG = "MyBroadcastReceiver";
    private static int m = 1;

    public MyBroadcastReceiver(){
        Log.i(TAG,"MyReceiver");
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG,"intent:" + intent);
        String name = intent.getStringExtra("name");
        /***用SharedPreference将相关内容保存起来***/
        Bundle bundle = intent.getExtras();
    }
}
