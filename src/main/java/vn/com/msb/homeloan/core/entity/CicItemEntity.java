package vn.com.msb.homeloan.core.entity;

import java.util.Date;
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
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.cic.CicQueryStatusEnum;

@Data
@Entity
@Table(name = "cic_item", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CicItemEntity extends BaseEntity {

  @Column(name = "cic_id")
  String cicId;

  @Column(name = "cic_code")
  String cicCode;

  @Column(name = "identity_card")
  String identityCard;

  @Enumerated(EnumType.STRING)
  @Column(name = "type_debt")
  CicGroupEnum typeDebt;

  @Enumerated(EnumType.STRING)
  @Column(name = "type_debt_12")
  CicGroupEnum typeDebt12;

  @Enumerated(EnumType.STRING)
  @Column(name = "type_debt_24")
  CicGroupEnum typeDebt24;

  @Enumerated(EnumType.STRING)
  @Column(name = "description")
  CicQueryStatusEnum description;

  @Column(name = "pass")
  Boolean pass;

  @Column(name = "amount_loan_non_msb_secure")
  Long amountLoanNonMsbSecure;

  @Column(name = "amount_loan_non_msb_unsecure")
  Long amountLoanNonMsbUnsecure;

  @Column(name = "amount_loan_msb_secure")
  Long amountLoanMsbSecure;

  @Column(name = "amount_loan_msb_unsecure")
  Long amountLoanMsbUnsecure;

  @Column(name = "amount_card_non_msb")
  Long amountCardNonMsb;

  @Column(name = "amount_card_msb")
  Long amountCardMsb;

  @Column(name = "amount_over_draft_msb")
  Long amountOverDraftMsb;

  @Column(name = "meta_data")
  String metaData;

  @Column(name = "active")
  Boolean active;

  @Column(name = "check_date")
  Date checkDate;

  @Column(name = "cic_content", columnDefinition = "text")
  private String cicContent;
}

