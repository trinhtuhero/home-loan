package vn.com.msb.homeloan.infras.configs.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineCacheConfig {

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager(
        "rolesCache");
    cacheManager.setCaffeine(caffeineCacheBuilder());
    return cacheManager;
  }

  Caffeine<Object, Object> caffeineCacheBuilder() {

    return Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(500)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        //.weakKeys()
        .recordStats();
  }
}
