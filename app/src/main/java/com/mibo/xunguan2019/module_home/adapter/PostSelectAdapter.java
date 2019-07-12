package com.mibo.xunguan2019.module_home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_home.model.PostSelectModel;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerAdapter;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerHolder;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/7/10.9:44
 */

public class PostSelectAdapter extends BaseRecyclerAdapter<PostSelectModel> {

    public PostSelectAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public PostSelectAdapter(Context context, List<PostSelectModel> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        convert(holder, list.get(position), position);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, PostSelectModel item, int position) {
        holder.setText(R.id.post_name, item.getPostEntity().getPostname());
        Spinner spinner = holder.getView(R.id.post_spinner);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(item.getUserEntityList(), context);
        spinner.setAdapter(spinnerAdapter);
        //给Spinner添加事件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //当选中某一个数据项时触发该方法
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String data = adapter.getItem(position);//从适配器中获取被选择的数据项
                //String data = list.get(position);//从集合中获取被选择的数据项
//                String data = (String)spinner.getItemAtPosition(position);//从spinner中获取被选择的数据
                UserEntity userEntity = spinnerAdapter.getUserEntityList().get(position);
                item.setSelectUser(userEntity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
