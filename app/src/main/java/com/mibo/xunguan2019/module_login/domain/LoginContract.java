package com.mibo.xunguan2019.module_login.domain;

import com.mibo.xunguan2019.BasePresenter;
import com.mibo.xunguan2019.BaseView;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.utils.GlobalHandler;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/5/17.14:49
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void showInterError();

        void showEnd(String endStr);

        //处理离线登录时候的判断  1：登录成功    2：登录失败
        void solveLogin(int code);

    }

    interface Presenter extends BasePresenter{

        void syncData(String username, String password, GlobalHandler handler);

        void syncNode(GlobalHandler mHandler);

        void syncContent(GlobalHandler mHandler, List<NodeEntity> nodeEntities);

        void uploadContent(GlobalHandler mHandler);

    }

}
