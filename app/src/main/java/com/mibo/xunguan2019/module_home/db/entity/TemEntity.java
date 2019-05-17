package com.mibo.xunguan2019.module_home.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 表单模板数据库
 * Author liuyangchao
 * Date on 2019/5/10.10:58
 */
@Entity(tableName = "template")
public class TemEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;                                     //表单名称
    @ColumnInfo(name = "nodeId")
    private String nodeId;                                  //所属节点ID
    @ColumnInfo(name = "serverTmpId")
    private String serverTmpId;                          //服务端的表单ID
    @ColumnInfo(name = "type")
    private String type;                                       //普通表，综合表

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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getServerTmpId() {
        return serverTmpId;
    }

    public void setServerTmpId(String serverTmpId) {
        this.serverTmpId = serverTmpId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
