package com.mibo.xunguan2019.utils;

import android.app.Activity;
import android.content.Intent;

import com.mibo.xunguan2019.common.Constants;
import com.mibo.xunguan2019.module_content.ContentActivity;
import com.mibo.xunguan2019.module_home.HomeActivity;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;

import java.io.Serializable;

/**
 * Created by 皓然 on 2017/7/12.
 * intent跳转类
 */

public class IntentUtil {

    public static void ToHomeActivity(Activity context){
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void ToContentActivity(Activity context, NodeEntity nodeEntity){
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(Constants.NODE_ENTITY, (Serializable) nodeEntity);
        context.startActivity(intent);
    }

}
