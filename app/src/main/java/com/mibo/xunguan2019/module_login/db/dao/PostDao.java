package com.mibo.xunguan2019.module_login.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mibo.xunguan2019.module_login.db.entity.PostEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/10.14:15
 */
@Dao
public interface PostDao {

    @Insert
    long insertPost(PostEntity postEntity);

    @Insert
    long[] insertAllPost(List<PostEntity> postEntityList);

    @Delete
    void deletePost(List<PostEntity> postEntities);

    @Query("SELECT * FROM post")
    List<PostEntity> getAllPost();

    @Query("SELECT * FROM post where postId = :postId")
    List<PostEntity> getPostByServerId(String postId);

}
