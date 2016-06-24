package com.test.zjj.httplib;

/**
 * Created by Administrator on 2016/6/22.
 */
public class StringRequest extends Request {

    public StringRequest(String url, String method, CallBack callBack) {

        super(url, method, callBack);
    }

    @Override
    public void onDispatchContent(byte[] bytes) {
        onRespond(new String(bytes));
    }
}
