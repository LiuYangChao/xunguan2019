package com.mibo.xunguan2019.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.ContentActivity;
import com.mibo.xunguan2019.module_content.model.CellModel.SCell;
import com.mibo.xunguan2019.utils.adapter.DialogButtonAdapter;

import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/28.9:04
 */

public class MutiDialog extends Dialog {

    RecyclerView recyclerView;
    Button btn_ok;
    Button btn_cancel;
    List<SCell> sCellList;
    Context mContext;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public interface onYesOnclickListener {
        void onYesClick(List<SCell> sCellList);
    }

    public interface onNoOnclickListener {
        void onNoClick(List<SCell> sCellList);
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public MutiDialog(@NonNull Context context) {
        super(context);
    }

    public MutiDialog(@NonNull Context context, List<SCell> sCellList) {
        super(context);
        this.mContext = context;
        this.sCellList = sCellList;
    }

    public MutiDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MutiDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_button);
        setCanceledOnTouchOutside(false);

        initView();
        initData();
        initEvent();
    }

    private void initView(){
        recyclerView = findViewById(R.id.dialog_button_list);
        btn_ok = findViewById(R.id.dialog_button_ok);
        btn_cancel = findViewById(R.id.dialog_button_cancel);
    }
    private void initData(){
        RecyclerView recyclerView = this.findViewById(R.id.dialog_button_list);
        DialogButtonAdapter dialog_button_adapter = new DialogButtonAdapter(mContext, sCellList,
                R.layout.cell_type_input, R.layout.cell_type_click);
//        dialog_button_adapter.setOnItemClickListener((ContentActivity)mContext);
        LinearLayoutManager manager_home = new LinearLayoutManager(mContext);
        manager_home.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager_home);
        recyclerView.setAdapter(dialog_button_adapter);
    }
    private void initEvent(){
        //设置确定按钮被点击后，向外界提供监听
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick(sCellList);
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick(sCellList);
                }
            }
        });
    }

}
