package com.hundsun.jerry.bean;

/**
 * Created by huangzhiyuan on 2017/2/9.
 */
public class UserInfo {
    private Integer userId;
    private String userTrueName;
    private String userNickname;
    private String Sex;
    private String Birthday;
    private Integer Age;
    private String QQNumber;
    private String Introduction;
    private String Declaration;
    private String Profession;

    public UserInfo(Integer userId, String userTrueName, String userNickname, String sex, String birthday, Integer age, String QQNumber, String introduction, String declaration, String profession) {
        this.userId = userId;
        this.userTrueName = userTrueName;
        this.userNickname = userNickname;
        Sex = sex;
        Birthday = birthday;
        Age = age;
        this.QQNumber = QQNumber;
        Introduction = introduction;
        Declaration = declaration;
        Profession = profession;
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
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getQQNumber() {
        return QQNumber;
    }

    public void setQQNumber(String QQNumber) {
        this.QQNumber = QQNumber;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getDeclaration() {
        return Declaration;
    }

    public void setDeclaration(String declaration) {
        Declaration = declaration;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }
}
