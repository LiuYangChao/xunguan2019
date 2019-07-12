package com.mibo.xunguan2019.module_home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.MessageDialog;
import com.kongzue.dialog.v2.SelectDialog;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.common.BaseActivity;
import com.mibo.xunguan2019.custom.PostDialog;
import com.mibo.xunguan2019.module_home.adapter.PostSelectAdapter;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.domain.HomeContract;
import com.mibo.xunguan2019.module_home.domain.HomePresenter;
import com.mibo.xunguan2019.module_home.model.PostSelectModel;
import com.mibo.xunguan2019.module_login.LoginActivity;
import com.mibo.xunguan2019.utils.GlobalHandler;
import com.mibo.xunguan2019.utils.IntentUtil;
import com.mibo.xunguan2019.utils.ProgressDialogUtil;
import com.mibo.xunguan2019.utils.SPUtils;
import com.mibo.xunguan2019.utils.adapter.TableInstanceAdapter;
import com.mibo.xunguan2019.utils.adapter.TableTemplateAdapter;
import com.mibo.xunguan2019.utils.adapter.TaskAdapter;

import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.View, TaskAdapter.OnItemClickListener,
    TableTemplateAdapter.OnItemClickListener, TableInstanceAdapter.OnItemClickListener{

    RecyclerView home_tree;
    RecyclerView home_tree_template;
    RecyclerView home_tree_instance;

    private TaskAdapter taskAdapter;
    private TableTemplateAdapter tableTemplateAdapter;
    private TableInstanceAdapter tableInstanceAdapter;
    private PostSelectAdapter postSelectAdapter;

    private HomeContract.Presenter mPresenter;
    private NodeEntity currentSelectNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_tree = findViewById(R.id.home_tree);
        home_tree_template = findViewById(R.id.home_tree_template);
        home_tree_instance = findViewById(R.id.home_tree_instance);

        mPresenter = new HomePresenter(this, this);
        taskAdapter = new TaskAdapter(this, R.layout.item_tree_task, R.layout.item_tree_item, R.layout.item_tree_table);
        tableTemplateAdapter = new TableTemplateAdapter(this, R.layout.item_middle);
        tableInstanceAdapter = new TableInstanceAdapter(this, R.layout.item_right);

        LinearLayoutManager manager_home = new LinearLayoutManager(this);
        manager_home.setOrientation(LinearLayout.VERTICAL);
        home_tree.setLayoutManager(manager_home);
        home_tree.setAdapter(taskAdapter);
        taskAdapter.setmItemClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        home_tree_template.setLayoutManager(layoutManager);
        home_tree_template.setAdapter(tableTemplateAdapter);
        tableTemplateAdapter.setmItemClickListener(this);

        LinearLayoutManager manager_instance = new LinearLayoutManager(this);
        manager_instance.setOrientation(LinearLayout.VERTICAL);
        home_tree_instance.setLayoutManager(manager_instance);
        home_tree_instance.setAdapter(tableInstanceAdapter);
        tableInstanceAdapter.setmItemClickListener(this);

        mPresenter.init((String) SPUtils.get(this, "username", "guest1"));
//        mPresenter.init("guest1");
    }

    @Override
    public void showMessage(int code) {
        if(code == 1){
            MessageDialog.show(HomeActivity.this, "提示", "表单未绑定岗位人员");
        }else if(code == 2){
            mPresenter.initNewInstance(currentSelectNode);
        }
    }

    @Override
    public void notifyTreeLeft(List<NodeEntity> nodeEntityList) {
        taskAdapter.updateWithClear(nodeEntityList);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyTreeMiddle(List<NodeEntity> nodeEntityList) {
        tableTemplateAdapter.updateWithClear(nodeEntityList);
        tableTemplateAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyTreeRight(List<NodeEntity> nodeEntityList) {
        tableInstanceAdapter.updateWithClear(nodeEntityList);
        tableInstanceAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyPostDialog(List<PostSelectModel> postSelectModelList) {
        postSelectAdapter.updateWithClear(postSelectModelList);
        postSelectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position) {
        NodeEntity taskNodeEntity = taskAdapter.getItems(position);
        if(taskNodeEntity.getLayer() == 2){     //点击的是工作项节点，就要刷新表单模板和表单实例
            mPresenter.initTemplateTree(taskNodeEntity);
            mPresenter.initInstanceTree(taskNodeEntity);
        }
    }

    @Override
    public void onItemClickTemplate(RecyclerView parent, View view, int position) {
        Log.e("template click", position+"");
        NodeEntity templateNodeEntity = tableTemplateAdapter.getItems(position);

//        SelectDialog.show(this, "提示", "是否生成一张新表单", "确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mPresenter.initNewInstance(templateNodeEntity);
//            }
//        }, "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        currentSelectNode = templateNodeEntity;
        final View post_view = View.inflate(this, R.layout.post_dialog_view, null);
        final RecyclerView post_view_recycler = post_view.findViewById(R.id.post_dialog_recycler);
        postSelectAdapter = new PostSelectAdapter(this, R.layout.post_dialog_view_item);
        LinearLayoutManager postManager = new LinearLayoutManager(this);
        postManager.setOrientation(LinearLayout.VERTICAL);
        post_view_recycler.setLayoutManager(postManager);
        post_view_recycler.setAdapter(postSelectAdapter);
        mPresenter.initPostDialog(templateNodeEntity);

        new PostDialog(this).setTitle("分配人员").setView(post_view).setConfirmButton("确定", new PostDialog.OnConfirmClickListener() {
            @Override
            public void doConfirm() {
                mPresenter.distributionPerson(currentSelectNode, postSelectAdapter.getList());
                //点击确定按钮的实现
            }
        }).setCancelButton("取消", new PostDialog.OnCancelClickListener() {
            @Override
            public void doCancel() {

            }
        }).show();

    }

    @Override
    public void onItemClickInstance(RecyclerView parent, View view, int position) {
        NodeEntity templateNodeEntity = tableInstanceAdapter.getItems(position);
        IntentUtil.ToContentActivity(this, templateNodeEntity);
    }

}
