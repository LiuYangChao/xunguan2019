package com.mibo.xunguan2019.net;

import android.util.Log;

import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.module_login.net.HttpService_Login;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author liuyangchao
 * Date on 2019/5/11.10:17
 */

public class HttpRequest {

    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;
    private HttpService_Login mHttpService;

    private static HttpRequest instance;

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                instance = new HttpRequest();
            }
        }
        return instance;
    }

    //默认方法
    private HttpRequest() {
        //手动创建一个OkHttpClient并设置超时时间
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mHttpService = mRetrofit.create(HttpService_Login.class);
    }

    public void validateUser(String username, String password, HttpCallBack<UserEntity> httpCallback) {
//        Observable<HttpResult<UserEntity>> observable = mHttpService.validateUser(username, password);

//        Call<UserEntity> gitHubBeanCall = (Call<UserEntity>) mHttpService.validateUser(username, password);
//        //执行请求
//        gitHubBeanCall.enqueue(new Callback<UserEntity>() {
//            @Override
//            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<UserEntity> call, Throwable t) {
//
//            }
//        });


    }

}
