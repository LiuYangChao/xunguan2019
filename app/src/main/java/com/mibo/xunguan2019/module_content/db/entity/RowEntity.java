package com.mibo.xunguan2019.module_content.db.entity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:59
 */

public class RowEntity {

    private int num;                                 //排序字段
    private String serverTmpId;               //服务端表单ID
    private int androidTmpId;                  //本地表单ID
    private String serverRowId;

    private List<CellEntity> cellEntityList;

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
