package com.mibo.xunguan2019.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.MessageDialog;
import com.mibo.xunguan2019.AppExecutors;
import com.mibo.xunguan2019.BasicApp;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.common.BaseActivity;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_login.db.dao.UserDao;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.module_login.domain.LoginContract;
import com.mibo.xunguan2019.module_login.domain.LoginPresenter;
import com.mibo.xunguan2019.utils.AtyContainer;
import com.mibo.xunguan2019.utils.GlobalHandler;
import com.mibo.xunguan2019.utils.IntentUtil;
import com.mibo.xunguan2019.utils.ProgressDialogUtil;

import java.util.List;
import java.util.concurrent.Executors;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_IOS;
import static com.kongzue.dialog.v2.DialogSettings.THEME_DARK;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;

public class LoginActivity extends BaseActivity implements LoginContract.View, GlobalHandler.HandleMsgListener {

    private LoginContract.Presenter mPresenter;
    private GlobalHandler mHandler;

    private EditText username;
    private EditText password;
    private ImageView loginBtn;
    private ImageView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mHandler = GlobalHandler.getInstance();
        mHandler.setListener(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        exitBtn = findViewById(R.id.loginOutBtn);

        DialogSettings.style = STYLE_IOS;
        DialogSettings.tip_theme = THEME_DARK;
        DialogSettings.dialog_theme = THEME_LIGHT;

        mPresenter = new LoginPresenter(this, this);

        initListener();
    }

    private void initListener(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_text = username.getText().toString();
                String password_text = password.getText().toString();
                mPresenter.syncData(username_text, password_text, mHandler);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtyContainer.getInstance().finishAllActivity();
            }
        });
    }

    @Override
    public void showInterError() {
        MessageDialog.show(LoginActivity.this, "错误", "网络未连接");
    }

    @Override
    public void showEnd(String endStr) {
        ProgressDialogUtil.dismiss();
        MessageDialog.show(LoginActivity.this, "提示", endStr);
    }

    @Override
    public void solveLogin(int code) {
        if(code == 1){
            IntentUtil.ToHomeActivity(this);
        }else{
            MessageDialog.show(LoginActivity.this, "登录提示", "当前用户不存在或密码错误！");
        }
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what){
            case 0:
                ProgressDialogUtil.show(this, msg.obj.toString());
                break;
            case 1:
                ProgressDialogUtil.dismiss();
                MessageDialog.show(LoginActivity.this, "通知", msg.obj.toString());
                break;
            case 2:
                mPresenter.syncNode(mHandler);                  //下载所有节点
                break;
            case 3:
                List<NodeEntity> nodeEntities = (List<NodeEntity>) msg.obj;
                mPresenter.syncContent(mHandler, nodeEntities);             //下载表单内容
                break;
            case 4:
                mPresenter.uploadContent(mHandler);             //下载表单内容
                break;
        }
    }
}
