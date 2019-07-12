package com.mibo.xunguan2019.module_home.model;

import com.mibo.xunguan2019.module_login.db.entity.PostEntity;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;

import java.util.List;

/**
 * 下拉选择框
 * Author liuyangchao
 * Date on 2019/7/10.9:45
 */

public class PostSelectModel {

    private PostEntity postEntity;
    private List<UserEntity> userEntityList;
    private UserEntity selectUser;                      //选择的这个用户

    public PostEntity getPostEntity() {
        return postEntity;
    }

    public void setPostEntity(PostEntity postEntity) {
        this.postEntity = postEntity;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public UserEntity getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(UserEntity selectUser) {
        this.selectUser = selectUser;
    }
}
