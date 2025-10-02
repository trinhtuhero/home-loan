package vn.com.msb.homeloan.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "mvalue_upload_files", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MvalueUploadFilesEntity extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "document_mvalue_id")
  String documentMvalueId;

  @Column(name = "mvalue_code")
  String mvalueCode;

  @Column(name = "file_name")
  String fileName;

  @Column(name = "folder")
  String folder;

  @Column(name = "status")
  String status;

  @Column(name = "mvalue_status")
  String mvalueStatus;

  @Column(name = "collateral_id")
  String collateralId;

  @Column(name = "upload_user")
  String uploadUser;

  @Column(name = "collateral_type_ECM")
  String collateralTypeECM;

  @Column(name = "loan_upload_file_id")
  String loanUploadFileId;

  @Column(name = "profile_id")
  String profileId;
}
