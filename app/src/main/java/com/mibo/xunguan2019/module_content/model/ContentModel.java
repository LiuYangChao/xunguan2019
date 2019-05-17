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

    public void setRowEntityList(List<RowEntity> rowEntityList) {
        this.rowEntityList = rowEntityList;
    }
}
