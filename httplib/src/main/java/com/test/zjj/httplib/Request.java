package com.test.zjj.httplib;

/**
 * Created by Administrator on 2016/6/22.
 */
public abstract class Request<T> {
    private String url;
    private String method;
    private CallBack callBack;

    public Request(String url, String method, CallBack callBack) {
        this.url = url;
        this.method = method;
        this.callBack = callBack;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public interface CallBack<T>{
        public void onResoponse(T t);
    }

    public void onRespond(T t){
        callBack.onResoponse(t);
    }

    public abstract void onDispatchContent(byte[] bytes);
}
