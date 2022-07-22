package com.app.util;

public class JsonUtil {

    public static String object2Json(Object object) {
        return JsonConverter.getInstance().object2Json(object);
    }

}