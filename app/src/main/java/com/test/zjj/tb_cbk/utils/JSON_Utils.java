package com.test.zjj.tb_cbk.utils;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class JSON_Utils {

    public static String getChannelJsonArr(String jsonStr) throws Exception {
        if (jsonStr==null){
            throw new Exception("jsonStr is null");
        }
        JSONObject object = new JSONObject(jsonStr);
        String jsonArr = object.getJSONObject("showapi_res_body").getJSONArray("channelList").toString();
        return jsonArr;
    }
    public static String getInfoJsonArr(String jsonStr) throws Exception {
        if (jsonStr==null){
            throw new Exception("jsonStr is null");
        }
        JSONObject object = new JSONObject(jsonStr);
        String jsonArr = object.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist").toString();
        return jsonArr;
    }
    public static <T> List<T> parseJson2List(Class<T> classz,String jsonArr) throws Exception {
        if (jsonArr==null||classz==null){
            throw new Exception("IllegeArgument: argument is null");
        }
        List<T> list ;
        list = JSON.parseArray(jsonArr,classz);

        return list;
    }
}
