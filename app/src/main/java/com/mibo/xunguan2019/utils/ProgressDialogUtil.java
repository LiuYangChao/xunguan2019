package com.mibo.xunguan2019.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Author liuyangchao
 * Date on 2019/5/17.16:06
 */

public class ProgressDialogUtil {

    private static ProgressDialog mDialog;

    public static void show(Context context, String title, String msg){
        if(mDialog == null){
            mDialog = new ProgressDialog(context);
            mDialog.setCancelable(false);           //无法点击取消
        }
        mDialog.setTitle(title);
        mDialog.setMessage(msg);
        if( !mDialog.isShowing() ){
            mDialog.show();
        }
    }

    public static void showold(Context context, String title, String msg){
        if(mDialog != null){
            mDialog = null;
        }
        mDialog = new ProgressDialog(context);
        mDialog.setCancelable(false);           //无法点击取消
        mDialog.setTitle(title);
        mDialog.setMessage(msg);
        if( !mDialog.isShowing() ){
            mDialog.show();
        }
    }

    public static void show(Context context, String msg){
        show(context, "", msg);
    }

    public static void dismiss(){
        if( mDialog == null){
            return;
        }
        if( mDialog.isShowing() ){
            mDialog.dismiss();
        }
    }

}
