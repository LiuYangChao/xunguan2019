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

package com.mibo.xunguan2019.module_content.model;

import com.evrencoskun.tableview.filter.IFilterableModel;
import com.evrencoskun.tableview.sort.ISortableModel;

/**
 * Created by evrencoskun on 11/06/2017.
 */

public class Cell implements ISortableModel, IFilterableModel {

    private String mId;
    private Object mData;
    private String mFilterKeyword;
    private String type;                        //CELL的类型显示，输入，勾选，按钮，文件1/2/3/4/5

    public Cell(String id) {
        this.mId = id;
    }

    public Cell(String id, Object data) {
        this.mId = id;
        this.mData = data;
        this.mFilterKeyword = String.valueOf(data);
    }

    public Cell(String id, Object data, String type) {
        this.mId = id;
        this.mData = data;
        this.mFilterKeyword = String.valueOf(data);
        this.type = type;
    }

    /**
     * This is necessary for sorting process.
     * See ISortableModel
     */
    @Override
    public String getId() {
        return mId;
    }

    /**
     * This is necessary for sorting process.
     * See ISortableModel
     */
    @Override
    public Object getContent() {
        return mData;
    }


    public Object getData() {
        return mData;
    }

    public void setData(String data) { mData = data; }

    public String getFilterKeyword() {
        return mFilterKeyword;
    }

    public void setFilterKeyword(String filterKeyword) {
        this.mFilterKeyword = filterKeyword;
    }

    @Override
    public String getFilterableKeyword() {
        return mFilterKeyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

