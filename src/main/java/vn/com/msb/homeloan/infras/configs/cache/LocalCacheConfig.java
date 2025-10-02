package vn.com.msb.homeloan.infras.configs.cache;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@Profile("local")
@Slf4j
public class LocalCacheConfig {

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
    redisConf.setHostName(redisHost);
    redisConf.setPort(redisPort);
    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
        //.useSsl().disablePeerVerification().and()
        .commandTimeout(Duration.ofSeconds(2))
        .build();

    LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisConf,
        clientConfig);
    redisConnectionFactory.afterPropertiesSet();
    redisConnectionFactory.getConnection().setConfig("notify-keyspace-events", "Ex");
    return redisConnectionFactory;

  }

  @Bean
  RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    StringRedisSerializer keySerializer = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer valueSerializer =
        new GenericJackson2JsonRedisSerializer();

    redisTemplate.setKeySerializer(keySerializer);
    redisTemplate.setValueSerializer(valueSerializer);
    redisTemplate.setHashValueSerializer(valueSerializer);
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
//  @Bean
//  public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection) {
//    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//    container.setConnectionFactory(redisConnection);
//    Topic topic = new PatternTopic("__keyevent@*__:expired");
//    container.addMessageListener(new ExpirationListenerRedis(), topic);
//    return container;
//  }
}
