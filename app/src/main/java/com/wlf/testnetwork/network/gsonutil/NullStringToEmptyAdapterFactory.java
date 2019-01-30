package com.wlf.testnetwork.network.gsonutil;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * =============================================
 * coder: wulingfeng
 * 创建时间: 2018/4/19
 * 描述:
 * 备注:
 * =============================================
 */

public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class) {
            return null;
        }
        return (TypeAdapter<T>) new StringNullAdapter();
    }
}