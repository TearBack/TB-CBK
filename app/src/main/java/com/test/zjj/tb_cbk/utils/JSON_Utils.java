package com.test.zjj.tb_cbk.utils;

import com.test.zjj.tb_cbk.beans.Info;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class JSON_Utils {

    public static String getChannelJsonArr(String jsonStr) throws Exception {
        if (jsonStr == null) {
            throw new Exception("jsonStr is null");
        }
        JSONObject object = new JSONObject(jsonStr);
        String jsonArr = object.getJSONObject("showapi_res_body").getJSONArray("channelList").toString();
        return jsonArr;
    }

    public static String getInfoJsonArr(String jsonStr) throws Exception {
        if (jsonStr == null) {
            throw new Exception("jsonStr is null");
        }
        JSONObject object = new JSONObject(jsonStr);
        String jsonArr = object.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist").toString();
        return jsonArr;
    }

    public static List<Info> parseJson2List(String jsonArr) throws Exception {
        if (jsonArr == null) {
            throw new Exception("IllegeArgument: argument is null");
        }
        List<Info> list;
        JSONObject object = null;
        Info info = null;
        JSONArray array = new JSONArray(jsonArr);
        list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            info = new Info();
            object = array.getJSONObject(i);

            JSONArray mArr = object.getJSONArray("allList");
            String htmlStr = "";
            if (mArr.length() != 0) {
                for (int j = 0; j < mArr.length(); j++) {
                    htmlStr += mArr.optString(j);
                }
                info.setHtml(htmlStr);
            }

            JSONArray iArr = object.getJSONArray("imageurls");
            if (iArr.length() != 0) {
                info.setImageurl(iArr.getJSONObject(0).optString("url"));
            }

            info.setChannelName(object.optString("channelName"));
            info.setDesc(object.optString("desc"));
            info.setLink(object.optString("link"));
            info.setPubDate(object.optString("pubDate"));
            info.setSource(object.optString("source"));
            info.setTitle(object.optString("title"));

            list.add(info);
        }
//        list = JSON.parseArray(jsonArr,classz);

        return list;
    }
}
