package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;

@Data
@Entity
@Table(name = "upload_file_status", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileStatusEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "file_config_id")
  String fileConfigId;

  @Column(name = "is_enough")
  Integer isEnough;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  CommentStatusEnum status;

  @Column(name = "empl_id")
  String emplId;
}
