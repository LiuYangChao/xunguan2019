package com.mibo.xunguan2019.module_content.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:59
 */
@Entity(tableName = "head")
public class HeadEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;                            //类型：描述项，检查项，岗位项，文件项
    @ColumnInfo(name = "num")
    private int num;                                 //排序字段
    @ColumnInfo(name = "serverTmpId")
    private String serverTmpId;               //服务端表单ID
    @ColumnInfo(name = "androidTmpId")
    private int androidTmpId;                  //本地表单ID
    @ColumnInfo(name = "serverHeadId")
    private String serverHeadId;               //服务端ID
    @ColumnInfo(name = "tableType")
    private String tableType;                            //表头类型：表单/表单实例

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public HeadEntity(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getServerHeadId() {
        return serverHeadId;
    }

    public void setServerHeadId(String serverHeadId) {
        this.serverHeadId = serverHeadId;
    }
}

