package com.mibo.xunguan2019.module_home.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 所有任务树的结构表
 * Author liuyangchao
 * Date on 2019/5/10.10:56
 */
@Entity(tableName = "node")
public class NodeEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;                                     //本节点的名称
    @ColumnInfo(name = "layer")
    private int layer;                                       //本节点的层级
    @ColumnInfo(name = "serverNodeId")
    private String serverNodeId;                                         //服务端的主键ID
    @ColumnInfo(name = "parentId")
    private int parentId;                                              //父亲节点ID
    @ColumnInfo(name = "type")
    private String type;                                       //服务端的模型类型（表单节点）任务/工作项/表单/表单实例
    @ColumnInfo(name = "downloadStatus")
    private String downloadStatus;                           //表单下载状态  0表单模板待下载状态   1表单模板已下载状态    2表单实例待上传
    @ColumnInfo(name = "inputStatus")
    private String inputStatus;                                       //表单填写状态： 1未点击   2已点击开始填写    3填写完成可以上传    4已经上传完成
    @ColumnInfo(name = "taskId")
    private String taskId;                                       //任务服务端ID
    @Ignore
    private List<NodeEntity> nodeList;
    @Ignore
    private List<NodeEntity> cacheNodeList;               //缓存中的下级表单状态，初始状态都是全展开
    @Ignore
    private int expandStatus;                                   //该节点展开状态，1展开，2缩起,  3,中立
    @Ignore
    private boolean clickStatus;                                        //该节点是否点击（正常情况下点击节点之后要先消除其他节点状态）

    //针对表单模板的岗位选择问题，再加两个字段
    @ColumnInfo(name = "postId")
    private String postId;
    @ColumnInfo(name = "postName")
    private String postName;
    @Ignore
    private String postType;                                    //0表示普通岗位  1表示综合管理岗

    public NodeEntity() {
        this.expandStatus = 2;
        this.clickStatus = false;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInputStatus() {
        return inputStatus;
    }

    public void setInputStatus(String inputStatus) {
        this.inputStatus = inputStatus;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public int getExpandStatus() {
        return expandStatus;
    }

    public void setExpandStatus(int expandStatus) {
        this.expandStatus = expandStatus;
    }

    public boolean isClickStatus() {
        return clickStatus;
    }

    public void setClickStatus(boolean clickStatus) {
        this.clickStatus = clickStatus;
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

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getServerNodeId() {
        return serverNodeId;
    }

    public void setServerNodeId(String serverNodeId) {
        this.serverNodeId = serverNodeId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public List<NodeEntity> getCacheNodeList() {
        return cacheNodeList;
    }

    public void setCacheNodeList(List<NodeEntity> cacheNodeList) {
        this.cacheNodeList = cacheNodeList;
    }

}
