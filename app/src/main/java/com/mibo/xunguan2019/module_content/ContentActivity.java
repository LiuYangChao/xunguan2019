package com.mibo.xunguan2019.module_content;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.evrencoskun.tableview.TableView;
import com.kongzue.dialog.v2.MessageDialog;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.common.BaseActivity;
import com.mibo.xunguan2019.common.Constants;
import com.mibo.xunguan2019.module_content.domain.ContentContract;
import com.mibo.xunguan2019.module_content.domain.ContentPresenter;
import com.mibo.xunguan2019.module_content.holder.TableViewModel;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.utils.adapter.TableViewAdapter;
import com.suke.widget.SwitchButton;

public class ContentActivity extends BaseActivity implements ContentContract.View {

    private TableView mTableView;
    private SwitchButton switch_button;
    private TableViewModel mTableViewModel;
    private TableViewAdapter mTableViewAdapter;

    private NodeEntity nodeEntity;
    private ContentContract.Presenter mPresenter;

    public ContentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        nodeEntity = (NodeEntity) getIntent().getSerializableExtra(Constants.NODE_ENTITY);

        mTableView = findViewById(R.id.mTableView);
        switch_button = findViewById(R.id.switch_button);

        mPresenter = new ContentPresenter(this, this);

        initializeTableView();
        initListener();
        initData();
//        initUIStatus();

//        mTableView.getCellRecyclerView().getPosition();
    }

    private void initData(){
        mPresenter.initData(nodeEntity);
    }

    private void initUIStatus(){
        if(nodeEntity.getDownloadStatus().equals("2")){
            switch_button.setChecked(true);
        }else{
            switch_button.setChecked(false);
        }
    }

    private void initListener(){
        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                mPresenter.setUpload(nodeEntity, isChecked);
            }
        });
    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        mTableViewModel = new TableViewModel(this);

        // Create TableView Adapter
        mTableViewAdapter = new TableViewAdapter(this, mTableViewModel);

        mTableView.setAdapter(mTableViewAdapter);
//        mTableView.setTableViewListener(new TableViewListener(mTableView));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        mPresenter.initTableData(nodeEntity, mTableViewModel);

    }

    @Override
    public void showTable(TableViewModel mTableViewModel) {
        mTableViewAdapter.setAllItems(mTableViewModel.getColumnHeaderList(), mTableViewModel
                .getRowHeaderList(), mTableViewModel.getCellList());
    }

    @Override
    public void saveSuccess(int count) {
        MessageDialog.show(ContentActivity.this, "提示", "保存成功"+count+"条");
    }

    @Override
    public void showSwitchButton(boolean show) {
        switch_button.setChecked(show);
    }

    @Override
    public void initData(NodeEntity nodeEntity) {
        this.nodeEntity = nodeEntity;
        initUIStatus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_1:
                mPresenter.saveTableData(mTableViewModel);
                break;
            case R.id.action_2:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
