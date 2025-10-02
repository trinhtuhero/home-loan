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
import vn.com.msb.homeloan.core.constant.CMSTabEnum;

@Data
@Entity
@Table(name = "cms_tab_action", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CmsTabActionEntity extends BaseEntity {

  @Column(name = "loan_id")
  String loanId;

  @Column(name = "empl_id")
  String emplId;

  @Column(name = "tab_code")
  @Enumerated(EnumType.STRING)
  CMSTabEnum tabCode;

  @Column(name = "status")
  String status;
}
