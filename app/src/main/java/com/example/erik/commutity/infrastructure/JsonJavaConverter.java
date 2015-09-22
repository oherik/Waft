package com.example.erik.commutity.infrastructure;

import android.util.Log;

import com.google.gson.Gson;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class JsonJavaConverter<T> {
    
    private final String LOG_TAG = this.getClass().getSimpleName();
    private static final Gson GSON = new Gson();
    private final Class<T> classType;

    public JsonJavaConverter(Class<T> classType) {
        this.classType = classType;
    }

    public T toJava(String json) {
        return GSON.fromJson(json, classType);
    }

    public String toJson(T object) {
        try {
            return GSON.toJson(object);
        } catch (StackOverflowError e) {
            Log.e(LOG_TAG, "Stack Overflow: Is there a circular reference in the object you tried to parse? https://sites.google.com/site/gson/gson-user-guide#TOC-Object-Examples", e);
            throw e;
        }
    }
}
