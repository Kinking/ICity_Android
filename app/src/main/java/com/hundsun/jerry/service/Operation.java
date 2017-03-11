package com.hundsun.jerry.service;

import android.support.v7.widget.LinearLayoutCompat;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.hundsun.jerry.bean.UserInfo;
import com.hundsun.jerry.dao.UserInfoDao;
import com.hundsun.jerry.util.network.ConnNet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by huangzhiyuan on 2017/1/21.
 */
public class Operation {
    //登录验证
    public String login(String url,String username,String password)
    {
        String result = null;
        ConnNet connNet=new ConnNet();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        try {
            HttpEntity entity=new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost=connNet.getHttpPost(url);
            System.out.println(httpPost.toString());
            httpPost.setEntity(entity);
            HttpClient client=new DefaultHttpClient();
            HttpResponse httpResponse=client.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
            {
                result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                getJsonFromServer(url,username);
            }
            else
            {
                result="登录失败,账号不存在";
            }
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (ParseException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    //检查用户名
    public String checkUsername(String url,String username)
    {
        String result=null;
        ConnNet connNet=new ConnNet();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        try {
            HttpEntity entity=new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost=connNet.getHttpPost(url);
            System.out.println(httpPost.toString());
            httpPost.setEntity(entity);
            HttpClient client=new DefaultHttpClient();
            HttpResponse httpResponse=client.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
            {
                result=EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                System.out.println("resu"+result);
            }
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (ParseException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    //注册信息发送
    public String upData(String uripath,String jsonString)
    {
        String result = null;
        List<NameValuePair> list=new ArrayList<NameValuePair>();
        NameValuePair nvp=new BasicNameValuePair("jsonstring", jsonString);
        list.add(nvp);
        ConnNet connNet=new ConnNet();
        HttpPost httpPost=connNet.getHttpPost(uripath);
        try {
            HttpEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
            //此句必须加上否则传到客户端的中文将是乱码
            httpPost.setEntity(entity);
            HttpClient client=new DefaultHttpClient();
            HttpResponse httpResponse=client.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode()==200)
            {
                result=EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                System.out.println("resu"+result);
            }
            else {
                result="注册失败";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /***获取服务器的json数据***/
    public void getJsonFromServer(String url, final String userName){

        final UserInfo userInfo=null;

        final UserInfoDao userInfoDao = null;
        //访问服务器端 获取json数据
        //创建客户对象
        AsyncHttpClient client = new AsyncHttpClient();
//        Toast.makeText(this,"发送请求到服务器", LENGTH_SHORT).show();

        client.get(url,new JsonHttpResponseHandler(){
            //返回JSONArray对象 | JSONObject对象
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(statusCode==200){
                    //存储数据变量
//                    List<String> objects = new ArrayList<String>();
                    for (int i = 0;i<response.length();i++){
                        try {
                            //获取具体的一个JSONObject对象
                            JSONObject obj = response.getJSONObject(i);
                            if(userName.equals(obj.getString("userName"))){
                                userInfo.setUserTrueName(obj.getString("userTrueName"));
                                userInfo.setUserNickname(obj.getString("userNickname"));
                                userInfo.setSex(obj.getString("sex"));
                                userInfo.setBirthday(obj.getString("birthday"));
                                userInfo.setAge(Integer.parseInt(obj.getString("age")));
                                userInfo.setQqNumber(obj.getString("qqNumber"));
                                userInfo.setIntroduction(obj.getString("introduction"));
                                userInfo.setDeclaration(obj.getString("declaration"));
                                userInfo.setProfession(obj.getString("profession"));
                                userInfo.setUserName(obj.getString("userName"));
                                userInfoDao.insert(userInfo);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    //控制层主要就是对数据处理
                }
            }
        });

    }


}
