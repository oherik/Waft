package com.alive_n_clickin.commutity.infrastructure.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author hjorthjort
 *         Created 22/09/15
 * Methods for parsing JSON in to Java Objects of a given class, and vice versa
 *
 * Package private, this class handles low level functionality, and should not be visible to other packages.
 * @since 0.1
 */
class JsonJavaConverter<T> {
    
    private final String LOG_TAG = this.getClass().getSimpleName();
    private static final Gson GSON = new Gson();
    private static final JsonParser PARSER = new JsonParser();
    private final Class<T> classType;

    /**
     * Create a converter that will be able to parse and return objects of a certain type
     * @param classType The Class object for the class which the converter will be able to work with
     */
    public JsonJavaConverter(Class<T> classType) {
        this.classType = classType;
    }

    public List<T> toJavaList(String json) {
        //Type token is necessary to get the type of a list with generic type
        TypeToken typeToken = new TypeToken<List<T>>(){};
        //This parameter tells GSON to create a list with objects of the given type
        Type type = typeToken.getType();
        return GSON.fromJson(json, new TypeToken<List<T>>(){}.getType());
    }

    /**
     * Take a JSON object an turn it into an object of the type matching the class of the converter.
     * IMPORTANT! The format of the string should be in json NOT jsonp.
     * @param json is simply the string in json format.
     * @param startNode is the node within the json file to start converting data from.
     * @return the type matching the class of the converter or null if unsuccessful
     */
    public T toJava(String json,String startNode) {
        JsonObject obj = PARSER.parse(json).getAsJsonObject();
        try{
            return GSON.fromJson(obj.get(startNode), classType);
        } catch(com.google.gson.JsonSyntaxException e) {
            return null;
        }
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
