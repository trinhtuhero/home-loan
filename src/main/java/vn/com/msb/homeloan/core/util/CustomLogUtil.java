package vn.com.msb.homeloan.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class CustomLogUtil {

  public static String getRequestParamsInfo(HttpServletRequest request) {

    Map<String, String> map = new HashMap<String, String>();

    Enumeration paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String key = (String) paramNames.nextElement();
      String value = request.getParameter(key);
      map.put(key, value);
    }

    return convertWithStream(map);
  }

  private static String convertWithStream(Map<?, ?> map) {
    String mapAsString = map.keySet().stream()
        .map(key -> "\"" + key + "\"" + "=" + "\"" + map.get(key) + "\"")
        .collect(Collectors.joining(", ", "{", "}"));
    return mapAsString;
  }

  public static String getHeadersInfo(HttpServletRequest request) {

    Map<String, String> map = new HashMap<String, String>();

    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }

    return convertWithStream(map);
  }

  public static String getBodyOfRequest(HttpServletRequest request) {
    String requestBody = HttpRequestUtil.getBodyContent(request);
    return requestBody;
  }

  public static String writeValueAsString(ObjectMapper mapper, Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException jsonProcessingException) {
      return null;
    }
  }
}
