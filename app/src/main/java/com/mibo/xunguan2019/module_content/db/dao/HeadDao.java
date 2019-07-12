package com.mibo.xunguan2019.module_content.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/19.10:18
 */
@Dao
public interface HeadDao {

    @Insert
    long insertHead(HeadEntity headEntity);

    @Insert
    long[] insertAllHead(List<HeadEntity> headEntities);

    @Delete
    void deleteHeads(List<HeadEntity> headEntities);

    @Query("SELECT * FROM head")
    List<HeadEntity> getAllHead();

    @Query("SELECT * FROM head where serverTmpId = :tmpId and tableType = :tableType")
    List<HeadEntity> getHeadsByTmpId(String tmpId, String tableType);

    @Query("SELECT * FROM head where androidTmpId = :androidId")
    List<HeadEntity> getHeadsByAndroidId(String androidId);

    //根据serverTmpId+tableType查询某张表单的表头
    @Query("SELECT * FROM head where androidTmpId = :androidTmpId AND tableType = '表单实例'")
    List<HeadEntity> getTableHeads(String androidTmpId);

}
