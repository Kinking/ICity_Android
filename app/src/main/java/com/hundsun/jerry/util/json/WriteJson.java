package com.hundsun.jerry.util.json;

import com.google.gson.Gson;

import java.util.List;


/**
 * Created by huangzhiyuan on 2016/12/18.
 */
public class WriteJson {
    /**
     * 通过引入gson jar包写入json数据
     */
    public String getJsonData(List<?> list){
        //此处要注意，时常会出现说找不到Gson类的情况，这时我们只需要将导入的包和系统提供换换顺序就行了
        Gson gson=new Gson();//利用gson将传入进来的list集合写成json字符串形式
        String jsonString=gson.toJson(list);
        return jsonString;
    }
}
