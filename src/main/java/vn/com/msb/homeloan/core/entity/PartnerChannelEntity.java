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
@Table(name = "partner_channel", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerChannelEntity extends BaseEntity {

  @Column(name = "loan_id")
  String loanId;

  @Column(name = "deal_assignee")
  private String dealAssignee;

  @Column(name = "deal_reference_code")
  private String dealReferenceCode;

  @Column(name = "deal_referral_channel")
  private String dealReferralChannel;
}
