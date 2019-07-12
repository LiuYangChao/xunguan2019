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

package com.mibo.xunguan2019.module_content.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;


import com.mibo.xunguan2019.R;
import com.mibo.xunguan2019.module_content.model.Cell;
import com.mibo.xunguan2019.module_content.model.ColumnHeader;
import com.mibo.xunguan2019.module_content.model.RowHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class TableViewModel {

    //定义的Cell类型，分别是显示，输入，勾选，按钮，文件
    public static final int CELL_DISPLAY = 1;
    public static final int CELL_INPUT = 2;
    public static final int CELL_CLICK = 3;
    public static final int CELL_BUTTON = 4;
    public static final int CELL_FILE = 5;

    // Drawables
    private final Drawable mBoyDrawable;
    private final Drawable mGirlDrawable;
    private final Drawable mHappyDrawable;
    private final Drawable mSadDrawable;

    private Context mContext;

    public TableViewModel(Context context) {
        mContext = context;

        // initialize drawables
        mBoyDrawable = ContextCompat.getDrawable(context, R.drawable.ic_male);
        mGirlDrawable = ContextCompat.getDrawable(context, R.drawable.ic_female);
        mHappyDrawable = ContextCompat.getDrawable(context, R.drawable.ic_happy);
        mSadDrawable = ContextCompat.getDrawable(context, R.drawable.ic_sad);
    }

    private List<List<Cell>> cellList;
    private List<RowHeader> rowHeaderList;
    private List<ColumnHeader> columnHeaderList;

    public void setCellList(List<List<Cell>> cellList) {
        this.cellList = cellList;
    }

    public void setRowHeaderList(List<RowHeader> rowHeaderList) {
        this.rowHeaderList = rowHeaderList;
    }

    public void setColumnHeaderList(List<ColumnHeader> columnHeaderList) {
        this.columnHeaderList = columnHeaderList;
    }

    public List<List<Cell>> getCellList() {
        return cellList;
    }

    public List<RowHeader> getRowHeaderList() {
        return rowHeaderList;
    }

    public List<ColumnHeader> getColumnHeaderList() {
        return columnHeaderList;
    }
}
