package com.hundsun.jerry.bean;

import io.realm.RealmObject;

/**
 * Created by huangzhiyuan on 2017/2/9.
 */
public class UserInfo extends RealmObject{

    public UserInfo() {
    }


    private Integer userId;
    private String userTrueName;
    private String userNickname;
    private String sex;
    private String birthday;
    private Integer age;
    private String qqNumber;
    private String introduction;
    private String declaration;
    private String profession;
    private String userName;

    public UserInfo(Integer userId, String userTrueName, String userNickname, String sex, String birthday, Integer age, String QQNumber, String introduction, String declaration, String profession,String userName) {
        this.userId = userId;
        this.userTrueName = userTrueName;
        this.userNickname = userNickname;
        this.sex = sex;
        this.birthday = birthday;
        this.age = age;
        this.qqNumber = QQNumber;
        this.introduction = introduction;
        this.declaration = declaration;
        this.profession = profession;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
