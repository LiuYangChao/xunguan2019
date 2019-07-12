package com.mibo.xunguan2019.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mibo.xunguan2019.R;

/**
 * Author liuyangchao
 * Date on 2019/7/9.17:08
 */

public class PostDialog extends Dialog {

    private Context context;
    private String title = "";
    private String message = "";
    private View customView;
    private String confirmBtnText = "";
    private String cancelBtnText = "";
    private OnConfirmClickListener onConfirmClickListener;
    private OnCancelClickListener onCancelClickListener;

    public interface OnConfirmClickListener{
        void doConfirm();
    }
    public interface OnCancelClickListener{
        void doCancel();
    }

    public PostDialog(Context context){
        super(context, R.style.MyDialogStyle);
        this.context = context;
    }

    public PostDialog setTitle(String title){
        this.title = title;
        return this;
    }
    public PostDialog setMessage(String message){
        this.message = message;
        return this;
    }
    public PostDialog setView(View customView){
        this.customView = customView;
        return this;
    }
    public PostDialog setConfirmButton(String text, OnConfirmClickListener onClickListener){
        this.confirmBtnText = text;
        this.onConfirmClickListener = onClickListener;
        return this;
    }

    public PostDialog setCancelButton(String text, OnCancelClickListener onClickListener){
        this.cancelBtnText = text;
        this.onCancelClickListener = onClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_dialog, null);
        setContentView(view);

        LinearLayout container = (LinearLayout) view.findViewById(R.id.llcontainer);
        if(customView != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                container.addView(customView);
            }
        }
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvMessage = (TextView) view.findViewById(R.id.message);
        Button btnConfirm = (Button) view.findViewById(R.id.confirm);
        Button btnCancel = (Button) view.findViewById(R.id.cancel);

        tvTitle.setText(TextUtils.isEmpty(title) ? "提示" : title);
        if(TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.GONE);
        }else{
            tvMessage.setText(message);
        }
        if(! TextUtils.isEmpty(confirmBtnText) && ! TextUtils.isEmpty(cancelBtnText)) {
            btnConfirm.setText(confirmBtnText);
            btnCancel.setText(cancelBtnText);
        }

        btnConfirm.setOnClickListener(new CustomDialogClickListener());
        btnCancel.setOnClickListener(new CustomDialogClickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.4); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    private class CustomDialogClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.confirm:
                    onBackPressed();
                    if(onConfirmClickListener != null){
                        onConfirmClickListener.doConfirm();
                    }
                    break;
                case R.id.cancel:
                    onBackPressed();
                    if(onCancelClickListener != null){
                        onCancelClickListener.doCancel();
                    }
                    break;
            }
        }
    };

}
