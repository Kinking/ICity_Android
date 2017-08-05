package com.jerryzhiyuan.icity.util.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jerryzhiyuan.icity.bean.Moment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhiyuan on 2016/12/18.
 */
public class JsonUtil {
    /**
     * 将传过来的json字符串转换成list
     */

    public List<?> StringFromJson (String jsonData){
        Type listType = new TypeToken<List<?>>(){}.getType();//??????
        Gson gson=new Gson();
        ArrayList<?> list=gson.fromJson(jsonData,listType);
        return list;
    }

    public List<Moment> momentStringFromJson (String jsonStr)
    {
        Gson gson=new Gson();
        List<Moment> list=gson.fromJson(jsonStr, new TypeToken<List<Moment>>(){}.getType());
        return list;
    }
}
