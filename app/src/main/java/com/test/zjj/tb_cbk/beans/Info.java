package com.test.zjj.tb_cbk.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Info implements Parcelable {
    private String title;
    private String desc;
    private String html;
    private String source;
    private String pubDate;
    private String nid;
    //    private Images[] imageurls;
    private String imageurl;
    private String channelName;
    private String link;
    private List<String> contentStrList;

    public Info(){}

    @Override
    public String toString() {
        return "Info{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", html='" + html + '\'' +
                ", source='" + source + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", nid='" + nid + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", channelName='" + channelName + '\'' +
                ", link='" + link + '\'' +
                ", contentStrList=" + contentStrList +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getContentStrList() {
        return contentStrList;
    }

    public void setContentStrList(List<String> contentStrList) {
        this.contentStrList = contentStrList;
    }

    protected Info(Parcel in) {
        title = in.readString();
        desc = in.readString();
        html = in.readString();
        source = in.readString();
        pubDate = in.readString();
        nid = in.readString();
        imageurl = in.readString();
        channelName = in.readString();
        link = in.readString();
        contentStrList = in.createStringArrayList();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(html);
        dest.writeString(source);
        dest.writeString(pubDate);
        dest.writeString(nid);
        dest.writeString(imageurl);
        dest.writeString(channelName);
        dest.writeString(link);
        dest.writeStringList(contentStrList);
    }
}
