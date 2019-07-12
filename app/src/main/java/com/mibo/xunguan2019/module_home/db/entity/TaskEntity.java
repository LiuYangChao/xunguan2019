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
    private String name;                                                    //任务名称
    @ColumnInfo(name = "serverTaskId")
    private String serverTaskId;                                         //任务ID(不用了)
    @ColumnInfo(name = "serverUserId")
    private String serverUserId;                                        //服务端的人员ID
    @ColumnInfo(name = "xid")
    private String xid;                                                         //任务ID

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

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

    public String getServerTaskId() {
        return serverTaskId;
    }

    public void setServerTaskId(String serverTaskId) {
        this.serverTaskId = serverTaskId;
    }

    public String getServerUserId() {
        return serverUserId;
    }

    public void setServerUserId(String serverUserId) {
        this.serverUserId = serverUserId;
    }
}
