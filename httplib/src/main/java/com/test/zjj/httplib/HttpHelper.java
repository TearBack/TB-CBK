package com.test.zjj.httplib;

/**
 * Created by Administrator on 2016/6/22.
 */
public class HttpHelper {
    private static HttpHelper httpHelper;
    private RequestQueue mQueue;
    private HttpHelper(){
        mQueue = new RequestQueue();
    }

    private static HttpHelper getInstance(){
        if (httpHelper==null){
            synchronized (HttpHelper.class){
                if (httpHelper==null){
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public static void addRequest(Request request){
        getInstance().mQueue.addRequest(request);
    }

    public static void stopWork(){
        getInstance().mQueue.stopWork();
    }
}
