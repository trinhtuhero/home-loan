package vn.com.msb.homeloan.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestUtil {

  public static String getBodyContent(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    try (InputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      log.error("[HttpRequestUtil] getBodyContent error: ", e);
    }
    return sb.toString();
  }
}