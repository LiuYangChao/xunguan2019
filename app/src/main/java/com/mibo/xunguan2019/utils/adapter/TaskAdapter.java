package com.mibo.xunguan2019.utils.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerAdapter;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author liuyangchao
 * Date on 2019/6/1.11:31
 */

public class TaskAdapter extends BaseRecyclerAdapter<NodeEntity> {

    public static final int ITEM_TASK = 0;
    public static final int ITEM_ITEM = 1 ;
    public static final int ITEM_TABLE = 2;

    private int itemTaskLayoutId;
    private int itemItemLayoutId;
    private int itemTableLayoutId;

    private OnItemClickListener mItemClickListener;

    public TaskAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public TaskAdapter(Context context, List<NodeEntity> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    public TaskAdapter(Context context,
                       int itemTaskLayoutId,
                       int itemItemLayoutId,
                       int itemTableLayoutId) {
        super(context, itemTaskLayoutId);
        this.itemLayoutId = itemTaskLayoutId;
        this.itemTaskLayoutId = itemTaskLayoutId;
        this.itemItemLayoutId = itemItemLayoutId;
        this.itemTableLayoutId = itemTableLayoutId;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, NodeEntity item, int position) {
        switch (getItemViewType(position)){
            case ITEM_TASK:
                holder.setText(R.id.task_name, item.getName());
                ImageView imageView = holder.getView(R.id.task_image);
                if(item.getExpandStatus()==1){
                    imageView.setImageResource(R.mipmap.icon_add_white);
                }else{
                    imageView.setImageResource(R.mipmap.icon_delete_white);
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (item.getExpandStatus()){
                            case 1:             //展开--->缩起，隐藏下面的子节点
                                list.addAll(position+1, item.getCacheNodeList());
                                item.setExpandStatus(2);
                                notifyDataSetChanged();
                                break;
                            case 2:             //缩起--->展开，显示下面的子节点
                                list.removeAll(item.getCacheNodeList());
                                item.setExpandStatus(1);
                                notifyDataSetChanged();
                                break;
                            default:
                                item.setNodeList(null);
                                item.setExpandStatus(2);
                                notifyDataSetChanged();
                                break;
                        }
                    }
                });
                break;
            case ITEM_ITEM:
                holder.setText(R.id.item_name, item.getName());
                break;
            case ITEM_TABLE:
                holder.setText(R.id.table_name, item.getName());
                break;
        }
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
                    mItemClickListener.onItemClick(recyclerView, view, position);
                }
            }
        });

        convert(holder, list.get(position), position);
    }

    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case ITEM_TASK:
                view = inflater.inflate(itemTaskLayoutId, parent, false);
                break;
            case ITEM_ITEM:
                view = inflater.inflate(itemItemLayoutId, parent, false);
                break;
            case ITEM_TABLE:
                view = inflater.inflate(itemTableLayoutId, parent, false);
                break;
            default:
                view = inflater.inflate(itemTaskLayoutId, parent, false);
                break;
        }
        return BaseRecyclerHolder.getRecyclerHolder(context, view);
    }

    public int getItemViewType(int position) {
        NodeEntity nodeEntity = list.get(position);
        if(nodeEntity.getLayer() == 1){
            return ITEM_TASK;
        }else if(nodeEntity.getLayer() == 2){
            return ITEM_ITEM;
        }else if(nodeEntity.getLayer() == 3){
            return ITEM_TABLE;
        }else{
            return ITEM_TASK;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

}
