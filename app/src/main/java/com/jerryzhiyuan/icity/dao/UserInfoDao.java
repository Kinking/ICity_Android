package com.jerryzhiyuan.icity.dao;

import com.jerryzhiyuan.icity.bean.UserInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangzhiyuan on 2017/3/9.
 */
public interface UserInfoDao {
    /**
     * 插入一个用户信息
     * @param user    需要插入的用户信息对象
     * @throws SQLException
     */
    void insert(UserInfo user) throws SQLException;

    /**
     * 获得所有的用户信息列表
     * @return 用户信息列表
     * @throws SQLException
     */
    List<UserInfo> getAllUserInfo() throws SQLException;

    /**
     * 更新一个用户信息
     * @param user 需要更新的用户信息类
     * @return      更新后的用户信息对象
     * @throws SQLException
     */
    UserInfo updateUserInfo(UserInfo user) throws SQLException;

    /**
     * 根据姓名修改新姓名
     * @param name1 老名字
     * @param name2 新名字
     * @throws SQLException
     */
    void updateUserInfo(String name1,String name2) throws SQLException;

    /**
     * 根据id删除用户信息
     * @param id 用户信息主键
     * @throws SQLException
     */
    void deleteUserInfo(int id) throws SQLException;

    /**
     * 异步添加用户信息
     * @param user 需要添加的用户信息对象
     * @throws SQLException
     */
    void insertUserInfoAsync(UserInfo user) throws SQLException;

    /**
     * 按名字或者年龄查找第一个User
     */
    UserInfo findByNameOrAge(String name1,int a) throws SQLException;

    /**
     * 按名字查找第一个User
     */
    UserInfo findByUsername(String name) throws SQLException;

    /**
     * 清楚所有
     * @throws SQLException
     */
    void deleteAll() throws SQLException;

    /**
     * 关闭事务
     */
    void closeRealm();
}
