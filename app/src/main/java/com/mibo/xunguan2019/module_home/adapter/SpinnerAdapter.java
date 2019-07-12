package com.mibo.xunguan2019.module_home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/7/10.11:34
 */

public class SpinnerAdapter extends BaseAdapter {

    private List<UserEntity> userEntityList;
    private Context mContext;

    public SpinnerAdapter(List<UserEntity> userEntityList, Context context) {
        this.userEntityList = userEntityList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return userEntityList.size();
    }

    @Override
    public Object getItem(int i) {
        return userEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        view=_LayoutInflater.inflate(R.layout.spinner_item, null);
        if(view!=null) {
            TextView textView=(TextView)view.findViewById(R.id.spinner_text);
            textView.setText(userEntityList.get(i).getAllname());
        }
        return view;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }
}
