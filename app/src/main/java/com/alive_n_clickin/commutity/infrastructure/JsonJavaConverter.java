package com.alive_n_clickin.commutity.infrastructure;

import android.util.Log;

import com.google.gson.Gson;

/**
 * @author hjorthjort
 *         Created 22/09/15
 * Methods for parsing JSON in to Java Objects of a given class, and vice versa
 *
 * Package private, this class handles low level functionality, and should not be visible to other packages.
 */
class JsonJavaConverter<T> {
    
    private final String LOG_TAG = this.getClass().getSimpleName();
    private static final Gson GSON = new Gson();
    private final Class<T> classType;

    /**
     * Create a converter that will be able to parse and return objects of a certain type
     * @param classType The Class object for the class which the converter will be able to work with
     */
    public JsonJavaConverter(Class<T> classType) {
        this.classType = classType;
    }

    /**
     * Take a JSON object an turn it into an object of the type matching the class of the converter
     * @param json
     * @return
     */
    public T toJava(String json) {
        return GSON.fromJson(json, classType);
    }

    /**
     * Turn a Java object into a JSON object.
     * @param object
     * @return
     */
    public String toJson(T object) {
        try {
            return GSON.toJson(object);
        } catch (StackOverflowError e) {
            Log.e(LOG_TAG, "Stack Overflow: Is there a circular reference in the object you tried to parse? https://sites.google.com/site/gson/gson-user-guide#TOC-Object-Examples", e);
            throw e;
        }
    }
}
