package com.mibo.xunguan2019.module_login.net;

import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.module_login.model.ValidateModel;
import com.mibo.xunguan2019.net_rxjava.config.BaseEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 登录模块网络请求
 * Author liuyangchao
 * Date on 2019/5/11.10:04
 */

public interface HttpService_Login {

    @GET("api/validateUser.rdm")
    Observable<BaseEntity<ValidateModel>> validateUser(String username, String password);

    @GET("api/getUserList.rdm")
    Observable<BaseEntity<UserEntity>> getUserList();

}
