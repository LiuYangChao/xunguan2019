package com.mibo.xunguan2019.utils.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.model.CellModel.FCell;
import com.mibo.xunguan2019.module_content.model.CellModel.SCell;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerAdapter;
import com.mibo.xunguan2019.utils.adapter.base.BaseRecyclerHolder;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/27.11:13
 */

public class DialogButtonAdapter extends BaseRecyclerAdapter<SCell> {

    public static final int ITEM_INPUT = 0;
    public static final int ITEM_CHECK = 1;

    private int itemInputLayoutId;
    private int itemCheckLayoutId;

    public DialogButtonAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public DialogButtonAdapter(Context context,
                               int itemInputLayoutId,
                               int itemCheckLayoutId) {
        super(context, itemInputLayoutId);
        this.itemInputLayoutId = itemInputLayoutId;
        this.itemCheckLayoutId = itemCheckLayoutId;
    }

    public DialogButtonAdapter(Context context,
                               List<SCell> list,
                               int itemInputLayoutId,
                               int itemCheckLayoutId) {
        super(context, list, itemInputLayoutId);
        this.itemInputLayoutId = itemInputLayoutId;
        this.itemCheckLayoutId = itemCheckLayoutId;
    }


    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        convert(holder, list.get(position), position);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case ITEM_INPUT:
                view = inflater.inflate(itemInputLayoutId, parent, false);
                break;
            case ITEM_CHECK:
                view = inflater.inflate(itemCheckLayoutId, parent, false);
                break;
            default:
                view = inflater.inflate(itemInputLayoutId, parent, false);
                break;
        }
        return BaseRecyclerHolder.getRecyclerHolder(context, view);
    }

    public int getItemViewType(int position) {
        SCell sCell = list.get(position);
        if(sCell.getMeType().equals("填值")){
            return ITEM_INPUT;
        }else{
            return ITEM_CHECK;
        }
    }

    @Override
    public void convert(BaseRecyclerHolder holder, SCell item, int position) {
        switch (getItemViewType(position)){
            case ITEM_INPUT:
                EditText editText = holder.itemView.findViewById(R.id.cell_type_input_edit);
                editText.setText(item.getInputValue());
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        list.get(position).setInputValue(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
            case ITEM_CHECK:
                CheckBox checkBox = holder.itemView.findViewById(R.id.cell_type_click_checkbox);
                checkBox.setChecked(Boolean.valueOf(item.getInputValue()));
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        list.get(position).setInputValue(String.valueOf(b));
                    }
                });
                break;
        }
    }
}
