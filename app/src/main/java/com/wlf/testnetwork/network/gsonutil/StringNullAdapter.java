package com.wlf.testnetwork.network.gsonutil;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * =============================================
 * coder: wulingfeng
 * 创建时间: 2018/4/19
 * 描述:
 * 备注:
 * =============================================
 */

public class StringNullAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader reader) throws IOException {
        // TODO Auto-generated method stub
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        }
        return reader.nextString();
    }
    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        // TODO Auto-generated method stub
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
