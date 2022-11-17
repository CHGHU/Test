package com.app.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class XmlUtil {

    private static final String LIST_SIGN = "sdo";

    public static Map<String, Object> parseXml2Map(String xmlStr) {
        Map<String, Object> result = new HashMap<>();

        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element rootElement = document.getRootElement();

        } catch (Exception e) {
            log.error("XmlUtil parseXml2Map ERROR!", e);
        }

        return result;
    }

    private static Object xml2Map(Element rootElement) {
        List<Element> elementList = rootElement.elements();

        if (elementList.size() == 0) {
            return rootElement.getTextTrim();

        } else {
            Map<String, Object> map = new HashMap<>();
            List<Object> list = new ArrayList<>();

            for (Element element : elementList) {
                if (LIST_SIGN.equalsIgnoreCase(element.getName())) {
                    list.add(xml2Map(element));
                } else {
                    map.put(element.getName(), xml2Map(element));
                }
            }

            return list.isEmpty() ? map : list;
        }
    }

}