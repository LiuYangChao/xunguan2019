package com.mibo.xunguan2019.module_login.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 岗位表
 * Author liuyangchao
 * Date on 2019/5/10.10:36
 */
@Entity(tableName = "post")
public class PostEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "postname")
    private String postname;
    @ColumnInfo(name = "postId")
    private String postId;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "userId")
    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
