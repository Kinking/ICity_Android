package com.hundsun.jerry.bean;

/**
 * Created by huangzhiyuan on 2017/1/20.
 */
public class User {
    private Integer id;
    private String userName;
    private String userPwd;
    private String userTel;
    private String userEmail;
    private String img_url;
    private String userRegisterDate;

    public User(Integer id, String userEmail, String userName, String userPwd, String userRegisterDate, String userTel,String img_url) {
        this.id = id;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userRegisterDate = userRegisterDate;
        this.userTel = userTel;
        this.img_url = img_url;
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
}
