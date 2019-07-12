package com.mibo.xunguan2019.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.UUID;

import static android.text.TextUtils.isEmpty;

/**
 * Author liuyangchao
 * Date on 2019/7/5.10:59
 */

public class PhoneCodeUtils {

    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        //deviceId.append("a");
        try {

            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            //动态申请权限,对于安卓6.0及以上的手机
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //没有权限，申请一下
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);

            }
            String imei = tm.getDeviceId();
            if(!isEmpty(imei)){
                //deviceId.append("imei");
                deviceId.append(imei);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

            //序列号（sn）
            String sn = tm.getSimSerialNumber();
            if(!isEmpty(sn)){
                //deviceId.append("sn");
                deviceId.append(sn);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            //安卓号
            String ANDROID_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            if (!isEmpty(ANDROID_ID)) {
                return ANDROID_ID;
            }

            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if(!isEmpty(wifiMac)){
                //deviceId.append("wifi");
                deviceId.append(wifiMac);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }


            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if(!isEmpty(uuid)){
                deviceId.append("uuid");
                deviceId.append(uuid);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        Log.e("getDeviceId : ", deviceId.toString());
        return deviceId.toString();
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context){
        String uuid = (String) SPUtils.get(context, "uuid", "");
        if(isEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            SPUtils.put(context, "uuid", uuid);
        }
        Log.e("UUID", "getUUID : " + uuid);
        return uuid;
    }

}
