package com.mibo.xunguan2019.module_content.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.model.Cell;
import com.mibo.xunguan2019.module_content.model.CellModel.FCell;
import com.mibo.xunguan2019.utils.GsonHelper;

/**
 * Author liuyangchao
 * Date on 2019/6/24.16:50
 */

public class CellInputHolder extends AbstractViewHolder {

    public final EditText cell_type_input_edit;
    public final LinearLayout cell_container;
    private Cell cell;

    public CellInputHolder(View itemView) {
        super(itemView);
        cell_type_input_edit = (EditText) itemView.findViewById(R.id.cell_type_input_edit);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_type_input);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        CellEntity cellEntity = (CellEntity) cell.getData();
        FCell fCell = GsonHelper.convertEntity(cellEntity.getContent(), FCell.class);
        String inputStr = fCell.getCheck().get(0).getInputValue();
        cell_type_input_edit.setText(inputStr);
        cell_type_input_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fCell.getCheck().get(0).setInputValue(charSequence.toString());
                cellEntity.setContent(GsonHelper.object2JsonStr(fCell));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = 200;
    }
}
