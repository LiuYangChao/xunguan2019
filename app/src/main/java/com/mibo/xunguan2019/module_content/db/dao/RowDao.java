package com.mibo.xunguan2019.module_content.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_content.db.entity.RowEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/19.10:19
 */
@Dao
public interface RowDao {

    @Insert
    long insertRow(RowEntity rowEntity);

    @Insert
    long[] insertAllRow(List<RowEntity> rowEntities);

    @Delete
    void deleteRows(List<RowEntity> rowEntities);

    @Query("SELECT * FROM rowtable")
    List<RowEntity> getAllRow();

    @Query("SELECT * FROM rowtable where serverTmpId = :serverTmpId and tableType = :tableType")
    List<RowEntity> getRowsByTmpId(String serverTmpId, String tableType);

    @Query("SELECT * FROM rowtable where androidTmpId = :androidTmpId")
    List<RowEntity> getRowsByAndroidId(String androidTmpId);

}
