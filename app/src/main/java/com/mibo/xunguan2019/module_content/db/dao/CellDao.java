package com.mibo.xunguan2019.module_content.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_content.db.entity.RowEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/19.10:18
 */
@Dao
public interface CellDao {

    @Insert
    long insertCell(CellEntity cellEntity);

    @Insert
    long[] insertAllCell(List<CellEntity> cellEntities);

    @Delete
    void deleteCells(List<CellEntity> cellEntities);

    @Query("SELECT * FROM cell")
    List<CellEntity> getAllCell();

    @Query("SELECT * FROM cell where androidTmpId = :androidTmpId and tableType = :tableType and rowNum = :rowNum ORDER BY colNum ASC")
    List<CellEntity> getTableCells(String androidTmpId, String tableType, String rowNum);

    @Query("SELECT * FROM cell where serverTmpId = :serverTmpId and tableType = :tableType")
    List<CellEntity> getCellsByTmpId(String serverTmpId, String tableType);

    @Query("SELECT * FROM cell where androidTmpId = :androidId")
    List<CellEntity> getCellsByAndroidId(String androidId);

    @Update
     int updateCells(List<CellEntity> cellEntities);

//    @Query("SELECT * FROM cell where serverUserId = :username")
//    List<CellEntity> getCellsByUserName(String username);

}
