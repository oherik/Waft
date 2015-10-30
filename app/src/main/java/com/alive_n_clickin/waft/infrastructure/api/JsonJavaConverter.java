package com.alive_n_clickin.waft.infrastructure.api;

import com.alive_n_clickin.waft.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;
import java.util.List;

/**
 * Methods for parsing JSON in to Java Objects of a given class, and vice versa
 *
 * Package private, this class handles low level functionality, and should not be visible to other packages.
 *
 * @since 0.1
 */
public class JsonJavaConverter<T> {
    private static final String LOG_TAG = LogUtils.getLogTag(JsonJavaConverter.class);

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

    /**
     * Turn a JSON list on the form [{...}, {...}, ...] into a Java object where the type of the objects
     * in the JSON array match the Java class.
     * @param json the JSON string
     * @param classArray class of an array with the same type as the list of java objects we want
     * @param <T> the type of the java object to be returned
     * @return a list of type T with objects representing the objects in the JSON array
     */
    public static <T> List<T> toJavaList(String json, Class<T[]> classArray) throws JsonSyntaxException {
        T[] parsedResult = GSON.fromJson(json, classArray);
        return Arrays.asList(parsedResult);
    }

    /**
     * Take a JSON object an turn it into an object of the type matching the class of the converter.
     * IMPORTANT! The format of the string should be in json NOT jsonp.
     * @param json is simply the string in json format.
     * @param startNode is the node within the json file to start converting data from.
     * @return the type matching the class of the converter or null if unsuccessful
     */
    public T toJava(String json,String startNode) throws JsonSyntaxException {
        T parsedResult;
        JsonObject jsonObject = PARSER.parse(json).getAsJsonObject();
        JsonElement startElement = jsonObject.get(startNode);
        if (startElement == null) {
            throw new JsonSyntaxException("Unable to find startElement in json string");
        }
        parsedResult = GSON.fromJson(startElement, classType);
        return parsedResult;
    }
}
