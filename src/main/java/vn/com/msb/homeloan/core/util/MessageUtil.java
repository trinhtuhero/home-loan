package vn.com.msb.homeloan.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Include handle message general
 *
 * @version 1.0
 */
@Component
public class MessageUtil {

  @Autowired
  private MessageSource msgSource;

  /**
   * Get message
   *
   * @param key
   * @return
   */
  public String getMessage(String key, Locale locale) {
    return getMessage(key, locale, "");
  }

  /**
   * Get message with parameters
   *
   * @param key
   * @param params
   * @return
   */
  public String getMessage(String key, Locale locale, Object... params) {
    List<String> paramStrs = new ArrayList<>();
    for (Object param : params) {
      paramStrs.add(String.valueOf(param));
    }
    return msgSource.getMessage(key, paramStrs.toArray(), locale);
  }

  public String getMessage(String key, Object... params) {
    List<String> paramStrs = new ArrayList<>();
    for (Object param : params) {
      paramStrs.add(String.valueOf(param));
    }
    return msgSource.getMessage(key, paramStrs.toArray(), Locale.US);
  }


}