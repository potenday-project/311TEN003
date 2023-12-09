package com.bside.bside_311.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JsonParseUtilTest {

  @Test()
  public void test() {
    String taste = " {\n" +
                       "      \"Aroma\": \"과일, 다채로운\",\n" +
                       "      \"Taste\": \"과일, 오크, 초콜릿, 산미\",\n" +
                       "      \"Finish\": \"우아한\"\n" +
                       "    }";
    try {
      String s = JsonParseUtil.tastePreProcessing(new ObjectMapper().readValue(taste, Map.class));
      System.out.println(s);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test()
  public void reverseTest() {
    String taste = " {\n" +
                       "      \"Aroma\": \"과일, 다채로운\",\n" +
                       "      \"Taste\": \"과일, 오크, 초콜릿, 산미\",\n" +
                       "      \"Finish\": \"우아한\"\n" +
                       "    }";
    try {
      String s = JsonParseUtil.tastePreProcessing(new ObjectMapper().readValue(taste, Map.class));
      Map<String, Object> stringObjectMap = JsonParseUtil.tasteMapProcessing(s);
      System.out.println(stringObjectMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}