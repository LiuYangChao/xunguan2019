package com.mibo.xunguan2019.module_login.domain;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.mibo.xunguan2019.BasicApp;
import com.mibo.xunguan2019.module_content.db.dao.CellDao;
import com.mibo.xunguan2019.module_content.db.dao.HeadDao;
import com.mibo.xunguan2019.module_content.db.dao.RowDao;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.db.entity.HeadEntity;
import com.mibo.xunguan2019.module_content.db.entity.RowEntity;
import com.mibo.xunguan2019.module_content.model.ContentModel;
import com.mibo.xunguan2019.module_home.db.dao.NodeDao;
import com.mibo.xunguan2019.module_home.db.dao.TaskDao;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;
import com.mibo.xunguan2019.module_home.model.NodeModel;
import com.mibo.xunguan2019.module_home.net.HttpService_Home;
import com.mibo.xunguan2019.module_login.LoginActivity;
import com.mibo.xunguan2019.module_login.db.dao.PostDao;
import com.mibo.xunguan2019.module_login.db.dao.UserDao;
import com.mibo.xunguan2019.module_login.db.entity.PostEntity;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.module_login.model.ValidateModel;
import com.mibo.xunguan2019.module_login.net.HttpService_Login;
import com.mibo.xunguan2019.net_rxjava.config.BaseEntity;
import com.mibo.xunguan2019.net_rxjava.http.RetrofitFactory;
import com.mibo.xunguan2019.utils.GlobalHandler;
import com.mibo.xunguan2019.utils.NetworkUtils;
import com.mibo.xunguan2019.utils.PhoneCodeUtils;
import com.mibo.xunguan2019.utils.ProgressDialogUtil;
import com.mibo.xunguan2019.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Author liuyangchao
 * Date on 2019/5/17.14:49
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private final Context mContext;
    private final HttpService_Login loginService = RetrofitFactory.getInstence().create(HttpService_Login.class);
    private final HttpService_Home homeService = RetrofitFactory.getInstence().create(HttpService_Home.class);
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private String username;
    private UserDao userDao;
    private PostDao postDao;
    private TaskDao taskDao;
    private NodeDao nodeDao;
    private HeadDao headDao;
    private RowDao rowDao;
    private CellDao cellDao;

    private List<String> final_needDownTaskIds = new ArrayList<>();
    private List<Integer> final_needDownTaskAndroidIds = new ArrayList<>();
    private List<NodeEntity> final_needDownContents = new ArrayList<>();

    public LoginPresenter(Context context, @NonNull LoginContract.View view) {
        mContext = context;
        mView = view;
        initDataBase();
    }

    private void initDataBase() {
        userDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().userDao();
        postDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().postDao();
        taskDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().taskDao();
        nodeDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().nodeDao();
        headDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().headDao();
        rowDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().rowDao();
        cellDao = ((BasicApp) mContext.getApplicationContext()).getDatabase().cellDao();
    }

    @Override
    public void syncData(String username, String password, GlobalHandler handler) {
        boolean isConnected = NetworkUtils.isConnected(mContext);
        if (!isConnected) {
            Observable.create(new ObservableOnSubscribe<Integer>() { //定义被观察者
                @Override
                public void subscribe(final ObservableEmitter<Integer> e) throws Exception {
                    List<UserEntity> userEntities = userDao.getUserByUsername(username);
                    if(userEntities != null && userEntities.size()>0){
                        e.onNext(1);        //登录成功
                    }else{
                        e.onNext(2);        //登录失败
                    }
                }
            })
                    .subscribeOn(Schedulers.io())  //被观察者 在线程池中调用了
                    .observeOn(AndroidSchedulers.mainThread()) //观察者在主线程中实现
                    .subscribe(new Observer<Integer>() {        //观察者
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Integer value){
                            mView.solveLogin(value);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            this.username = username;
            ProgressDialogUtil.show(mContext, "开始连通网络");
            SPUtils.put(mContext, "username", this.username);
            startLogin(username, password, handler);
        }
    }

    @Override
    public void syncNode(GlobalHandler handler) {
        final_needDownContents.clear();
        Observable.fromIterable(final_needDownTaskIds)
                .concatMap(new Function<String, ObservableSource<BaseEntity<List<NodeModel>>>>() {
            @Override
            public ObservableSource<BaseEntity<List<NodeModel>>> apply(String s) throws Exception {
                notifyLogin(handler, "下载任务:" + s);
                return loginService.getNodeByTaskId(s);
            }
        }).compose(LoginActivity.setThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<BaseEntity<List<NodeModel>>>() {
                    @Override
                    public void accept(BaseEntity<List<NodeModel>> listBaseEntity) throws Exception {
                        String forTaskId = "";
                        //总：任务+工作项+表单
                        for (int i = 0; i < listBaseEntity.getData().size(); i++) {
                            //1.任务数据入库
                            NodeModel nodeModel_task = listBaseEntity.getData().get(i);
                            String taskId = nodeModel_task.getTaskId();
                            forTaskId = taskId;
                            String taskName = nodeModel_task.getTaskName();
                            List<NodeEntity> nodeEntityList_task = nodeDao.getNodeByServerNodeId(taskId, "任务");
                            long nodeEntity_task_id = -1;
                            if (nodeEntityList_task != null && nodeEntityList_task.size()>0) {
                                nodeEntity_task_id = nodeEntityList_task.get(0).getId();
                            } else {
                                NodeEntity nodeEntity_task = new NodeEntity();
                                nodeEntity_task.setLayer(1);
                                nodeEntity_task.setName(taskName);
                                nodeEntity_task.setParentId(-1);
                                nodeEntity_task.setServerNodeId(taskId);
                                nodeEntity_task.setType("任务");
                                nodeEntity_task.setTaskId(taskId);
                                nodeEntity_task_id = nodeDao.insertNode(nodeEntity_task);
                            }
                            //2.工作项数据入库
                            String workItemId = nodeModel_task.getWorkItemId();
                            String workItemName = nodeModel_task.getWorkItemName();
                            List<NodeEntity> nodeEntityList_item = nodeDao.getNodeByServerNodeId(workItemId, "工作项");
                            long nodeEntity_item_id = -1;
                            if (nodeEntityList_item != null && nodeEntityList_item.size()>0) {
                                nodeEntity_item_id = nodeEntityList_item.get(0).getId();
                            } else {
                                NodeEntity nodeEntity_item = new NodeEntity();
                                nodeEntity_item.setLayer(2);
                                nodeEntity_item.setName(workItemName);
                                nodeEntity_item.setParentId((int) nodeEntity_task_id);
                                nodeEntity_item.setServerNodeId(workItemId);
                                nodeEntity_item.setType("工作项");
                                nodeEntity_item.setTaskId(taskId);
                                nodeEntity_item.setPostId(nodeModel_task.getPostId());
                                nodeEntity_item.setPostName(nodeModel_task.getPostName());
                                nodeEntity_item_id = nodeDao.insertNode(nodeEntity_item);
                            }
                            //3.表单数据入库
                            String instanceId = nodeModel_task.getInstanceId();
                            String instanceName = nodeModel_task.getInstanceName();
                            List<NodeEntity> nodeEntityList_instance = nodeDao.getNodeByServerNodeId(instanceId, "表单");
                            if (nodeEntityList_instance != null && nodeEntityList_instance.size()>0) {

                            } else {
                                NodeEntity nodeEntity_instance = new NodeEntity();
                                nodeEntity_instance.setLayer(3);
                                nodeEntity_instance.setName(instanceName);
                                nodeEntity_instance.setParentId((int) nodeEntity_item_id);
                                nodeEntity_instance.setServerNodeId(instanceId);
                                nodeEntity_instance.setType("表单");
                                nodeEntity_instance.setTaskId(taskId);
                                nodeEntity_instance.setDownloadStatus("0");
                                nodeEntity_instance.setInputStatus("1");
                                nodeEntity_instance.setPostId(nodeModel_task.getPostId());
                                nodeEntity_instance.setPostName(nodeModel_task.getPostName());
                                nodeDao.insertNode(nodeEntity_instance);
                            }
                        }
                        List<NodeEntity> nodeEntityList = nodeDao.getDownInstance(forTaskId + "");
                        final_needDownContents.addAll(nodeEntityList);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<NodeModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseEntity<List<NodeModel>> listBaseEntity) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i("下载表单数量", final_needDownContents.size()+"");
                notifySyncContent(handler);
            }
        });

    }

    @Override
    public void syncContent(GlobalHandler handler, List<NodeEntity> nodeEntities) {
        Observable.fromIterable(nodeEntities)
                .flatMap(new Function<NodeEntity, ObservableSource<BaseEntity<ContentModel>>>() {
            @Override
            public ObservableSource<BaseEntity<ContentModel>> apply(NodeEntity nodeEntity) throws Exception {
                notifyLogin(handler, "开始下载表单内容"+nodeEntity.getName());
                Log.i("下载表单:", nodeEntity.getName());
                return loginService.getContentByTId(nodeEntity.getServerNodeId());
            }
        }).compose(LoginActivity.setThread())
                .observeOn(Schedulers.io()).doOnNext(new Consumer<BaseEntity<ContentModel>>() {
                    @Override
                    public void accept(BaseEntity<ContentModel> contentModelBaseEntity) throws Exception {
                        notifyLogin(handler, "开始下载表单内容");
                        if (contentModelBaseEntity.getData() != null) {
                            String nodeServerId = contentModelBaseEntity.getData().getInstanceId();
                            List<NodeEntity> local_nodeEntityList = nodeDao.getNodeByServerNodeId(nodeServerId, "表单");
                            if(local_nodeEntityList != null && local_nodeEntityList.size()>0){
                                local_nodeEntityList.get(0).setDownloadStatus("1");
                                nodeDao.updateNodes(local_nodeEntityList.get(0));
                            }
                            List<HeadEntity> headEntityList = contentModelBaseEntity.getData().getHeadEntityList();
                            List<RowEntity> rowEntityList = contentModelBaseEntity.getData().getRowEntityList();
                            if (headEntityList != null && !headEntityList.isEmpty())
                                for (int i = 0; i < headEntityList.size(); i++) {
                                    headEntityList.get(i).setTableType("表单模板");
                                }
                            headDao.insertAllHead(headEntityList);
                            if (rowEntityList != null && !rowEntityList.isEmpty()) {
                                for (int i = 0; i < rowEntityList.size(); i++) {
                                    rowEntityList.get(i).setTableType("表单模板");
                                    rowDao.insertRow(rowEntityList.get(i));
                                    if (rowEntityList.get(i).getCellEntityList() != null && rowEntityList.get(i).getCellEntityList().size() > 0) {
                                        for (int j = 0; j < rowEntityList.get(i).getCellEntityList().size(); j++) {
                                            rowEntityList.get(i).getCellEntityList().get(j).setType(headEntityList.get(j).getType());
                                            rowEntityList.get(i).getCellEntityList().get(j).setTableType("表单模板");
                                            rowEntityList.get(i).getCellEntityList().get(j).setRowNum(j + 1);
                                            rowEntityList.get(i).getCellEntityList().get(j).setColNum(i + 1);
                                        }
                                    }
                                    cellDao.insertAllCell(rowEntityList.get(i).getCellEntityList());
                                }
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<ContentModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<ContentModel> contentModelBaseEntity) {
                        Log.i("已下载", "yes!");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        notifyuploadContent(handler);
                    }
                });
    }

    @Override
    public void uploadContent(GlobalHandler mHandler) {
        uploadContentList();
    }

    private void notifyLogin(GlobalHandler handler, String msg) {
        Message message = new Message();
        message.what = 0;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void notifyEnd(GlobalHandler handler, String msg) {
        Message message = new Message();
        message.what = 1;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void notifySyncNode(GlobalHandler handler) {
        Message message = new Message();
        message.what = 2;
        handler.sendMessage(message);
    }

    private void notifySyncContent(GlobalHandler handler) {
        Message message = new Message();
        message.what = 3;
        message.obj = final_needDownContents;
        handler.sendMessage(message);
    }

    private void notifyuploadContent(GlobalHandler handler) {
        Message message = new Message();
        message.what = 4;
        handler.sendMessage(message);
    }

    //下载行文件
    private void test6_1(){

    }

    private void uploadContentList() {
        //首先构造好上传的实体Bean，然后再发送
//        Observable<ContentModel> uploadTables =
        Observable.create(new ObservableOnSubscribe<ContentModel>() {
            @Override
            public void subscribe(ObservableEmitter<ContentModel> e) throws Exception {
                List<NodeEntity> nodeEntityList = nodeDao.getUploadInstance();
                if (nodeEntityList != null && nodeEntityList.size() > 0) {
                    for (int i = 0; i < nodeEntityList.size(); i++) {
                        ContentModel contentModel = new ContentModel();
                        contentModel.setAndroidTableId(nodeEntityList.get(i).getId() + "");
                        contentModel.setPadId(PhoneCodeUtils.getDeviceId(mContext));
                        contentModel.setInstanceId(nodeEntityList.get(i).getServerNodeId());
                        contentModel.setInstanceName(nodeEntityList.get(i).getName());
                        List<HeadEntity> headEntityList = headDao.getTableHeads(contentModel.getAndroidTableId());
//                        List<RowEntity> rowEntityList = rowDao.getRowsByTmpId(contentModel.getAndroidTableId(), "表单实例");
                        List<RowEntity> rowEntityList = rowDao.getRowsByAndroidId(contentModel.getAndroidTableId());
                        for (int j = 0; j < rowEntityList.size(); j++) {
                            List<CellEntity> cellEntityList = cellDao. getTableCells(contentModel.getAndroidTableId(), "表单实例", j + 1 + "");
                            rowEntityList.get(j).setCellEntityList(cellEntityList);
                        }
                        contentModel.setHeadEntityList(headEntityList);
                        contentModel.setRowEntityList(rowEntityList);
                        e.onNext(contentModel);
                    }
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<ContentModel, ObservableSource<BaseEntity<String>>>() {
                    @Override
                    public ObservableSource<BaseEntity<String>> apply(ContentModel contentModel) throws Exception {
                        return loginService.uploadContent(contentModel);
                    }
                })
                .doOnNext(new Consumer<BaseEntity<String>>() {
                    @Override
                    public void accept(BaseEntity<String> stringBaseEntity) throws Exception {
                        System.out.println(stringBaseEntity.getCode());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<String> stringBaseEntity) {
                        System.out.println("上传成功！");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 开始进入同步逻辑
     * @param username
     * @param password
     * @param handler
     */
    private void startLogin(String username, String password, GlobalHandler handler) {
        loginService.validateUser(username, password)
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseEntity<ValidateModel>, ObservableSource<BaseEntity<List<UserEntity>>>>() {
                    @Override
                    public ObservableSource<BaseEntity<List<UserEntity>>> apply(BaseEntity<ValidateModel> validateModelBaseEntity) throws Exception {
                        notifyLogin(handler, "验证用户身份");
                        if (validateModelBaseEntity.getData().isResult()) {
                            return loginService.getUserList();
                        } else {
                            throw new Exception("无法登陆");
                        }
                    }
                })
                .flatMap(new Function<BaseEntity<List<UserEntity>>, ObservableSource<BaseEntity<List<PostEntity>>>>() {
                    @Override
                    public ObservableSource<BaseEntity<List<PostEntity>>> apply(BaseEntity<List<UserEntity>> listBaseEntity) throws Exception {
                        try {
                            List<UserEntity> userEntities = userDao.getAllUser();
                            userDao.deleteUsers(userEntities);
                        } catch (Exception e) {
                            Log.e("room", e.toString());
                            notifyLogin(handler, e.toString());
                        }
                        long[] userIds = userDao.insertAllUser(listBaseEntity.getData());
                        notifyLogin(handler, "人员信息下载完成");
                        return loginService.getPostList();
                    }
                })
                .flatMap(new Function<BaseEntity<List<PostEntity>>, ObservableSource<BaseEntity<List<TaskEntity>>>>() {
                    @Override
                    public ObservableSource<BaseEntity<List<TaskEntity>>> apply(BaseEntity<List<PostEntity>> listBaseEntity) throws Exception {
                        //缺少岗位的数据库操作
                        List<PostEntity> postEntities = postDao.getAllPost();
                        postDao.deletePost(postEntities);
                        long[] postIds = postDao.insertAllPost(listBaseEntity.getData());
                        notifyLogin(handler, "岗位同步完成");
                        return loginService.getTaskListByUserName(username);
                    }
                })
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行请求
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .doOnNext(new Consumer<BaseEntity<List<TaskEntity>>>() {
                    @Override
                    public void accept(BaseEntity<List<TaskEntity>> listBaseEntity) throws Exception {
                        notifyLogin(handler, "下载任务列表！");
                        UserEntity userEntity = userDao.getUserByUsername(username).get(0);
                        List<TaskEntity> localTaskEntities = taskDao.getTasksByUserServerId(userEntity.getEndId()+"");
                        List<String> localTaskIds = Stream.of(localTaskEntities).map(TaskEntity::getXid).collect(Collectors.toList());
                        for (int i = 0; i < listBaseEntity.getData().size(); i++) {
                            String remoteId = listBaseEntity.getData().get(i).getXid();
                            if (!localTaskIds.contains(remoteId)) {               //远程出现新的任务则下载。
                                long taskAndroidId = taskDao.insertTask(listBaseEntity.getData().get(i));
                                final_needDownTaskIds.add(listBaseEntity.getData().get(i).getXid());
                                final_needDownTaskAndroidIds.add((int) taskAndroidId);
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<TaskEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<TaskEntity>> listBaseEntity) {
                        notifySyncNode(handler);
                    }

                    @Override
                    public void onError(Throwable e) {
                        notifyEnd(handler, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
