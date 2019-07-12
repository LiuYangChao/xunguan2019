package com.mibo.xunguan2019.module_content.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:59
 */
@Entity(tableName = "cell")
public class CellEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "type")
    private String type;                            //类型：描述项，检查项，岗位项，文件项
    @ColumnInfo(name = "rowNum")
    private int rowNum;                                 //行排序字段
    @ColumnInfo(name = "colNum")
    private int colNum;                                 //行排序字段
    @ColumnInfo(name = "serverTmpId")
    private String serverTmpId;               //服务端表单ID
    @ColumnInfo(name = "androidTmpId")
    private int androidTmpId;                  //本地表单ID
    @ColumnInfo(name = "serverHeadId")
    private String serverHeadId;                //服务端ID
    @ColumnInfo(name = "tableType")
    private String tableType;                      //表头类型：表单/表单实例
    @ColumnInfo(name = "serverCellId")
    private String serverCellId;
    @Ignore
    private int cellType;                             //单元格类型CELL_DISPLAY/CELL_INPUT/CELL_CLICK/CELL_BUTTON/CELL_FILE  1/2/3/4/5

    public String getServerCellId() {
        return serverCellId;
    }

    public void setServerCellId(String serverCellId) {
        this.serverCellId = serverCellId;
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
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
