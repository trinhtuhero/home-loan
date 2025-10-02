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
@Table(name = "file_config", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileConfigEntity extends BaseEntity {

  @Column(name = "file_config_category_id")
  String fileConfigCategoryId;

  @Column(name = "code")
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "description")
  String description;

  @Column(name = "short_guide_desc")
  String shortGuideDesc;

  @Column(name = "file_format_desc")
  String fileFormatDesc;

  @Column(name = "long_guide_desc")
  String longGuideDesc;

  @Column(name = "require")
  Boolean require;

  @Column(name = "max_limit")
  Integer maxLimit;

  @Column(name = "order_number")
  Integer orderNumber;

  @Column(name = "zip_prefix")
  String zipPrefix;

  @Column(name = "cms_long_guide_desc")
  String cmsLongGuideDesc;
}
