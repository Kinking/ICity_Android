package com.hundsun.jerry.util.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by huangzhiyuan on 2017/3/9.
 */
public class RealmUtils {
    private Context context;
    private static RealmUtils mInstance;
    private String realName = "myRealm.realm";

    private RealmUtils(Context context){
        this.context = context;
    }

    public static RealmUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (RealmUtils.class){
                if (mInstance == null){
                    mInstance = new RealmUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获得Realm对象
     * @return
     */
    public Realm getRealm(){
        return Realm.getInstance(new RealmConfiguration.Builder(context).name(realName).build());
    }
}
