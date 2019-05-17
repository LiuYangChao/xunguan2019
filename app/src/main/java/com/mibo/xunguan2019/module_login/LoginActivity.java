package com.mibo.xunguan2019.module_login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.common.BaseActivity;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.module_login.model.ValidateModel;
import com.mibo.xunguan2019.module_login.net.HttpService_Login;
import com.mibo.xunguan2019.net_rxjava.config.BaseEntity;
import com.mibo.xunguan2019.net_rxjava.http.RetrofitFactory;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity {

    public TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    public void getData(){
        HttpService_Login loginService = RetrofitFactory.getInstence().create(HttpService_Login.class);
        loginService.validateUser("","").compose(this.<BaseEntity<ValidateModel>>setThread())
                .subscribe(new Observer<BaseEntity<ValidateModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<ValidateModel> userEntityBaseEntity) {
                        System.out.println(userEntityBaseEntity);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
