package com.mibo.xunguan2019.module_login.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author liuyangchao
 * Date on 2019/5/10.10:11
 */
@Entity(tableName = "uuser")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private String post;
    private int id;
    private int id;



}
