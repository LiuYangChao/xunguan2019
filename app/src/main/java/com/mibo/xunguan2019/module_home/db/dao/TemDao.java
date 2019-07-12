package com.mibo.xunguan2019.module_home.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mibo.xunguan2019.module_home.db.entity.TemEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/21.16:39
 */
@Dao
public interface TemDao {

    @Insert
    long insertTem(TemEntity temEntity);

    @Query("SELECT * FROM template")
    List<TemEntity> getAllTmp();

}
