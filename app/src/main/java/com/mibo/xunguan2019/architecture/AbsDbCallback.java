package com.mibo.xunguan2019.architecture;

import android.arch.persistence.db.SupportSQLiteDatabase;

/**
 * Author liuyangchao
 * Date on 2019/5/10.9:39
 */

public abstract class AbsDbCallback {
    public abstract void create(SupportSQLiteDatabase db);

    public abstract void open();

    public abstract void upgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion);
}