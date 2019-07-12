package com.mibo.xunguan2019.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerAdapter;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerHolder;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/19.15:27
 */

public class TableInstanceAdapter extends BaseRecyclerAdapter<NodeEntity> {

    private OnItemClickListener mItemClickListener;

    public TableInstanceAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.itemLayoutId = itemLayoutId;
    }

    public TableInstanceAdapter(Context context, List<NodeEntity> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, NodeEntity item, int position) {
        holder.setText(R.id.instance_name, item.getName());
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (mItemClickListener != null){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    mItemClickListener.onItemClickInstance(recyclerView, view, position);
                }
            }
        });

        convert(holder, list.get(position), position);
    }


    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_right, parent, false);
        return BaseRecyclerHolder.getRecyclerHolder(context, view);
    }

    public interface OnItemClickListener {
        void onItemClickInstance(RecyclerView parent, View view, int position);
    }

    public OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
