package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CmsUserStatusEnum;
import vn.com.msb.homeloan.core.model.CmsUserInfo;

@Data
@Entity
@Table(name = "cms_users", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@SqlResultSetMappings({@SqlResultSetMapping(name = "CmsUserSearchResult", classes = {
    @ConstructorResult(targetClass = CmsUserInfo.class, columns = {
        @ColumnResult(name = "fullName", type = String.class),
        @ColumnResult(name = "email", type = String.class),
        @ColumnResult(name = "phone", type = String.class),
        @ColumnResult(name = "branchCode", type = String.class),
        @ColumnResult(name = "branchName", type = String.class),
        @ColumnResult(name = "leader", type = String.class),
        @ColumnResult(name = "updatedAt", type = String.class),
        @ColumnResult(name = "createdBy", type = String.class),
        @ColumnResult(name = "status", type = String.class),
        @ColumnResult(name = "role", type = String.class)})})})
public class CmsUserEntity extends AuditEntity {

  @Id
  @Column(name = "empl_id")
  String emplId;

  @Column(name = "full_name")
  String fullName;

  @Column(name = "email")
  String email;

  @Column(name = "branch_code")
  String branchCode;

  @Column(name = "branch_name")
  String branchName;

  @Column(name = "leader_empl_id")
  String leaderEmplId;

  @Column(name = "leader_email")
  String leaderEmail;

  @Column(name = "phone")
  String phone;

  @Column(name = "area")
  String area;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  CmsUserStatusEnum status;

  @Column(name = "created_by")
  String createdBy;

  @Column(name = "updated_by")
  String updatedBy;

  @Column(name = "role")
  String role;
}
