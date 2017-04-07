package com.hundsun.jerry.util.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.client.methods.HttpPost;

/**
 * Created by huangzhiyuan on 2016/12/18.
 */
public class ConnNet {

    private static final String SERVICE_URL="http://172.20.10.7:8080/ICity/";

    //    private static final String SERVICE_URL="http://119.29.69.123:8080/ICity/";  //云服务器地址119.29.69.123:8080
    //将路径定义为一个常量，修改的时候也好更改
    //通过url获取网络连接  connection
    public HttpURLConnection getConn(String urlPath){
        String final_URL = SERVICE_URL+urlPath;
        HttpURLConnection connection=null;

        try {
            URL url=new URL(final_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//允许输入流
            connection.setDoOutput(true);//允许输出流
            connection.setUseCaches(false);//不允许使用缓存
            connection.setRequestMethod("POST");//请求方式

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return connection;
    }

    public HttpPost getHttpPost(String urlPath){
        HttpPost httpPost=new HttpPost(SERVICE_URL+urlPath);

        System.out.println(SERVICE_URL+urlPath);
        return httpPost;
    }


}
