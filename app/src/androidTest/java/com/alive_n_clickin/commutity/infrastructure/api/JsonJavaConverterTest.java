package com.alive_n_clickin.commutity.infrastructure.api;

import android.test.AndroidTestCase;

import com.alive_n_clickin.commutity.infrastructure.api.JsonJavaConverter;
import com.google.gson.internal.LinkedTreeMap;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class JsonJavaConverterTest extends AndroidTestCase {

    private JsonJavaConverter<BagOfThings> converter;
    private final String JSON_PRIMITIVES1 = "\"value1\":1";
    private final String JSON_PRIMITIVES2 = "\"value2\":\"abc\"";
    private final String JSON_OBJECT = "\"objectValue\":{" + JSON_PRIMITIVES1 + "," + JSON_PRIMITIVES2 + "}";
    private final String JSON_OBJECT_REVERSE = "\"objectValue\":{" + JSON_PRIMITIVES2 + "," + JSON_PRIMITIVES1 + "}";
    public  final BagOfThings BAG_OF_PRIMITIVES = BagOfThings.getBagOfPrimitives();
    public  final BagOfThings BAG_WITH_OBJECT = BagOfThings.getBagWithObject();

    @Override
    protected void setUp() throws Exception {
        converter = new JsonJavaConverter<>(BagOfThings.class);
    }

    public void testFromJson() {
        //Convert object with only primitives
        BagOfThings testObject1 = converter.toJava("{" + JSON_PRIMITIVES1 + "," + JSON_PRIMITIVES2 + "}");
        assertEquals("Primitive JSON to Java", testObject1, BAG_OF_PRIMITIVES);

        //Convert an object with an object in it.
        BagOfThings testObject2 = converter.toJava("{" + JSON_PRIMITIVES1 + "," + JSON_PRIMITIVES1 + "," + JSON_OBJECT + "}");
        assertEquals("JSON with object to Java", testObject2.value1, BAG_WITH_OBJECT.value1);
        assertEquals("JSON with object to Java", testObject2.value2, BAG_WITH_OBJECT.value2);
        //The object will become a LinkedTreeMap. We could also test if it contains the right values, but this might be overkill.
        assertTrue("JSON with object to Java", testObject2.objectValue instanceof LinkedTreeMap);
    }

    public void testToJson() {
        //An object with only primitive fields and a Null object field. The null object should be ignored
        assertTrue("Primitive Java to Json", converter.toJson(BAG_OF_PRIMITIVES).contains(JSON_PRIMITIVES1));
        assertTrue("Primitive Java to Json", converter.toJson(BAG_OF_PRIMITIVES).contains(JSON_PRIMITIVES2));

        //An object with at least one field that is an object
        String json = converter.toJson(BAG_WITH_OBJECT);
        //The order in which the parameters appear int the object value doesn't matter
        assertTrue("Bag with object to Json", json.contains(JSON_OBJECT) || json.contains(JSON_OBJECT_REVERSE));
    }

    private static class BagOfThings {

        private int value1 = 1;
        private String value2 = "abc";
        private transient int value3 = 3;
        private Object objectValue;

        public static BagOfThings getBagOfPrimitives() {
            return new BagOfThings();
        }

        public static BagOfThings getBagWithObject() {
            BagOfThings bag = new BagOfThings();
            bag.objectValue = new BagOfThings();
            return bag;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BagOfThings)) {
                return false;
            }
            BagOfThings tempObj = (BagOfThings) obj;
            if (tempObj.objectValue != null) {
                return tempObj.value1 == this.value1 &&
                        tempObj.value2.equals(value2) &&
                        tempObj.value3 == this.value3 &&
                        tempObj.objectValue.equals(this.objectValue);
            }
            return tempObj.value1 == this.value1 &&
                    tempObj.value2.equals(this.value2) &&
                    tempObj.value3 == this.value3 &&
                    null == this.objectValue;
        }

        @Override
        public String toString() {
            return value1 + value2 + value3 + objectValue;
        }
    }
}
