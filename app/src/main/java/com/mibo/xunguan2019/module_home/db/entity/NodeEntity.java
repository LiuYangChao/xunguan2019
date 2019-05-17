package com.mibo.xunguan2019.module_home.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * 所有任务树的结构表
 * Author liuyangchao
 * Date on 2019/5/10.10:56
 */
@Entity(tableName = "node")
public class NodeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;                                     //本节点的名称
    @ColumnInfo(name = "layer")
    private String layer;                                       //本节点的层级
    @ColumnInfo(name = "xId")
    private String xId;                                         //服务端的主键ID
    @ColumnInfo(name = "pId")
    private int pId;                                              //父亲节点ID
    @ColumnInfo(name = "type")
    private String type;                                       //服务端的模型类型

    private List<NodeEntity> nodeList;

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

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getxId() {
        return xId;
    }

    public void setxId(String xId) {
        this.xId = xId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NodeEntity> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeEntity> nodeList) {
        this.nodeList = nodeList;
    }
}
