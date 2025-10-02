package vn.com.msb.homeloan.infras.configs.kafka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import vn.com.msb.homeloan.core.model.MobioNotifyMessage;

@Slf4j
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Value(value = "${kafka.mobio.bootstrap-servers}")
  private String mobioBootstrapServers;

  @Autowired
  private DataSource dataSource;

  public ConsumerFactory<String, String> consumerFactory(String groupId) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, mobioBootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
    props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
    props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, false);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
      String groupId) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory(groupId));
    factory.setRetryTemplate(retryTemplate());

    factory.setConcurrency(2);

    factory.setErrorHandler(((exception, record) -> {
      log.error("Error in process with Exception {} and the record is {}", exception, record);

      ConsumerRecord<String, String> consumerRecord = (ConsumerRecord<String, String>) record;

      MobioNotifyMessage mobioNotifyMessage = MobioNotifyMessage.builder()
          .topic(record.topic())
          .partition(record.partition())
          .offset(record.offset())
          .key(consumerRecord.key())
          .value(consumerRecord.value())
          .timestamp(record.timestamp())
          .timestampType(record.timestampType().toString())
          .consumerRecord(record.toString())
          .build();
      persistErrorMessage(mobioNotifyMessage);
    }));

    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> group2KafkaListenerContainerFactory() {
    return kafkaListenerContainerFactory("group_03");
  }

  private RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
    return retryTemplate;
  }

  private SimpleRetryPolicy getSimpleRetryPolicy() {
    return new SimpleRetryPolicy(3);
  }

  public void persistErrorMessage(MobioNotifyMessage mobioNotifyMessage) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = dataSource.getConnection();
      stmt = conn.createStatement();

      PreparedStatement preparedStatement = conn.prepareStatement(
          "INSERT INTO mobio_noti_consume_message_error(`uuid`, `topic`, `_partition`, `offset`, `_key`, `value`, `timestamp`, `timestamp_type`, `consumer_record`)"
              + " VALUES (uuid(), ?, ?, ?, ?, ?, ?, ?, ?)");
      preparedStatement.setString(1, mobioNotifyMessage.getTopic());
      preparedStatement.setInt(2, mobioNotifyMessage.getPartition());
      preparedStatement.setLong(3, mobioNotifyMessage.getOffset());
      preparedStatement.setString(4, mobioNotifyMessage.getKey());
      preparedStatement.setString(5, mobioNotifyMessage.getValue());
      preparedStatement.setLong(6, mobioNotifyMessage.getTimestamp());
      preparedStatement.setString(7, mobioNotifyMessage.getTimestampType());
      preparedStatement.setString(8, mobioNotifyMessage.getConsumerRecord());

      int row = preparedStatement.executeUpdate();

      log.info("Persist the failed message to DB successfully: {}", mobioNotifyMessage);
    } catch (SQLException e) {
      log.error("Error when persist the failed message to DB: {} {}", mobioNotifyMessage, e);
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
