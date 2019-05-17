package com.mibo.xunguan2019.module_home.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 任务数据库
 * Author liuyangchao
 * Date on 2019/5/16.16:24
 */
@Entity(tableName = "task")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;                                     //任务名称
    @ColumnInfo(name = "xId")
    private String xId;                                         //任务ID
    @ColumnInfo(name = "serverUserId")
    private String serverUserId;                          //服务端的人员ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getxId() {
        return xId;
    }

    public void setxId(String xId) {
        this.xId = xId;
    }

    public String getServerUserId() {
        return serverUserId;
    }

    public void setServerUserId(String serverUserId) {
        this.serverUserId = serverUserId;
    }
}
