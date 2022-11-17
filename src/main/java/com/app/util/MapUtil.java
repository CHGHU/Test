package com.app.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MapUtil {

    private static final String SETTER_PREFIX = "set";

    public static <T> T parseMap2Object(Class<T> clazz, Map<String, Object> map) throws Exception {
        T result = clazz.newInstance();
        if (map == null || map.isEmpty()) {
            return result;
        }

        String clazzPackage = clazz.getPackage().getName() + ".";
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            fields = clazz.getSuperclass().getDeclaredFields();
        }

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldClassName = clazzPackage + fieldName + "Vo";

            if (!"serialVersionUID".equalsIgnoreCase(fieldName) && map.containsKey(fieldName)) {
                String setMethodName = SETTER_PREFIX + StringUtils.capitalize(fieldName);

                Method setMethod;
                try {
                    setMethod = clazz.getDeclaredMethod(setMethodName, field.getType());
                } catch (NoSuchMethodException e) {
                    log.error(e.getMessage());
                    setMethod = clazz.getSuperclass().getDeclaredMethod(setMethodName, field.getType());
                }

                Class<?> typeClass = field.getType();
                Object value = map.get(fieldName);
                try {
                    invokeSetter(result, setMethod, fieldClassName, typeClass, value);
                } catch (Exception e) {
                    log.error("MapUtil invokeSetter ERROR!", e.getMessage());
                }
            }
        }

        return result;
    }

    private static <T> void invokeSetter(T result, Method setMethod, String fieldClassName, Class<?> clazz, Object value) throws Exception {
        if (value instanceof Map) {
            Object obj = parseMap2Object(clazz, (Map<String, Object>) value);
            setMethod.invoke(result, obj);

        } else if (value instanceof List) {
            Class<?> arrayClass = null;
            try {
                arrayClass = Class.forName(fieldClassName);
                List<Object> arrayList = new ArrayList<>();

                for (Object obj : (List<Object>) value) {
                    Object arrayNodeObj = parseMap2Object(arrayClass, (Map<String, Object>) obj);
                    arrayList.add(arrayNodeObj);
                }
                setMethod.invoke(result, arrayList);

            } catch (ClassNotFoundException e) {
                log.error("MapUtil invokeSetter instanceof List ERROR! {} Not Exist!", fieldClassName);
            }

        } else {
            setMethod.invoke(result, value);
        }
    }

}