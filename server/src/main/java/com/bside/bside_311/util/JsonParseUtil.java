package com.bside.bside_311.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParseUtil {
  public static ObjectMapper objectMapper = new ObjectMapper();


  public static String tastePreProcessing(Map<String, String> taste)
      throws JsonProcessingException {

    if (ObjectUtils.isEmpty(taste)) {
      return null;
    }
//    Map<String, Object> tMap = objectMapper.readValue(taste, Map.class);
    Map<String, String> tMap = taste;
    Map<String, Object> newMap = new HashMap<>();
    newMap.put("Aroma", getMapStringDataAsList(tMap, "Aroma"));
    newMap.put("Taste", getMapStringDataAsList(tMap, "Taste"));
    newMap.put("Finish", getMapStringDataAsList(tMap, "Finish"));

    return objectMapper.writeValueAsString(newMap);
  }

  public static List<String> getMapStringDataAsList(Map<String, String> param, String key) {
    String text = param.getOrDefault(key, null);
    List<String> itemList = new ArrayList<>();
    if (StringUtils.contains(text, ",")) {
      String[] split = text.split(",");
      for (String s : split) {
        s = s.replaceAll("\n", "").replaceAll(" ", "");
        itemList.add(s);
      }
    } else {
      itemList.add(text);
    }
    return itemList;
  }

  public static Map<String, Object> tasteMapProcessing(String taste) {
    Map<String, Object> tasteMap = new HashMap<>();
    if (StringUtils.isNotEmpty(taste)) {
      try {
        tasteMap = objectMapper.readValue(taste, Map.class);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return tasteMap;
  }

}
