package com.mibo.xunguan2019.architecture;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Author liuyangchao
 * Date on 2019/5/10.9:38
 */

public class DbCallbackHelper {
    private static ArrayList<AbsDbCallback> mDbCallbacks = new ArrayList<>();

    public static void init() {
//        mDbCallbacks.add(new EssayDbCallback());
    }

    public static void dispatchOnCreate(SupportSQLiteDatabase db) {
        for (AbsDbCallback callback : mDbCallbacks) {
            callback.create(db);
        }
    }

    private static void dispatchUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
        for (AbsDbCallback callback : mDbCallbacks) {
            callback.upgrade(db, oldVersion, newVersion);
        }
    }

    public static Migration[] getUpdateConfig() {
        return new Migration[]{
                new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        dispatchUpgrade(database, 1, 2);
                    }
                },
                new Migration(2, 3) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        dispatchUpgrade(database, 2, 3);
                    }
                }
        };
    }
}
