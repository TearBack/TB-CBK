package com.test.zjj.tb_cbk.beans;

/**
 * Created by Administrator on 2016/6/23.
 */
public class Collection {
    private String title;
    private long saveTime;

    public Collection(String title, long saveTime) {
        this.title = title;
        this.saveTime = saveTime;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "title='" + title + '\'' +
                ", saveTime=" + saveTime +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }
}
