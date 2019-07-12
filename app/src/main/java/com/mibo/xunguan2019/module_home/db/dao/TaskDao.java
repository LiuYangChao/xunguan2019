package com.mibo.xunguan2019.module_home.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/10.14:23
 */
@Dao
public interface TaskDao {

    @Insert
    long insertTask(TaskEntity taskEntity);

    @Insert
    long[] insertAllTask(List<TaskEntity> taskEntities);

    @Delete
    void deleteTasks(List<TaskEntity> taskEntities);

    @Query("SELECT * FROM task")
    List<TaskEntity> getAllTask();

    @Query("SELECT * FROM task where serverUserId = :serverUserId")
    List<TaskEntity> getTasksByUserServerId(String serverUserId);

}
