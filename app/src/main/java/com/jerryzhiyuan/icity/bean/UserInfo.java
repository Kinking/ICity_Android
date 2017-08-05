package com.jerryzhiyuan.icity.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by huangzhiyuan on 2017/2/9.
 */
public class UserInfo extends RealmObject implements Serializable{

    public UserInfo() {
    }


    private Integer userId;
    private String img_url;
    private String userNickname;
    private String sex;
    private Integer age;
    private String userTrueName;
    private String birthday;
    private String constellation;
    private String tel;
    private String qqNumber;
    private String email;
    private String address;
    private String introduction;
    private String declaration;
    private String profession;
    private String userName;


    public UserInfo(Integer userId,String img_url, String userNickname, String sex, Integer age,String userTrueName,
                    String birthday,String constellation,String tel,String QQNumber, String email,String address,
                    String introduction, String declaration, String profession,String userName) {
        this.userId = userId;
        this.img_url = img_url;
        this.userNickname = userNickname;
        this.sex = sex;
        this.age = age;
        this.userTrueName = userTrueName;
        this.birthday = birthday;
        this.constellation = constellation;
        this.tel = tel;
        this.qqNumber = QQNumber;
        this.email=email;
        this.address=address;
        this.introduction = introduction;
        this.declaration = declaration;
        this.profession = profession;
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }


}
