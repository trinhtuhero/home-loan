package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "mobio_noti_consume_message_log", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MobioNotiConsumeMessageLogEntity extends BaseEntity {

  @Column(name = "topic")
  String topic;

  @Column(name = "_partition")
  Integer partition;

  @Column(name = "offset")
  Long offset;

  @Column(name = "_key")
  String key;

  @Column(name = "value")
  String value;

  @Column(name = "timestamp")
  Long timestamp;

  @Column(name = "timestamp_type")
  String timestampType;

  // RECORD_METADATA
  @Column(name = "consumer_record")
  String consumerRecord;
}
