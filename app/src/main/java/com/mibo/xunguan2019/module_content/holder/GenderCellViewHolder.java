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

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mibo.xunguan2019.R;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class GenderCellViewHolder extends MoodCellViewHolder {

    public GenderCellViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data) {
        int gender = (int) data;
        Drawable genderDrawable = ContextCompat.getDrawable(itemView.getContext(), gender ==
                1 ? R.drawable.ic_male : R.drawable.ic_female);

        cell_image.setImageDrawable(genderDrawable);
    }
}
