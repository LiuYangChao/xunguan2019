package com.mibo.xunguan2019.net_rxjava.config;

/**
 * Author liuyangchao
 * Date on 2019/5/15.16:10
 */

public class BaseEntity<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
