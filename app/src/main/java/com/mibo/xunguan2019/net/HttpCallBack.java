package com.mibo.xunguan2019.net;

import io.reactivex.disposables.Disposable;

/**
 * Author liuyangchao
 * Date on 2019/5/10.16:59
 */

public abstract class HttpCallBack<T> {

    public void onSubscribe(Disposable d){

    }

    public abstract void onNext(HttpResult<T> httpResult);

    public void onError(Throwable e){

    }

    public void onFinish() {

    }

}
