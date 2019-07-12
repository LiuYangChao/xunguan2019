package com.mibo.xunguan2019.module_content.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mibo.xunguan2019.BasicApp;
import com.mibo.xunguan2019.module_content.db.dao.CellDao;
import com.mibo.xunguan2019.module_content.db.dao.HeadDao;
import com.mibo.xunguan2019.module_content.db.dao.RowDao;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_content.db.entity.RowEntity;
import com.mibo.xunguan2019.module_content.holder.TableViewModel;
import com.mibo.xunguan2019.module_content.model.Cell;
import com.mibo.xunguan2019.module_content.model.ColumnHeader;
import com.mibo.xunguan2019.module_content.model.RowHeader;
import com.mibo.xunguan2019.module_home.db.dao.NodeDao;
import com.mibo.xunguan2019.module_home.db.dao.TaskDao;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;
import com.mibo.xunguan2019.module_home.domain.HomeContract;
import com.mibo.xunguan2019.module_login.db.dao.PostDao;
import com.mibo.xunguan2019.module_login.db.dao.UserDao;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.HEAD;

/**
 * Author liuyangchao
 * Date on 2019/6/24.9:44
 */

public class ContentPresenter implements ContentContract.Presenter {

    private final ContentContract.View mView;
    private final Context mContext;

    private UserDao userDao;
    private PostDao postDao;
    private TaskDao taskDao;
    private NodeDao nodeDao;
    private HeadDao headDao;
    private RowDao rowDao;
    private CellDao cellDao;

    public ContentPresenter(Context context, @NonNull ContentContract.View view){
        mContext = context;
        mView = view;
        initDataBase();
    }

    private void initDataBase(){
        userDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().userDao();
        postDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().postDao();
        taskDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().taskDao();
        nodeDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().nodeDao();
        headDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().headDao();
        rowDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().rowDao();
        cellDao = ((BasicApp)mContext.getApplicationContext()).getDatabase().cellDao();
    }

    @Override
    public void initTableData(NodeEntity nodeEntity, TableViewModel mTableViewModel) {
        Observable.create(new ObservableOnSubscribe<TableViewModel>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<TableViewModel> e) throws Exception {
                List<List<Cell>> cellList = new ArrayList<>();
                List<ColumnHeader> columnHeaderList = new ArrayList<>();
                List<RowHeader> rowHeaderList = new ArrayList<>();
                List<HeadEntity> headEntityList = headDao.getHeadsByAndroidId(nodeEntity.getId()+"");
                List<RowEntity> rowEntityList = rowDao.getRowsByAndroidId(nodeEntity.getId()+"");
                List<CellEntity> cellEntityList = cellDao.getCellsByAndroidId(nodeEntity.getId()+"");
                List<String> columnIdList = new ArrayList<>();
                List<String> rowIdList = new ArrayList<>();
                if(headEntityList != null && headEntityList.size()>0){
                    for(int i=0; i<headEntityList.size(); i++){
                        ColumnHeader columnHeader = new ColumnHeader(headEntityList.get(i).getId()+"", headEntityList.get(i).getName());
                        columnHeaderList.add(columnHeader);
                        columnIdList.add(headEntityList.get(i).getId()+"");
                    }
                }
                if(rowEntityList != null && rowEntityList.size()>0){
                    for(int i=0; i<rowEntityList.size(); i++){
                        RowHeader rowHeader = new RowHeader(rowEntityList.get(i).getId()+"", i+1+"");
                        rowHeaderList.add(rowHeader);
                        rowIdList.add(rowEntityList.get(i).getId()+"");
                    }
                }
                if(cellEntityList != null && cellEntityList.size()>0){
                    int count = 0;
                    for(int i=0; i<rowIdList.size(); i++){
                        List<Cell> cellList1 = new ArrayList<>();
                        for(int j=0; j<columnIdList.size(); j++){
                            String cellId = rowIdList.get(i)+"-"+columnIdList.get(j);
                            Cell cell = new Cell(cellId, cellEntityList.get(count));
                            cellList1.add(cell);
                            count++;
                        }
                        cellList.add(cellList1);
                    }
                }
                mTableViewModel.setCellList(cellList);
                mTableViewModel.setColumnHeaderList(columnHeaderList);
                mTableViewModel.setRowHeaderList(rowHeaderList);
                e.onNext(mTableViewModel);
            }
        })
                .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<TableViewModel>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TableViewModel value) {
                        mView.showTable(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void saveTableData(TableViewModel mTableViewModel) {
        List<List<Cell>> cellListList = mTableViewModel.getCellList();
        List<CellEntity> cellEntityList = new ArrayList<>();
        if(cellListList != null && cellListList.size()>0){
            for(int i=0; i<cellListList.size(); i++){
                List<Cell> cellList = cellListList.get(i);
                if(cellList != null && cellList.size()>0){
                    for(int j=0; j<cellList.size(); j++){
                        CellEntity cellEntity = (CellEntity) cellList.get(j).getContent();
                        cellEntityList.add(cellEntity);
                    }
                }
            }
        }
        Observable.create(new ObservableOnSubscribe<Integer>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<Integer> e) throws Exception {
                int count = cellDao.updateCells(cellEntityList);
                e.onNext(count);
            }
        }).subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
            .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
            .subscribe(new Observer<Integer>() {        //观察者
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    mView.saveSuccess(value);
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }
            });
    }

    @Override
    public void setUpload(NodeEntity nodeEntity, boolean isChecked) {
        Observable.create(new ObservableOnSubscribe<String>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                nodeEntity.setDownloadStatus(isChecked?"2":"0");
                int aaa = nodeDao.updateNodes(nodeEntity);
            }
        }).subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<String>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initSwitchButton(NodeEntity nodeEntity) {
        Observable.create(new ObservableOnSubscribe<String>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                e.onNext(nodeEntity.getDownloadStatus());
            }
        }).subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<String>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        mView.showSwitchButton(value=="2"?true:false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initData(NodeEntity nodeEntity) {
        Observable.create(new ObservableOnSubscribe<NodeEntity>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<NodeEntity> e) throws Exception {
                e.onNext(nodeDao.getNodeById(nodeEntity.getId()));
            }
        }).subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<NodeEntity>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NodeEntity value) {
                        mView.initData(value);
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
