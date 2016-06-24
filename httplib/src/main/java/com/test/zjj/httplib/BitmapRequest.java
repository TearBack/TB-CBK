package com.test.zjj.httplib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2016/6/22.
 */
public class BitmapRequest extends Request {
    public BitmapRequest(String url, String method, CallBack callBack) {
        super(url, method, callBack);
    }

    @Override
    public void onDispatchContent(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        onRespond(bitmap);
    }
}
