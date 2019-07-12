package com.mibo.xunguan2019.module_home.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Author liuyangchao
 * Date on 2019/5/20.16:23
 */
@Dao
public interface NodeDao {

    @Insert
    long insertNode(NodeEntity nodeEntity);

    @Query("SELECT * FROM node WHERE downloadStatus = '0' AND layer = '3'")
    Flowable<List<NodeEntity>> getDownInstanceRx();

    @Query("SELECT * FROM node where serverNodeId = :serverNodeId AND type = :type")
    List<NodeEntity> getNodeByServerNodeId(String serverNodeId, String type);

    @Query("SELECT * FROM node where parentId = :taskId")
    List<NodeEntity> getNodeBytaskId(String taskId);

    @Query("SELECT * FROM node where parentId = :itemId")
    List<NodeEntity> getNodeByItemId(String itemId);

    //测试表单
    @Query("SELECT * FROM node where serverNodeId = '131' and type = '表单实例'")
    List<NodeEntity> getNeedTestTables();

    //查询需要上传的表单
    @Query("SELECT * FROM node where inputStatus = '3'")
    List<NodeEntity> getNeedUploadTables();
    //查询表单模板
    @Query("SELECT * FROM node where parentId = :itemId AND type = '表单'")
    List<NodeEntity> getTemplate(String itemId);
    //查询表单实例
    @Query("SELECT * FROM node where parentId = :itemId AND type = '表单实例'")
    List<NodeEntity> getInstance(String itemId);

    @Query("SELECT * FROM node where parentId = :instanceId")
    List<NodeEntity> getNodeByInstanceId(String instanceId);

    @Query("SELECT * FROM node")
    List<NodeEntity> getAllNode();

    @Query("SELECT * FROM node WHERE type = :type")
    List<NodeEntity> getNodeByType(String type);

    @Query("SELECT * FROM node WHERE layer = '3' AND taskId = :taskId AND downloadStatus = '0' And type = '表单'")
    List<NodeEntity> getDownInstance(String taskId);

    @Query("SELECT * FROM node WHERE layer = '3' AND downloadStatus = '2' And type = '表单实例'")
    List<NodeEntity> getUploadInstance();

    @Insert
    long[] insertAllNode(List<NodeEntity> nodeEntities);

    @Update
    int updateNodes(NodeEntity nodeEntity);

    @Query("SELECT * FROM node WHERE id = :id LIMIT 1")
    NodeEntity getNodeById(int id);

}
