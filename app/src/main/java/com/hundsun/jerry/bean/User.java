package com.hundsun.jerry.bean;


import java.io.Serializable;

/**
 * Created by huangzhiyuan on 2017/1/20.
 */
public class User implements Serializable{
    private Integer id;
    private String userName;
    private String userPwd;
    private String userTel;
    private String userEmail;
    private String img_url;
    private String userRegisterDate;

    public User(Integer id, String userName, String userPwd, String userTel, String userEmail,String img_url,String userRegisterDate) {
        this.id = id;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.img_url = img_url;
        this.userRegisterDate = userRegisterDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(String userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public User() {
        super();
    }
}
