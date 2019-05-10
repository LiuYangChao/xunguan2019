package com.mibo.xunguan2019.db_holder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.mibo.xunguan2019.AppExecutors;
import com.mibo.xunguan2019.architecture.DbCallbackHelper;

/**
 * Author liuyangchao
 * Date on 2019/5/10.9:33
 */

public abstract class AppDB extends RoomDatabase {

    private static AppDB sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "xunguan.db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDB getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDB.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDB buildDatabase(final Context appContext,
                                       final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                // Add a delay to simulate a long-running operation
                                addDelay();
                                // Generate the getDate for pre-population
                                AppDB database = AppDB.getInstance(appContext, executors);
                                // notify that the database was created and it's ready to be used

                                DbCallbackHelper.dispatchOnCreate(db);
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).addMigrations(DbCallbackHelper.getUpdateConfig()).build();
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }
    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
