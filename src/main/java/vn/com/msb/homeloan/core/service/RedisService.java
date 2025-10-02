package vn.com.msb.homeloan.core.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisService {

  void put(String key, Object value, int expiredTime, TimeUnit timeUnit);

  void putToHash(String key, String field, Object value);

  <T> T get(String key);

  <T> T getFromHash(String key, String field);

  boolean remove(String key);

  void update(String key, Object value);

  void update(String key, Byte[] value);

  void update(String key, Object value, int expiredTime, TimeUnit timeUnit);

  boolean isExpired(String key);

  Long getExpire(String key);

  String formatKeyAppPrefix(String key);

  Map search(String q);

  boolean hasKey(String key);
}
