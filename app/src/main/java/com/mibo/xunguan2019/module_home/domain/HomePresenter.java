package com.mibo.xunguan2019.module_home.domain;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.mibo.xunguan2019.BasicApp;
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
import com.mibo.xunguan2019.module_home.model.PostSelectModel;
import com.mibo.xunguan2019.module_login.db.dao.PostDao;
import com.mibo.xunguan2019.module_login.db.dao.UserDao;
import com.mibo.xunguan2019.module_login.db.entity.PostEntity;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.utils.CloneUtil;
import com.mibo.xunguan2019.utils.SPUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author liuyangchao
 * Date on 2019/6/19.11:35
 */

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private final Context mContext;

    private UserDao userDao;
    private PostDao postDao;
    private TaskDao taskDao;
    private NodeDao nodeDao;
    private HeadDao headDao;
    private RowDao rowDao;
    private CellDao cellDao;

    public HomePresenter(Context context, @NonNull HomeContract.View view){
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
    public void init(String username) {
//        String username = SPUtils.get(mContext, "username", "");
        Observable.create(new ObservableOnSubscribe<List<NodeEntity>>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<List<NodeEntity>> e) throws Exception {
                List<UserEntity> currentUserList = userDao.getUserByUsername(username);
                if(currentUserList != null && currentUserList.size()>0){
                    List<TaskEntity> taskEntityList = taskDao.getTasksByUserServerId(String.valueOf(currentUserList.get(0).getEndId()));
                    List<NodeEntity> nodeEntityList = new ArrayList<>();
                    if(!taskEntityList.isEmpty()){
                        for(int i=0; i<taskEntityList.size(); i++){
                            String taskServerId = taskEntityList.get(i).getXid();
                            List<NodeEntity> nodeEntityList_task = nodeDao.getNodeByServerNodeId(taskServerId, "任务");
                            if(!nodeEntityList_task.isEmpty()){
                                NodeEntity nodeEntity_task = nodeEntityList_task.get(0);
                                List<NodeEntity> nodeEntityList_item = nodeDao.getNodeBytaskId(String.valueOf(nodeEntity_task.getId()));
                                nodeEntity_task.setCacheNodeList(nodeEntityList_item);
                                nodeEntityList.add(nodeEntity_task);
                                nodeEntityList.addAll(nodeEntityList_item);
                            }
                        }
                    }
                    e.onNext(nodeEntityList);
                }
            }
        })
        .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
        .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
        .subscribe(new Observer<List<NodeEntity>>() {        //观察者
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NodeEntity> value) {
                mView.notifyTreeLeft(value);
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
    public void initTemplateTree(NodeEntity nodeEntity) {
        Observable.create(new ObservableOnSubscribe<List<NodeEntity>>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<List<NodeEntity>> e) throws Exception {
                List<NodeEntity> nodeEntityList = nodeDao.getTemplate(String.valueOf(nodeEntity.getId()));
                e.onNext(nodeEntityList);
            }
        })
                .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<List<NodeEntity>>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NodeEntity> value) {
                        mView.notifyTreeMiddle(value);
                        Log.e("111", value.toString());
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
    public void initInstanceTree(NodeEntity nodeEntity) {
        Observable.create(new ObservableOnSubscribe<List<NodeEntity>>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<List<NodeEntity>> e) throws Exception {
                List<NodeEntity> nodeEntityList = nodeDao.getInstance(String.valueOf(nodeEntity.getId()));
                e.onNext(nodeEntityList);
            }
        })
                .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<List<NodeEntity>>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NodeEntity> value) {
                        mView.notifyTreeRight(value);
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
    public void initNewInstance(NodeEntity nodeEntity) {
        Observable.create(new ObservableOnSubscribe<List<NodeEntity>>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<List<NodeEntity>> e) throws Exception {
                long newNodeId = initInstanceNode(nodeEntity);
                initInstanceContent(nodeEntity, newNodeId);
                List<NodeEntity> nodeEntityList = nodeDao.getInstance(String.valueOf(nodeEntity.getParentId()));
                e.onNext(nodeEntityList);
            }
        })
                .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<List<NodeEntity>>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NodeEntity> value) {
                        mView.notifyTreeRight(value);
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
    public void initPostDialog(NodeEntity templateNodeEntity) {
        Observable.create(new ObservableOnSubscribe<List<PostSelectModel>>() { //定义被观察者
            @Override
            public void subscribe(final ObservableEmitter<List<PostSelectModel>> e) throws Exception {
                List<PostEntity> postEntities = postDao.getPostByServerId(templateNodeEntity.getPostId());
                List<PostSelectModel> postSelectModelList = new ArrayList<>();
                if(postEntities != null && postEntities.size()>0){
                    for(int i=0; i<postEntities.size(); i++){
                        PostSelectModel postSelectModel = new PostSelectModel();
                        String userIds = postEntities.get(i).getUserId();
                        List<String> userList = new ArrayList<>();
                        if(!userIds.isEmpty()){
                            String[] var = userIds.split(",");
                            userList = Arrays.asList(var);
                        }
                        List<UserEntity> userEntityList = userDao.getUserByUserIds(userList);
                        postSelectModel.setPostEntity(postEntities.get(i));
                        postSelectModel.setUserEntityList(userEntityList);
                        postSelectModelList.add(postSelectModel);
                    }
                }
                e.onNext(postSelectModelList);
            }
        })
                .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                .subscribe(new Observer<List<PostSelectModel>>() {        //观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostSelectModel> value) {
                        mView.notifyPostDialog(value);
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
    public void distributionPerson(NodeEntity templateNodeEntity, List<PostSelectModel> postSelectModelList) {
        if(postSelectModelList == null || postSelectModelList.size()==0){
            //没有分配的岗位
            mView.showMessage(1);
        }else{
            //存储岗位并且生成表格
            mView.showMessage(2);
        }
    }

    private long initInstanceNode(NodeEntity nodeEntity){
        nodeEntity.setId(0);
        nodeEntity.setType("表单实例");
        NodeEntity nodeEntity_bak = (NodeEntity) CloneUtil.cloneObject(nodeEntity);
        long newNodeId = nodeDao.insertNode(nodeEntity_bak);
        return newNodeId;
    }

    private void initInstanceContent(NodeEntity nodeEntity, long newNodeId){
        initHeadTemplate(nodeEntity, newNodeId);
        initRowTemplate(nodeEntity, newNodeId);
        initCellTemplate(nodeEntity, newNodeId);
    }

    private void initHeadTemplate(NodeEntity nodeEntity, long newNodeId){
        List<HeadEntity> headEntityLIst = headDao.getHeadsByTmpId(nodeEntity.getServerNodeId(), "表单模板");
        if(headEntityLIst != null && headEntityLIst.size()>0){
            for(int i=0; i<headEntityLIst.size(); i++){
                headEntityLIst.get(i).setId(0);
                headEntityLIst.get(i).setTableType("表单实例");
                headEntityLIst.get(i).setAndroidTmpId((int) newNodeId);
            }
            headDao.insertAllHead(headEntityLIst);
        }
    }
    private void initRowTemplate(NodeEntity nodeEntity, long newNodeId){
        List<RowEntity> rowEntityList = rowDao.getRowsByTmpId(nodeEntity.getServerNodeId()+"", "表单模板");
        if(rowEntityList != null && rowEntityList.size()>0){
            for(int i=0; i<rowEntityList.size(); i++){
                rowEntityList.get(i).setId(0);
                rowEntityList.get(i).setTableType("表单实例");
                rowEntityList.get(i).setAndroidTmpId((int) newNodeId);
            }
            rowDao.insertAllRow(rowEntityList);
        }
    }
    private void initCellTemplate(NodeEntity nodeEntity, long newNodeId){
        List<CellEntity> cellEntityList = cellDao.getCellsByTmpId(nodeEntity.getServerNodeId()+"", "表单模板");
        if(cellEntityList != null && cellEntityList.size()>0){
            for(int i=0; i<cellEntityList.size(); i++){
                cellEntityList.get(i).setId(0);
                cellEntityList.get(i).setTableType("表单实例");
                cellEntityList.get(i).setAndroidTmpId((int) newNodeId);
            }
            cellDao.insertAllCell(cellEntityList);
        }
    }

}
