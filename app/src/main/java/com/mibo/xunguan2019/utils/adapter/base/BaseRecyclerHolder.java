package com.mibo.xunguan2019.utils.adapter.base;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibo.xunguan2019.R;

/**
 * Author liuyangchao
 * Date on 2019/6/1.14:05
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;

    public BaseRecyclerHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        views = new SparseArray<>(8);
    }

    public static BaseRecyclerHolder getRecyclerHolder(Context context, View itemView){
        return new BaseRecyclerHolder(context, itemView);
    }

    public SparseArray<View> getViews() {
        return views;
    }

    /**
     * 首先从缓存中加载控件，没有就加入缓存队列
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            //根据viewId查找对应的控件
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRecyclerHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        if(text == null){
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
        return this;
    }

    //启动动画
    public BaseRecyclerHolder startAnimation(int viewId){
        ImageView tv = getView(viewId);
        tv.setImageResource(R.drawable.loading_more);
        AnimationDrawable animationDrawable = (AnimationDrawable) tv.getDrawable();
        animationDrawable.start();
        return this;
    }


}
