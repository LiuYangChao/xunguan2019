package com.mibo.xunguan2019.module_content.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:59
 */
@Entity(tableName = "rowtable")
public class RowEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "num")
    private int num;                                 //排序字段
    @ColumnInfo(name = "serverTmpId")
    private String serverTmpId;               //服务端表单ID
    @ColumnInfo(name = "androidTmpId")
    private int androidTmpId;                  //本地表单ID
    @ColumnInfo(name = "serverRowId")
    private String serverRowId;
    @ColumnInfo(name = "tableType")
    private String tableType;                            //表头类型：表单/表单实例
    @ColumnInfo(name = "fileIds")
    private String fileIds;                                  //文件列表
    @Ignore
    private List<CellEntity> cellEntityList;
    @ColumnInfo(name = "postId")
    private String postId;                          //每一行的岗位Id
    @ColumnInfo(name = "userId")
    private String userId;                          //每一行的人员Id

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getServerTmpId() {
        return serverTmpId;
    }

    public void setServerTmpId(String serverTmpId) {
        this.serverTmpId = serverTmpId;
    }

    public int getAndroidTmpId() {
        return androidTmpId;
    }

    public void setAndroidTmpId(int androidTmpId) {
        this.androidTmpId = androidTmpId;
    }

    public String getServerRowId() {
        return serverRowId;
    }

    public void setServerRowId(String serverRowId) {
        this.serverRowId = serverRowId;
    }

    public List<CellEntity> getCellEntityList() {
        return cellEntityList;
    }

    public void setCellEntityList(List<CellEntity> cellEntityList) {
        this.cellEntityList = cellEntityList;
    }
}
