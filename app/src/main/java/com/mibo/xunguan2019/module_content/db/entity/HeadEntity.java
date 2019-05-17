package com.mibo.xunguan2019.module_content.db.entity;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:59
 */

public class HeadEntity {

    private String name;
    private String type;                            //类型：描述项，检查项，岗位项，文件项
    private int num;                                 //排序字段
    private String serverTmpId;               //服务端表单ID
    private int androidTmpId;                  //本地表单ID
    private String serverHeadId;                //服务端ID

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
}

