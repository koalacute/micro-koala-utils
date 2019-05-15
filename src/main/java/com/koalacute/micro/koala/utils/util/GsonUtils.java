package com.koalacute.micro.koala.utils.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson Tools
 *
 * Gson 谷歌的JSON处理工具， 好处是不用担心缺失的属性. 如： 我们的映射对象有A、B、C三个属性,
 * 如果JSON字符串缺失了某一个，转换时候不会报错(这样就可以差别不大的映射对象共用), Jackson会报错。
 *
 */
public class GsonUtils {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final Gson gson2 = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final Gson gson3 = new GsonBuilder().serializeNulls().setDateFormat("yyyy/M/d HH:mm:ss").create();
    private static final Gson gson4  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
    private static final Gson gson5 = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();

    /**
     * <pre>
     * JSON字符串转换为List数组, 提供两种方式(主要解决调用的容易程度)
     *     1. TypeToken<List<T>> token 参数转换
     *     2. Class<T> cls 方式转换
     *
     * @param json
     * @return List<T>
     *
     * <pre>
     */
    public static <T> List<T> convertList(String json, TypeToken<List<T>> token) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<T>();
        }
        return gson.fromJson(json, token.getType());
    }

    /**
     * Convert By TypeToken
     *
     * @param json
     * @param token
     * @return
     */
    public static <T> T convert(String json, TypeToken<T> token) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return gson.fromJson(json, token.getType());
    }

    /**
     * <pre>
     * JSON字符串转换为List数组, 提供两种方式(主要解决调用的容易程度)
     *     1. TypeToken<List<T>> token 参数转换
     *     2. Class<T> cls 方式转换
     *
     * @param json
     * @param cls
     * @return List<T>
     * </pre>
     */
    public static <T> List<T> convertList(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<T>();
        }
        Type type = new TypeToken<List<JsonObject>>() {
        }.getType();
        List<JsonObject> jsonObjs = gson.fromJson(json, type);
        List<T> listOfT = new ArrayList<T>();
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(convertObj(jsonObj.toString(), cls));
        }
        return listOfT;
    }

    /**
     * JSON字符串转换为List，忽略null值的字段
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList2(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<T>();
        }
        List<JsonObject> jsonObjectList = gson2.fromJson(json, new TypeToken<List<JsonObject>>() {}.getType());
        List<T> listOfT = new ArrayList<T>();
        for (JsonObject jsonObj : jsonObjectList) {
            listOfT.add(convertObj(jsonObj.toString(), cls));
        }
        return listOfT;
    }

    /**
     * <pre>
     * Json格式转换, 由JSON字符串转化到制定类型T
     *
     * @param json
     * @param cls
     * @return T
     *
     * <pre>
     */
    public static <T> T convertObj(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return gson.fromJson(json, cls);
    }

    public static <T> T convertObj2(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return gson2.fromJson(json, cls);
    }

    public static <T> T convertObj3(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return gson3.fromJson(json, cls);
    }

    public static <T> T convertObj4(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return gson4.fromJson(json, cls);
    }

    public static String toJson5(Object obj) {
        if (obj == null) {
            return "";
        }
        return gson5.toJson(obj);
    }


    public static <T> TypeToken<T> getTypeToken(T t) {
        return new TypeToken<T>() {
        };
    }

    /**
     * <pre>
     * java对象转化JSON
     *
     * @return String
     *
     * <pre>
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        return gson.toJson(obj);
    }

    /**
     * <pre>
     * java对象转化JSON(并输出Null字段)
     *
     * @return String
     *
     * <pre>
     */
    public static String toJsonWithNull(Object obj) {
        if (obj == null) {
            return "";
        }
        return gson2.toJson(obj);
    }

    public static String getJsonObjectAsString(JsonObject jsonObject, String name) {
        if (jsonObject == null || StringUtils.isBlank(name)) {
            return null;
        }
        JsonElement jsonElement = jsonObject.get(name);
        return (jsonElement == null) ? null : jsonElement.getAsString();
    }

    public static JsonObject getJsonObjectChild(JsonObject jsonObject, String name) {
        if (jsonObject == null || StringUtils.isBlank(name)) {
            return null;
        }
        JsonElement jsonElement = jsonObject.get(name);
        return (jsonElement == null) ? null : jsonElement.getAsJsonObject();
    }

    public static boolean getJsonObjectAsBoolean(JsonObject jsonObject, String name) {
        if (jsonObject == null || StringUtils.isBlank(name)) {
            return false;
        }
        JsonElement jsonElement = jsonObject.get(name);
        return (jsonElement == null) ? false : jsonElement.getAsBoolean();
    }
}
