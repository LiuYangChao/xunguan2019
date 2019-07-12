package com.mibo.xunguan2019.module_home.domain;

import com.mibo.xunguan2019.BasePresenter;
import com.mibo.xunguan2019.BaseView;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.model.PostSelectModel;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/19.11:35
 */

public interface HomeContract {

    interface View extends BaseView<HomeContract.Presenter> {

        /**
         * 1没有关联岗位人员  2生成表格实例
         * @param code
         */
        void showMessage(int code);

        void notifyTreeLeft(List<NodeEntity> nodeEntityList);

        void notifyTreeMiddle(List<NodeEntity> nodeEntityList);

        void notifyTreeRight(List<NodeEntity> nodeEntityList);

        void notifyPostDialog(List<PostSelectModel> postSelectModelList);

    }

    interface Presenter extends BasePresenter {

        void init(String username);

        void initTemplateTree(NodeEntity nodeEntity);

        void initInstanceTree(NodeEntity nodeEntity);

        void initNewInstance(NodeEntity nodeEntity);

        /**
         * 初始化岗位-人员选择框的数据适配器
         * @param templateNodeEntity    表单模板节点
         */
        void initPostDialog(NodeEntity templateNodeEntity);

        void distributionPerson(NodeEntity templateNodeEntity, List<PostSelectModel> postSelectModelList);

    }

}
