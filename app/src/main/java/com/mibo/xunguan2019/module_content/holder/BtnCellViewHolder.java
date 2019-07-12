package com.mibo.xunguan2019.module_content.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mibo.xunguan2019.R;

/**
 * Author liuyangchao
 * Date on 2019/6/20.9:24
 */

public class BtnCellViewHolder extends AbstractViewHolder {
    public final LinearLayout cell_container;
    public final Button cell_btn;

    public BtnCellViewHolder(View itemView) {
        super(itemView);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_container);
        cell_btn = (Button) itemView.findViewById(R.id.cell_btn);
    }
}
