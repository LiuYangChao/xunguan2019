package com.mibo.xunguan2019.module_content.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.model.Cell;

/**
 * Author liuyangchao
 * Date on 2019/6/24.16:51
 */

public class CellClickHolder extends AbstractViewHolder {

    public final CheckBox cell_type_click_checkbox;
    public final LinearLayout cell_container;
    private Cell cell;

    public CellClickHolder(View itemView) {
        super(itemView);
        cell_type_click_checkbox = (CheckBox) itemView.findViewById(R.id.cell_type_click_checkbox);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_type_click);
    }

    public void setCell(Cell cell) {
        this.cell = cell;

        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = 200;
    }
}
