package com.cr.gankio.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author RUI CAI
 * @date 2018/4/6
 */
@Entity(tableName = "gankio")
public class GankNews {
    @PrimaryKey
    @NonNull
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private Boolean used;
    private String who;

    public GankNews(String _id,
                    String createdAt,
                    String desc,
                    String publishedAt,
                    String type,
                    String url,
                    Boolean used,
                    String who) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }
}
