package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.LoanUploadFileStatusEnum;

@Data
@Entity
@Table(name = "loan_upload_files", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoanUploadFileEntity extends BaseEntity {

  @Column(name = "file_config_id")
  String fileConfigId;

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "file_name")
  String fileName;

  @Column(name = "folder")
  String folder;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  LoanUploadFileStatusEnum status;
}
