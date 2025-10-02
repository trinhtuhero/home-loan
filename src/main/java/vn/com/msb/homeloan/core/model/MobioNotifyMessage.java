package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobioNotifyMessage {

  String topic;
  Integer partition;
  Long offset;
  String key;
  String value;
  Long timestamp;
  String timestampType;
  // RECORD_METADATA
  String consumerRecord;

  @Override
  public String toString() {
    return "MobioNotifyMessage{" +
        "topic='" + topic + '\'' +
        ", partition=" + partition +
        ", offset=" + offset +
        ", key='" + key + '\'' +
        ", value='" + value + '\'' +
        ", timestamp=" + timestamp +
        ", timestampType='" + timestampType + '\'' +
        ", consumerRecord='" + consumerRecord + '\'' +
        '}';
  }
}
