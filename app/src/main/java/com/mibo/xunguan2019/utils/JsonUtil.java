package com.mibo.xunguan2019.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author liuyangchao
 * Date on 2019/6/25.9:10
 */

public class JsonUtil {

    private static final String TAG = "jsonUtil";
    private JSONObject jsonObject;

    private JsonUtil(String json) {
        Log.e(TAG, "json=" + json);
        jsonObject = getJsonObject(json);
        if (jsonObject == null) {
            Log.e(TAG, "jsonobject is null");
        }
    }

    private JsonUtil() {
        super();
    }

    public static JsonUtil newJsonUtil(String json) {
        JsonUtil util = new JsonUtil(json);
        return util;
    }

    /**
     * get json object
     *
     * @param json
     *            json data
     * @return JOSNObject
     */
    public JSONObject getJsonObject(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "create jsonobject exception");
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * get String data
     *
     * @param json
     *            json data
     * @param key
     *            param
     * @return String data
     * @throws JSONException
     */
    public String getString(String key) throws JSONException {
        if (jsonObject != null) {
            return jsonObject.getString(key);
        } else {
            return null;
        }

    }

    /**
     * get String data
     *
     * @param json
     *            json data
     * @param key
     *            param
     * @return int data
     * @throws JSONException
     */
    public int getInt(String key) throws JSONException {
        if (jsonObject != null) {
            return jsonObject.getInt(key);
        } else {
            return -1;
        }

    }

    /**
     * get Double data
     *
     * @param json
     *            json data
     * @param key
     *            param
     * @return double data
     * @throws JSONException
     */
    public double getDouble(String key) throws JSONException {
        if (jsonObject != null) {
            return jsonObject.getDouble(key);
        } else {
            return -1;
        }

    }

    /**
     * This Method use in jsonObject get current class with object
     *
     * @param jsonObject
     * @param c
     *            class
     * @return object
     * @throws Exception
     */
    public Object getObject(Class<?> c) throws Exception {
        if (jsonObject != null) {
            return getObject(c.getSimpleName().toLowerCase(), c);
        } else {
            return null;
        }
    }

    /**
     * This Method use in jsonObject get current class with object
     *
     * @param jsonObject
     * @param key
     *            query key
     * @param c
     *            class
     * @return object
     * @throws Exception
     */
    public Object getObject(String key, Class<?> c) throws Exception {
        if (jsonObject != null) {
            return getObject(jsonObject, key, c);
        } else {
            return null;
        }
    }

    public Object getObject(JSONObject jsonObject, Class<?> c) throws Exception {
        return getObject(jsonObject, c.getSimpleName().toLowerCase(), c);
    }

    /**
     * This Method use in jsonObject get current class with object
     *
     * @param jsonObject
     * @param key
     *            query key
     * @param c
     *            class
     * @return object
     * @throws Exception
     */
    public static Object getObject(JSONObject jsonObject, String key, Class<?> c) throws Exception {
        //Log.e(TAG, "key ==  " + key);
        Object bean = null;

        if (jsonObject != null) {
            JSONObject jo = null;
            if (key != null) {
                jo = jsonObject.getJSONObject(key);
            } else {
                jo = jsonObject;
            }
            if (jo != null) {
                if (c.equals(null)) {
                    Log.e(TAG, "class is null");
                    bean = jo.get(key);
                } else {
                    bean = c.newInstance();
                    Field[] fs = c.getDeclaredFields();
                    for (int i = 0; i < fs.length; i++) {
                        Field f = fs[i];
                        f.setAccessible(true);
                        Type type = f.getGenericType();
                        String value;
                        try {
                            value = jo.getString(f.getName());
                        } catch (Exception e) {
                            value = "";
                        }
                        if (type.equals(int.class)) {
                            f.setInt(bean, Integer.valueOf(value));
                        } else if (type.equals(double.class)) {
                            f.setDouble(bean, Double.valueOf(value));
                        } else {
                            f.set(bean, value);
                        }
                    }
                }
            } else {
                Log.e(TAG, "in jsonobject not key ");
            }
        } else {
            Log.e(TAG, "current param jsonobject is null");
        }
        return bean;
    }

    /**
     * This method use in jsonObject get list object
     *
     * @param key
     *            list key
     * @param objectKey
     *            object key
     * @param c
     *            object
     * @return list
     * @throws Exception
     */
    public ArrayList<Object> getList(String key, Class<?> c) throws Exception {
        ArrayList<Object> list = null;
        try {
            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                if (!jsonArray.isNull(0)) {
                    list = new ArrayList<Object>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object object = null;
                        if (c == String.class) {
                            String stringObject = jsonArray.getString(i);
                            list.add(stringObject);
                        } else {
                            JSONObject jsObject = jsonArray.getJSONObject(i);
                            object = getObject(jsObject, null, c);
                            list.add(object);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public ArrayList<?> getSpecTypeList(String key, Class<?> c) throws Exception {
        ArrayList<Object> list = null;
        try {
            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                if (!jsonArray.isNull(0)) {
                    list = new ArrayList<Object>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object object = null;
                        if (c == String.class) {
                            String stringObject = jsonArray.getString(i);
                            list.add(stringObject);
                        } else {
                            JSONObject jsObject = jsonArray.getJSONObject(i);
                            object = getObject(jsObject, null, c);
                            list.add(object);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public Map<Object, Object> getMap() {
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        Iterator keyIter = jsonObject.keys();
        Object key = null;
        Object value = null;
        while (keyIter.hasNext()) {
            key = keyIter.next();
            try {
                value = jsonObject.get((String) key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(key, value);
        }
        return map;
    }

    public Map<String, String> getStringMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator keyIter = jsonObject.keys();
        Object key = null;
        Object value = null;
        while (keyIter.hasNext()) {
            key = keyIter.next();
            try {
                value = jsonObject.get((String) key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put((String)key, (String)value);
        }
        return map;
    }

    /**
     * Test class field value
     *
     * @param c
     * @param classObject
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String getFieldValue(Class<?> c, Object classObject) throws IllegalArgumentException, IllegalAccessException {
        StringBuffer sb = new StringBuffer();
        Field[] fs = c.getFields();
        for (int i = 0; i < fs.length; i++) {
            String s = fs[i].getName() + "=" + fs[i].get(classObject);
            sb.append(s).append("\n");
        }
        // Log.e(TAG, sb.toString());
        return sb.toString();
    }

    @SuppressWarnings({ "deprecation", "unused", "unchecked" })
    public static Object fromJSONObject2Object(JSONObject json, Class pojo) throws Exception {
        // 首先得到pojo所定义的字段
        Field[] fields = pojo.getDeclaredFields();
        // 根据传入的Class动态生成pojo对象
        Object obj = pojo.newInstance();
        for (Field field : fields) {
            // 设置字段可访问（必须，否则报错）
            field.setAccessible(true);
            // 得到字段的属性名
            String name = field.getName();
            // 这一段的作用是如果字段在JSONObject中不存在会抛出异常，如果出异常，则跳过。
            try {
                json.get(name);
            } catch (Exception ex) {
                continue;
            }
            if (json.get(name) != null && !"".equals(json.getString(name))) {
                // 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
                if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    field.set(obj, Long.parseLong(json.getString(name)));
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, json.getString(name));
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    field.set(obj, Double.parseDouble(json.getString(name)));
                } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    field.set(obj, Integer.parseInt(json.getString(name)));
                } else if (field.getType().equals(java.util.Date.class)) {
                    field.set(obj, Date.parse(json.getString(name)));
                } else {
                    continue;
                }
            }
        }
        return obj;
    }

    public static ArrayList<Object> jsonArray2List(JSONArray jsonArray, Class<?> c) throws Exception {
        ArrayList<Object> list = null;
        try {
            if (!jsonArray.isNull(0)) {
                list = new ArrayList<Object>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object object = null;
                    if (c == String.class) {
                        String stringObject = jsonArray.getString(i);
                        list.add(stringObject);
                    } else {
                        JSONObject jsObject = jsonArray.getJSONObject(i);
                        object = getObject(jsObject, null, c);
                        list.add(object);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    
}
