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
@Table(name = "file_config_rule", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileConfigRuleEntity extends BaseEntity {

  @Column(name = "file_config_id")
  String fileConfigId;

  @Column(name = "file_rule_id")
  String fileRuleId;
}
