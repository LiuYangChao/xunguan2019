package com.mibo.xunguan2019.module_content.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.model.Cell;

/**
 * Author liuyangchao
 * Date on 2019/6/24.16:52
 */

public class CellFileHolder extends AbstractViewHolder {

    public final ImageView cell_type_file_image;
    public final LinearLayout cell_container;
    private Cell cell;

    public CellFileHolder(View itemView) {
        super(itemView);
        cell_type_file_image = (ImageView) itemView.findViewById(R.id.cell_type_file_image);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_type_file);
    }

    public void setCell(Cell cell) {
        this.cell = cell;

        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = 200;
    }
}

