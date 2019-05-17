package com.mibo.xunguan2019.module_login.model;

/**
 * 1，用户身份校验 返回模型
 * Author liuyangchao
 * Date on 2019/5/16.16:17
 */

public class ValidateModel {

    private boolean result;             //true:校验成功，false:校验失败
    private String msg = "人员身份信息校验通过";                   //校验失败提示用户：比如，用户名不存在，密码错误等

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
