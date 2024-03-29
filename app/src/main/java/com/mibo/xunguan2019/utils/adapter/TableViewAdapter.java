/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.mibo.xunguan2019.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.evrencoskun.tableview.sort.SortState;
import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.db.entity.CellEntity;
import com.mibo.xunguan2019.module_content.holder.CellButtonHolder;
import com.mibo.xunguan2019.module_content.holder.CellClickHolder;
import com.mibo.xunguan2019.module_content.holder.CellFileHolder;
import com.mibo.xunguan2019.module_content.holder.CellInputHolder;
import com.mibo.xunguan2019.module_content.holder.CellViewHolder;
import com.mibo.xunguan2019.module_content.holder.ColumnHeaderViewHolder;
//import com.mibo.xunguan2019.module_content.holder.MoodCellViewHolder;
import com.mibo.xunguan2019.module_content.holder.RowHeaderViewHolder;
import com.mibo.xunguan2019.module_content.holder.TableViewModel;
import com.mibo.xunguan2019.module_content.model.Cell;
import com.mibo.xunguan2019.module_content.model.CellModel.FCell;
import com.mibo.xunguan2019.module_content.model.ColumnHeader;
import com.mibo.xunguan2019.module_content.model.RowHeader;
import com.mibo.xunguan2019.utils.GsonHelper;

import java.util.List;

/**
 * Created by evrencoskun on 11/06/2017.
 * <p>
 * This is a sample of custom TableView Adapter.
 */

public class TableViewAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    // Cell View Types by Column Position
//    private static final int MOOD_CELL_TYPE = 1;
//    private static final int GENDER_CELL_TYPE = 2;
    private static final int CELL_DISPLAY = 1;
    private static final int CELL_INPUT = 2;
    private static final int CELL_CLICK = 3;
    private static final int CELL_BUTTON = 4;
    private static final int CELL_FILE = 5;
    // add new one if it necessary..

    private static final String LOG_TAG = TableViewAdapter.class.getSimpleName();

    private TableViewModel mTableViewModel;
    private final LayoutInflater mInflater;

    public TableViewAdapter(Context context, TableViewModel tableViewModel) {
        super(context);
        this.mTableViewModel = tableViewModel;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public TableViewModel getmTableViewModel() {
        return mTableViewModel;
    }

    public void setmTableViewModel(TableViewModel mTableViewModel) {
        this.mTableViewModel = mTableViewModel;
    }

    /**
     * This is where you create your custom Cell ViewHolder. This method is called when Cell
     * RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given type to
     * represent an item.
     *
     * @param viewType : This value comes from "getCellItemViewType" method to support different
     *                 type of viewHolder as a Cell item.
     *
     * @see #getCellItemViewType(int, int);
     */
    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        //TODO check
        Log.e(LOG_TAG, " onCreateCellViewHolder has been called");
        View layout;
//        getCellRecyclerViewAdapter()
        switch (viewType) {
            case CELL_DISPLAY:
                layout = mInflater.inflate(R.layout.table_view_cell_layout, parent, false);
                return new CellViewHolder(layout);
            case CELL_INPUT:
                layout = mInflater.inflate(R.layout.cell_type_input, parent, false);
                return new CellInputHolder(layout);
            case CELL_CLICK:
                layout = mInflater.inflate(R.layout.cell_type_click, parent, false);
                return new CellClickHolder(layout);
            case CELL_BUTTON:
                layout = mInflater.inflate(R.layout.cell_type_button, parent, false);
                return new CellButtonHolder(layout);
            case CELL_FILE:
                layout = mInflater.inflate(R.layout.cell_type_file, parent, false);
                return new CellFileHolder(layout);
            default:
                layout = mInflater.inflate(R.layout.table_view_cell_layout, parent, false);
                return new CellViewHolder(layout);
        }
    }

    /**
     * That is where you set Cell View Model data to your custom Cell ViewHolder. This method is
     * Called by Cell RecyclerView of the TableView to display the data at the specified position.
     * This method gives you everything you need about a cell item.
     *
     * @param holder         : This is one of your cell ViewHolders that was created on
     *                       ```onCreateCellViewHolder``` method. In this example we have created
     *                       "CellViewHolder" holder.
     * @param cellItemModel  : This is the cell view model located on this X and Y position. In this
     *                       example, the model class is "Cell".
     * @param columnPosition : This is the X (Column) position of the cell item.
     * @param rowPosition    : This is the Y (Row) position of the cell item.
     *
     * @see #onCreateCellViewHolder(ViewGroup, int) ;
     */
    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int
            columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;
        switch (JudgeViewType(cell)) {
            case CELL_DISPLAY:
                CellViewHolder viewHolder_display = (CellViewHolder) holder;
                viewHolder_display.setCell(cell);
                break;
            case CELL_INPUT:
                CellInputHolder inputViewHolder = (CellInputHolder) holder;
                inputViewHolder.setCell(cell);
                break;
            case CELL_CLICK:
                CellClickHolder clickViewHolder = (CellClickHolder) holder;
                clickViewHolder.setCell(cell);
                break;
            case CELL_BUTTON:
                CellButtonHolder buttonViewHolder = (CellButtonHolder) holder;
                buttonViewHolder.setCell(cell, mContext);
                break;
            case CELL_FILE:
                CellFileHolder fileViewHolder = (CellFileHolder) holder;
                fileViewHolder.setCell(cell);
                break;
            default:
                CellViewHolder viewHolder = (CellViewHolder) holder;
                viewHolder.setCell(cell);
                break;
        }
    }

    /**
     * 根据Cell判断某个单元格的属性
     * @param cell
     * @return
     */
    private int JudgeViewType(Cell cell){
        CellEntity cellEntity = (CellEntity) cell.getData();
        if(cellEntity == null){
            return CELL_DISPLAY;
        }else{
            if(cellEntity.getContent() == null){
                return CELL_DISPLAY;
            }else{
                FCell fCell = GsonHelper.convertEntity(cellEntity.getContent(), FCell.class);
                if(cellEntity.getType().equals("描述项")){
                    return CELL_DISPLAY;
                }else{
                    if(fCell == null || fCell.getCheck()==null || fCell.getCheck().size()==0){
                        return CELL_DISPLAY;
                    }else if(fCell.getCheck().size() == 1){
                        switch (fCell.getCheck().get(0).getMeType()){
                            case "填值":
                                return CELL_INPUT;
                            case "打钩":
                                return CELL_CLICK;
                            default:
                                return CELL_DISPLAY;
                        }
                    }else{
                        return CELL_BUTTON;
                    }
                }
            }
        }
    }

    /**
     * This is where you create your custom Column Header ViewHolder. This method is called when
     * Column Header RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item.
     *
     * @param viewType : This value comes from "getColumnHeaderItemViewType" method to support
     *                 different type of viewHolder as a Column Header item.
     *
     * @see #getColumnHeaderItemViewType(int);
     */
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        // TODO: check
        //Log.e(LOG_TAG, " onCreateColumnHeaderViewHolder has been called");
        // Get Column Header xml Layout
        View layout = mInflater.inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    /**
     * That is where you set Column Header View Model data to your custom Column Header ViewHolder.
     * This method is Called by ColumnHeader RecyclerView of the TableView to display the data at
     * the specified position. This method gives you everything you need about a column header
     * item.
     *
     * @param holder                : This is one of your column header ViewHolders that was created
     *                              on ```onCreateColumnHeaderViewHolder``` method. In this example
     *                              we have created "ColumnHeaderViewHolder" holder.
     * @param columnHeaderItemModel : This is the column header view model located on this X
     *                              position. In this example, the model class is "ColumnHeader".
     * @param columnPosition        : This is the X (Column) position of the column header item.
     *
     * @see #onCreateColumnHeaderViewHolder(ViewGroup, int) ;
     */
    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object
            columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeader);
    }

    /**
     * This is where you create your custom Row Header ViewHolder. This method is called when
     * Row Header RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item.
     *
     * @param viewType : This value comes from "getRowHeaderItemViewType" method to support
     *                 different type of viewHolder as a row Header item.
     *
     * @see #getRowHeaderItemViewType(int);
     */
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        // Get Row Header xml Layout
        View layout = mInflater.inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }


    /**
     * That is where you set Row Header View Model data to your custom Row Header ViewHolder. This
     * method is Called by RowHeader RecyclerView of the TableView to display the data at the
     * specified position. This method gives you everything you need about a row header item.
     *
     * @param holder             : This is one of your row header ViewHolders that was created on
     *                           ```onCreateRowHeaderViewHolder``` method. In this example we have
     *                           created "RowHeaderViewHolder" holder.
     * @param rowHeaderItemModel : This is the row header view model located on this Y position. In
     *                           this example, the model class is "RowHeader".
     * @param rowPosition        : This is the Y (row) position of the row header item.
     *
     * @see #onCreateRowHeaderViewHolder(ViewGroup, int) ;
     */
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel,
                                          int rowPosition) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));
    }


    @Override
    public View onCreateCornerView() {
        // Get Corner xml layout
        View corner = mInflater.inflate(R.layout.table_view_corner_layout, null);
        corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortState sortState = TableViewAdapter.this.getTableView()
                        .getRowHeaderSortingStatus();
                if (sortState != SortState.ASCENDING) {
                    Log.d("TableViewAdapter", "Order Ascending");
                    TableViewAdapter.this.getTableView().sortRowHeader(SortState.ASCENDING);
                } else {
                    Log.d("TableViewAdapter", "Order Descending");
                    TableViewAdapter.this.getTableView().sortRowHeader(SortState.DESCENDING);
                }
            }
        });
        return corner;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        // The unique ID for this type of column header item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int columnPosition, int rowPosition) {
        List<List<Cell>> cellList = mTableViewModel.getCellList();
        Cell cell = cellList.get(rowPosition).get(columnPosition);
        return JudgeViewType(cell);
    }
}
