package com.mibo.xunguan2019.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Author liuyangchao
 * Date on 2019/6/15.15:10
 */

public class GlobalHandler extends Handler {

    private GlobalHandler(){
        Log.e(Tag,"GlobalHandler创建");
    }
    private static class Holder{
        private static final GlobalHandler HANDLER = new GlobalHandler();
    }
    public static GlobalHandler getInstance(){
        return Holder.HANDLER;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(getListener() != null){
            getListener().handleMsg(msg);
        }else{
            Log.e(Tag,"请传入HandleMsgListener对象");
        }
    }

    public HandleMsgListener getListener() {
        return listener;
    }

    public void setListener(HandleMsgListener listener) {
        this.listener = listener;
    }

    private HandleMsgListener listener;
    private String Tag = GlobalHandler.class.getSimpleName();
    public interface HandleMsgListener{
        void handleMsg(Message msg);
    }

}
