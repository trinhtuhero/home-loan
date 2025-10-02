package vn.com.msb.homeloan.infras.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExpirationListenerRedis implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] pattern) {
    byte[] body = message.getBody();
    String expiredKey = new String(body);
    log.error("expired key: " + expiredKey);
  }
}
