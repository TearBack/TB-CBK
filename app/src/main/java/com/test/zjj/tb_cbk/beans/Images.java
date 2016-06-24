package com.test.zjj.tb_cbk.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Images implements Parcelable {
    private String height;
    private String width;

    public Images() {
    }

    protected Images(Parcel in) {
        height = in.readString();
        width = in.readString();
        url = in.readString();
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    private String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(height);
        dest.writeString(width);
        dest.writeString(url);
    }
}
