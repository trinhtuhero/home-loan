package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "properties", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PropertyEntity extends BaseEntity {

  @Column(name = "name")
  String name;

  @Column(name = "description")
  String description;

  @Column(name = "current_value")
  String currentValue;

  @Column(name = "default_value")
  String defaultValue;
}
