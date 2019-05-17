package com.mibo.xunguan2019.module_content.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:58
 */
@Entity(tableName = "ins")
public class InsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;                                     //表单名称
    @ColumnInfo(name = "nodeId")
    private String nodeId;                                  //所属节点ID
    @ColumnInfo(name = "serverTmpId")
    private String serverTmpId;                          //服务端的表单ID
    @ColumnInfo(name = "serverInsId")
    private String serverInsId;                          //服务端的实例ID
    @ColumnInfo(name = "androidTmpId")
    private String androidTmpId;                       //android的表单模板ID
    @ColumnInfo(name = "upload")
    private boolean upload;                                //是否上传

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

    public String getServerInsId() {
        return serverInsId;
    }

    public void setServerInsId(String serverInsId) {
        this.serverInsId = serverInsId;
    }

    public String getAndroidTmpId() {
        return androidTmpId;
    }

    public void setAndroidTmpId(String androidTmpId) {
        this.androidTmpId = androidTmpId;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }
}
