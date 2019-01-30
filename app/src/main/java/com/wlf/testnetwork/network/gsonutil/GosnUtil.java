package com.wlf.testnetwork.network.gsonutil;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import static android.text.TextUtils.isEmpty;

/**
 * =============================================
 *
 * @author: wlf
 * @date: 2019/1/30
 * @eamil: 845107244@qq.com
 * 描述:
 * 备注:
 * =============================================
 */

public class GosnUtil {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 以下为数据解析代码
     * @param entity 数据json字符串
     * @param tClass 要解析成的model类型
     * @param <T> 返回解析完成的model
     * @return
     * @throws IllegalStateException
     */
    public static <T> T parse(String entity, Class<T> tClass) throws IllegalStateException{
        T t = null;
        Gson gson = buildGson();
        try {
            t = (T)gson.fromJson(entity,tClass);
        }catch (Exception e){
            Log.e("解析错误",e.toString());
        }
        return t;
    }

    public static String parse(Object obj){
        return parse(obj, DEFAULT_DATE_PATTERN);
    }

    public static String parse(Object obj, String datePattern){
        return buildGson(datePattern).toJson(obj);
    }

    public static Gson buildGson() {
        return buildGson(DEFAULT_DATE_PATTERN);
    }

    public static Gson buildGson(String datePattern){
        if (isEmpty(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setDateFormat(datePattern)
                .create();
        return gson;
    }



    private static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("")) {
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    private static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("")) {
                    return Double.parseDouble("0");
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

}
