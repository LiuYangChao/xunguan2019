package com.mibo.xunguan2019.db_holder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.mibo.xunguan2019.AppExecutors;
import com.mibo.xunguan2019.architecture.DbCallbackHelper;
import com.mibo.xunguan2019.module_content.db.dao.CellDao;
import com.mibo.xunguan2019.module_content.db.dao.HeadDao;
import com.mibo.xunguan2019.module_content.db.dao.RowDao;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_content.db.entity.RowEntity;
import com.mibo.xunguan2019.module_home.db.dao.NodeDao;
import com.mibo.xunguan2019.module_home.db.dao.TaskDao;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;
import com.mibo.xunguan2019.module_login.db.dao.PostDao;
import com.mibo.xunguan2019.module_login.db.dao.UserDao;
import com.mibo.xunguan2019.module_login.db.entity.PostEntity;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;

/**
 * Author liuyangchao
 * Date on 2019/5/10.9:33
 */
@Database(entities = {UserEntity.class, PostEntity.class, TaskEntity.class, NodeEntity.class, HeadEntity.class, RowEntity.class, CellEntity.class}, version = 2, exportSchema = true)
@TypeConverters(DateConverter.class)
public abstract class AppDB extends RoomDatabase {

    private static AppDB sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "xunguan.db";

    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract TaskDao taskDao();
    public abstract NodeDao nodeDao();
    public abstract HeadDao headDao();
    public abstract RowDao rowDao();
    public abstract CellDao cellDao();

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
