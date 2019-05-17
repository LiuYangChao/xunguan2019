package com.mibo.xunguan2019.net;

import com.google.gson.annotations.SerializedName;

/**
 * Author liuyangchao
 * Date on 2019/5/10.17:01
 */

public class HttpResult<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return code==200;
    }

    @Override
    public String toString() {

        return "code: " + code + "\nmessage: " + message + "\nresult: " + result;
    }

}
