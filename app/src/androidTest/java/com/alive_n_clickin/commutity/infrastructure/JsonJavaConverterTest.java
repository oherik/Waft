package com.alive_n_clickin.commutity.infrastructure;

import android.test.AndroidTestCase;

import com.google.gson.internal.LinkedTreeMap;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class JsonJavaConverterTest extends AndroidTestCase {

    private final String LOG_TAG = getClass().getSimpleName();
    private JsonJavaConverter<BagOfThings> converter;
    private final String JSON_PRIMITIVES = "{\"value1\":1,\"value2\":\"abc\"}";
    private final String JSON_WITH_OBJECT = "{\"objectValue\":{\"value1\":1,\"value2\":\"abc\"},\"value1\":1,\"value2\":\"abc\"}";
    public  final BagOfThings BAG_OF_PRIMITIVES = BagOfThings.getBagOfPrimitives();
    public  final BagOfThings BAG_WITH_OBJECT = BagOfThings.getBagWithObject();

    @Override
    protected void setUp() throws Exception {
        converter = new JsonJavaConverter<>(BagOfThings.class);
    }

    public void testFromJson() {
        BagOfThings testObject1 = converter.toJava(JSON_PRIMITIVES);
        assertEquals("Primitive JSON to Java", testObject1, BAG_OF_PRIMITIVES);

        BagOfThings testObject2 = converter.toJava(JSON_WITH_OBJECT);
        assertEquals("JSON with object to Java", testObject2.value1, BAG_WITH_OBJECT.value1);
        assertEquals("JSON with object to Java", testObject2.value2, BAG_WITH_OBJECT.value2);
        assertTrue("JSON with object to Java", testObject2.objectValue instanceof LinkedTreeMap);
    }

    public void testToJson() {
        //An object with only primitive fields and a Null object field. The null object should be ignored
        assertEquals("Primitive Java to Json", JSON_PRIMITIVES, converter.toJson(BAG_OF_PRIMITIVES));

        //An object with at least one field that is an object
        String json = converter.toJson(BAG_WITH_OBJECT);
        assertEquals("Bag with object to Json", JSON_WITH_OBJECT, json);
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
