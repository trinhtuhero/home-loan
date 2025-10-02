package vn.com.msb.homeloan.core.service.impl;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.service.RedisService;
import vn.com.msb.homeloan.core.util.MapperUtil;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  private final EnvironmentProperties environmentProperties;

  /**
   * Put value as Object to redis
   *
   * @param key
   * @param value
   * @param expiredTime
   * @param timeUnit
   */
  @Override
  public void put(String key, Object value, int expiredTime, TimeUnit timeUnit) {
    key = formatKeyAppPrefix(key);
    redisTemplate.opsForValue().set(key, value);
    if (expiredTime != -1) {
      redisTemplate.expire(key, expiredTime, timeUnit);
    }
  }

  /**
   * Put value as Object to a Hash
   *
   * @param key
   * @param field
   * @param value
   */
  @Override
  public void putToHash(String key, String field, Object value) {
    key = formatKeyAppPrefix(key);
    HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
    hashOperations.put(key, field, value);
  }

  /**
   * Get a key from redis
   *
   * @param key
   * @param <T>
   * @return
   */
  @Override
  public <T> T get(String key) {
    key = formatKeyAppPrefix(key);
    return (T) redisTemplate.opsForValue().get(key);
  }

  /**
   * Get a key from Hash
   *
   * @param key
   * @param field
   * @param <T>
   * @return
   */
  @Override
  public <T> T getFromHash(String key, String field) {
    key = formatKeyAppPrefix(key);
    HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
    return (T) hashOperations.get(key, field);
  }

  /**
   * Delete a key from redis
   *
   * @param key
   */
  @Override
  public boolean remove(String key) {
    key = formatKeyAppPrefix(key);
    return redisTemplate.delete(key);
  }

  /**
   * Update a value as Object in redis
   *
   * @param key
   * @param value
   */
  @Override
  public void update(String key, Object value) {
    key = formatKeyAppPrefix(key);
    Long expireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
    redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
  }

  /**
   * Update a value as Byte[] in redis
   *
   * @param key
   * @param value
   */
  @Override
  public void update(String key, Byte[] value) {
    key = formatKeyAppPrefix(key);
    Long expireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
    redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
  }

  @Override
  public void update(String key, Object value, int expiredTime, TimeUnit timeUnit) {
    key = formatKeyAppPrefix(key);
    if (!redisTemplate.hasKey(key)) {
      redisTemplate.opsForValue().set(key, value);
    } else {
      Long expireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
      redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }
    if (expiredTime != -1) {
      redisTemplate.expire(key, expiredTime, timeUnit);
    }
  }

  /**
   * Check a key is expired
   *
   * @param key
   * @return
   */
  @Override
  public boolean isExpired(String key) {
    key = formatKeyAppPrefix(key);
    return !redisTemplate.hasKey(key);
  }

  @Override
  public Long getExpire(String key) {
    key = formatKeyAppPrefix(key);
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  @Override
  public String formatKeyAppPrefix(String key) {
    return environmentProperties.getApplicationName() + "::" + key;
  }

  @Override
  public Map search(String q) {
    String keyPattern = environmentProperties.getApplicationName() + "*";
    if (StringUtils.isNotBlank(q) && !"ALL".equals(q)) {
      keyPattern = formatKeyAppPrefix(q) + "*";
    }
    Set<String> keys = redisTemplate.keys(keyPattern);
    log.info("KEYS: {}", MapperUtil.object2Json(keys));
    if (CollectionUtils.isEmpty(keys)) {
      return null;
    }
    Map<String, Object> result = new HashMap<>();
    String prefix = environmentProperties.getApplicationName() + "::";
    for (String key : keys) {
      String fmtKey = key.replace(prefix, "");
      Object value = redisTemplate.opsForValue().get(key);
      Long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
      String data = time + "::" + value;
      result.put(fmtKey, data);
    }
    log.info("VALUES: {}", MapperUtil.object2Json(result));
    return result;
  }

  @Override
  public boolean hasKey(String key) {
    key = formatKeyAppPrefix(key);
    return redisTemplate.hasKey(key);
  }
}
