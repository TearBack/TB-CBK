package com.test.zjj.tb_cbk.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/22.
 */
public class TabInfo implements Parcelable{
    private String channelId;
    private String name;

    public TabInfo(Parcel in) {
        channelId = in.readString();
        name = in.readString();
    }

    public TabInfo(){

    }

    public static final Creator<TabInfo> CREATOR = new Creator<TabInfo>() {
        @Override
        public TabInfo createFromParcel(Parcel in) {
            return new TabInfo(in);
        }

        @Override
        public TabInfo[] newArray(int size) {
            return new TabInfo[size];
        }
    };

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelId);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "TabInfo{" +
                "channelId='" + channelId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
