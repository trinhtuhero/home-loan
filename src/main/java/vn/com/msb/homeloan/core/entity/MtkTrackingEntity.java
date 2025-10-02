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
import vn.com.msb.homeloan.core.constant.MtkTypeEnum;

@Data
@Entity
@Table(name = "mtk_tracking", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MtkTrackingEntity extends BaseEntity {

  @Column(name = "loan_id")
  String loanId;

  @Column(name = "loan_code")
  String loanCode;

  @Column(name = "utm_source")
  String utmSource;

  @Column(name = "utm_medium")
  String utmMedium;

  @Column(name = "utm_campaign")
  String utmCampaign;

  @Column(name = "utm_content")
  String utmContent;

  @Column(name = "mtk_type")
  @Enumerated(EnumType.STRING)
  MtkTypeEnum mtkType;

  @Column(name = "advise_id")
  String adviseId;
}
