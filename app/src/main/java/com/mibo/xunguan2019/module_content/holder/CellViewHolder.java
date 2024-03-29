/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.mibo.xunguan2019.module_content.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.model.Cell;
import com.mibo.xunguan2019.module_content.model.CellModel.FCell;
import com.mibo.xunguan2019.utils.GsonHelper;

/**
 * Created by evrencoskun on 23/10/2017.
 */

public class CellViewHolder extends AbstractViewHolder {

    public final TextView cell_textview;
    public final LinearLayout cell_container;
    private Cell cell;

    public CellViewHolder(View itemView) {
        super(itemView);
        cell_textview = (TextView) itemView.findViewById(R.id.cell_data);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_container);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        if(cell != null){
            CellEntity cellEntity = (CellEntity) cell.getData();
            if(cellEntity.getType().equals("描述项")){
                cell_textview.setText(String.valueOf(cellEntity.getContent()));
            }else if(cellEntity.getType().equals("检查项")){
                if(cellEntity.getContent() == null){
                    cell_textview.setText("---");
                } else{
                    FCell fCell = GsonHelper.convertEntity(cellEntity.getContent(), FCell.class);
                    if(fCell == null){
                        cell_textview.setText("---");
                    }else{
                        cell_textview.setText(String.valueOf(fCell.getCheck()));
                    }
                }
            }else{
                cell_textview.setText(String.valueOf(cell.getContent()));
            }
        }

        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        cell_textview.requestLayout();
    }
}