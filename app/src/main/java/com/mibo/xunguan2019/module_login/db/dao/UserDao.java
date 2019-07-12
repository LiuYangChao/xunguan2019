package com.mibo.xunguan2019.module_login.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mibo.xunguan2019.module_login.db.entity.UserEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/10.14:16
 */
@Dao
public interface UserDao {

    @Insert
    long insertUser(UserEntity userEntity);

    @Insert
    long[] insertAllUser(List<UserEntity> userEntityList);

    @Delete
    void deleteUsers(List<UserEntity> userEntities);

    @Query("SELECT * FROM user_table")
    List<UserEntity> getAllUser();

    @Query("SELECT * FROM user_table where user_name = :username")
    List<UserEntity> getUserByUsername(String username);

    @Query("SELECT * FROM user_table where endId in (:ids)")
    List<UserEntity> getUserByUserIds(List<String> ids);

}
