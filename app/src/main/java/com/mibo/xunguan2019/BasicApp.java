package com.mibo.xunguan2019;

import android.app.Application;

import com.mibo.xunguan2019.db_holder.AppDB;

/**
 * Author liuyangchao
 * Date on 2019/5/10.9:30
 */

public class BasicApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDB getDatabase() {
        return AppDB.getInstance(this, mAppExecutors);
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
