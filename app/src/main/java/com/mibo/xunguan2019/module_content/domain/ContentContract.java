package com.mibo.xunguan2019.module_content.domain;

import com.mibo.xunguan2019.BasePresenter;
import com.mibo.xunguan2019.BaseView;
import com.mibo.xunguan2019.module_content.holder.TableViewModel;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/24.9:43
 */

public interface ContentContract {

    interface View extends BaseView<ContentContract.Presenter> {

        void showTable(TableViewModel mTableViewModel);

        void saveSuccess(int count);

        void showSwitchButton(boolean show);

        void initData(NodeEntity nodeEntity);

    }

    interface Presenter extends BasePresenter {

        void initTableData(NodeEntity nodeEntity, TableViewModel mTableViewModel);

        void saveTableData(TableViewModel mTableViewModel);

        void setUpload(NodeEntity nodeEntity, boolean isChecked);

        void initSwitchButton(NodeEntity nodeEntity);

        void initData(NodeEntity nodeEntity);

    }

}
