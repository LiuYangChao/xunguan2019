package com.mibo.xunguan2019.module_content.holder;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.custom.MutiDialog;
import com.mibo.xunguan2019.module_content.ContentActivity;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.model.Cell;
import com.mibo.xunguan2019.module_content.model.CellModel.FCell;
import com.mibo.xunguan2019.module_content.model.CellModel.SCell;
import com.mibo.xunguan2019.utils.GsonHelper;
import com.mibo.xunguan2019.utils.adapter.DialogButtonAdapter;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/24.16:50
 */

public class CellButtonHolder extends AbstractViewHolder {

    public final Button cell_button;
    public final LinearLayout cell_container;
    private Cell cell;
    private Context mContext;

    public CellButtonHolder(View itemView) {
        super(itemView);
        cell_button = (Button) itemView.findViewById(R.id.cell_type_button_button);
        cell_container = (LinearLayout) itemView.findViewById(R.id.cell_type_button);
        //设置监听器
        cell_button.setOnClickListener(btnClickListener);
    }

    public void setCell(Cell cell, Context context) {
        this.cell = cell;
        this.mContext = context;
        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = 200;
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CellEntity cellEntity = (CellEntity) cell.getData();
            FCell fCell = GsonHelper.convertEntity(cellEntity.getContent(), FCell.class);
            List<SCell> sCellList = fCell.getCheck();

            MutiDialog mutiDialog = new MutiDialog(mContext, sCellList);
            mutiDialog.setYesOnclickListener("", new MutiDialog.onYesOnclickListener() {
                @Override
                public void onYesClick(List<SCell> sCellList) {
                    fCell.setCheck(sCellList);
                    ((CellEntity)cell.getContent()).setContent(GsonHelper.object2JsonStr(fCell));
                    mutiDialog.dismiss();
                }
            });
            mutiDialog.setNoOnclickListener("", new MutiDialog.onNoOnclickListener() {
                @Override
                public void onNoClick(List<SCell> sCellList) {
                    mutiDialog.dismiss();
                }
            });
            mutiDialog.show();

//            CustomDialog.show((ContentActivity)mContext, R.layout.dialog_button, new CustomDialog.OnBindView() {
//                @Override
//                public void onBind(CustomDialog dialog, View v) {
//                    RecyclerView recyclerView = v.findViewById(R.id.dialog_button_list);
//                    DialogButtonAdapter dialog_button_adapter = new DialogButtonAdapter(mContext, sCellList,
//                            R.layout.cell_type_input, R.layout.cell_type_click);
//                    LinearLayoutManager manager_home = new LinearLayoutManager(mContext);
//                    manager_home.setOrientation(LinearLayout.VERTICAL);
//                    recyclerView.setLayoutManager(manager_home);
//                    recyclerView.setAdapter(dialog_button_adapter);
//                }
//            });

        }
    };

}
