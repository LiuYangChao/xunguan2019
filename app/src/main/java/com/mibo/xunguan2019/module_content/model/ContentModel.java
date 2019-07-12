package com.mibo.xunguan2019.module_content.model;

import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_content.db.entity.RowEntity;

import java.util.List;

/**
 * 表单内容集合
 * Author liuyangchao
 * Date on 2019/5/16.16:50
 */

public class ContentModel {

    private String androidTableId;      //Android端表单ID
    private String padId;                      //Android端平板唯一ID
    private String instanceId;              //NodeEntity的instanceId
    private String instanceName;
    private String userIds;                    //用户Id集合
    private List<HeadEntity> headEntityList;
    private List<RowEntity> rowEntityList;

    public List<HeadEntity> getHeadEntityList() {
        return headEntityList;
    }

    public void setHeadEntityList(List<HeadEntity> headEntityList) {
        this.headEntityList = headEntityList;
    }

    public List<RowEntity> getRowEntityList() {
        return rowEntityList;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public void setRowEntityList(List<RowEntity> rowEntityList) {
        this.rowEntityList = rowEntityList;
    }

    public String getAndroidTableId() {
        return androidTableId;
    }

    public void setAndroidTableId(String androidTableId) {
        this.androidTableId = androidTableId;
    }

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
