package com.hundsun.jerry.dao.impl;

import android.content.Context;

import com.hundsun.jerry.bean.UserInfo;
import com.hundsun.jerry.dao.UserInfoDao;
import com.hundsun.jerry.util.realm.RealmUtils;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by huangzhiyuan on 2017/3/9.
 */
public class UserInfoDaoImpl implements UserInfoDao {
    private Context context;
    private Realm mRealm;

    public UserInfoDaoImpl(Context context) {
        mRealm = RealmUtils.getInstance(context).getRealm();
    }

    /**
     * 同步插入
     * @param userInfo    需要插入的用户对象
     * @throws SQLException
     */
    @Override
    public void insert(UserInfo userInfo) throws SQLException {
        mRealm.beginTransaction();//必须先开启事务
        UserInfo userInfo11 = mRealm.copyToRealm(userInfo);//把User对象复制到Realm
        mRealm.commitTransaction();//提交事务
//        mRealm.close();//必须关闭，不然会造成内存泄漏
    }

    /**
     * 返回所有的UserInfo对象,并按照名字首字母排序
     * @return  UserInfo对象表
     * @throws SQLException
     */
    @Override
    public List<UserInfo> getAllUserInfo() throws SQLException {
        List<UserInfo> list = null;
        RealmResults<UserInfo> results = mRealm.where(UserInfo.class).findAll();
        results.sort("name", Sort.DESCENDING);//针对字符串的排序，但目前并不是支持所有字符集
        list = results;
//        mRealm.close();
        return list;
    }

    /**
     * 更新一个User
     * @param user 需要更新的用户信息类
     * @return 返回更新后的UserInfo
     * @throws SQLException
     */
    @Override
    public UserInfo updateUserInfo(UserInfo user) throws SQLException {
        mRealm.beginTransaction();//开启事务
        UserInfo user1 = mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();//提交事务
//        mRealm.close();//必须关闭事务
        return user1;
    }

    /**
     * @param name1 老名字
     * @param name2 新名字
     * @throws SQLException
     */
    @Override
    public void updateUserInfo(String name1, String name2) throws SQLException {
        mRealm.beginTransaction();//开启事务
        mRealm.where(UserInfo.class)
                .equalTo("name",name1)//查询出name为name1的User对象
                .findFirst()

                .setUserName(name2);//修改查询出的第一个对象的名字
        mRealm.commitTransaction();
//        mRealm.close();
    }

    /**
     * 根据id删除一个UserInfo
     * @param id 用户主键
     * @throws SQLException
     */
    @Override
    public void deleteUserInfo(int id) throws SQLException {
        UserInfo userInfo = mRealm.where(UserInfo.class).equalTo("id",id).findFirst();//删除id列值为id的行
        mRealm.beginTransaction();
        userInfo.deleteFromRealm();//从数据库删除
        mRealm.commitTransaction();
//        mRealm.close();
    }

    /**
     * 异步插入UserInfo
     * @param userInfo 需要添加的用户对象
     * @throws SQLException
     */
    @Override
    public void insertUserInfoAsync(final UserInfo userInfo) throws SQLException {
        //一个Realm只能在同一个线程访问，在子线程中进行数据库操作必须重新获取realm对象
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();//开启事务
                UserInfo userInfo1 = realm.copyToRealm(userInfo);
                realm.commitTransaction();
                realm.close();//记得关闭事务
            }
        });
//        mRealm.close();//外面也不能忘记关闭事务
    }


    /**
     * 返回第一个指定名字或者年龄的对象
     * @param name1 名字
     * @param age1  年龄
     */
    @Override
    public UserInfo findByNameOrAge(String name1,int age1) throws SQLException{
        UserInfo userInfo = mRealm.where(UserInfo.class)
                .equalTo("name",name1)//相当于where name = name1
                .or()//或，连接查询条件，没有这个方式时会默认是&连接
                .equalTo("age",age1)//相当于where age = age1
                .findFirst();
        //整体相当于select * from (表名) where name = (传入的name) or age = （传入的age）limit 1;
//        mRealm.close();
        return userInfo;
    }

    @Override
    public void deleteAll() throws SQLException {
        mRealm.beginTransaction();
        mRealm.where(UserInfo.class).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
//        mRealm.close();
    }


    @Override
    public void closeRealm() {
        mRealm.close();
    }
}
