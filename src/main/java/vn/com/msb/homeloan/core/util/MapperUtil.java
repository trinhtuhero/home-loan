package vn.com.msb.homeloan.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {

  private MapperUtil() {
  }

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  static {
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static <T> T json2Object(String jsonObject, TypeReference<T> type) {
    try {
      return MAPPER.readValue(jsonObject, type);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> T json2Object(String jsonObject, Class<T> valueType) {
    try {
      return MAPPER.readValue(jsonObject, valueType);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static String object2Json(Object data) {
    try {
      return MAPPER.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> Class<T> getTypeHandler(TypeReference<T> type) {
    JavaType javaType = MAPPER.constructType(type);
    return (Class<T>) javaType.getRawClass();
  }

  public static String getValue(String json, String key) {
    try {
      return MAPPER.readTree(json).get(key).asText();
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static JsonNode toJsonNode(String json) {
    try {
      return MAPPER.readTree(json);
    } catch (JsonProcessingException e) {
      return null;
    }
  }
}
