package vn.com.msb.homeloan.core.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.model.MobioNotifyMessage;
import vn.com.msb.homeloan.core.service.MobioNotifyService;

@Slf4j
@Component
@RequiredArgsConstructor
public class MobioNotifyEventListener {

  private final MobioNotifyService mobioNotifyService;

  @KafkaListener(topics = {
      "cdp.dataout.notify.cj5"}, containerFactory = "group2KafkaListenerContainerFactory", autoStartup = "${kafka.mobio.consumer-listen-auto-start:false}")
  public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {
    log.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s\n",
        record.topic(), record.partition(), record.offset(), record.key(), record.value()));

    MobioNotifyMessage mobioNotifyMessage = MobioNotifyMessage.builder()
        .topic(record.topic())
        .partition(record.partition())
        .offset(record.offset())
        .key(record.key())
        .value(record.value())
        .timestamp(record.timestamp())
        .timestampType(record.timestampType().toString())
        .consumerRecord(record.toString())
        .build();
    // processing
    mobioNotifyService.process(mobioNotifyMessage);
  }
}
