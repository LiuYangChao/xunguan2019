package com.mibo.xunguan2019.module_home.adapter;

import android.content.Context;

import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerAdapter;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerHolder;

/**
 * Author liuyangchao
 * Date on 2019/6/4.11:24
 */

public class TreeAdapter extends BaseRecyclerAdapter<NodeEntity> {

    public TreeAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, NodeEntity item, int position) {

    }
}
