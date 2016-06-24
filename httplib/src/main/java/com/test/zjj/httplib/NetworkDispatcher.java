package com.test.zjj.httplib;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/6/22.
 */
public class NetworkDispatcher extends Thread {
    private boolean flag = true;
    private BlockingQueue<Request> requestQueue;
    public NetworkDispatcher(BlockingQueue<Request> requestQueue){
        this.requestQueue = requestQueue;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag&&!isInterrupted()){
            if (requestQueue!=null){
                try {
                    Request request = requestQueue.take();
                    byte[] data = getNetworkData(request);
                    if (data!=null) {
                        request.onDispatchContent(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] getNetworkData(Request request) throws Exception {
        if (TextUtils.isEmpty(request.getUrl())){
            throw new Exception("Url is null!");
        }

        if (request.getMethod().equals("GET")){
            return getNetworkDataByGet(request);
        }

        if (request.getMethod().equals("POST")){
            return getNetworkDataByPost(request);
        }
        return null;
    }

    private byte[] getNetworkDataByPost(Request request) {
        return null;
    }

    private byte[] getNetworkDataByGet(Request request) throws Exception {
        Log.i("test", "-->getNetworkDataByGet: url:" + request.getUrl());
        URL url = new URL(request.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestProperty("apikey","961de81d975d7e6cc0945352267dcdce");
        conn.connect();
        int code = conn.getResponseCode();
        if (code!=200){
            throw new Exception("code is error!  code = " + code);
        }
        InputStream iis = conn.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[2048];
        int len = 0;
        while ((len=iis.read(buf))!=-1){
            baos.write(buf,0,len);
        }
        return baos.toByteArray();
    }

}
