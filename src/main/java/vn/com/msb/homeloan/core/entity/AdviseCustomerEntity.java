package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.AdviseStatusEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.constant.MobioStatusEnum;

@Entity
@Table(name = "advise_customer")
@Getter
@Setter
@ToString
public class AdviseCustomerEntity extends BaseEntity {

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "customer_phone", nullable = false)
  private String customerPhone;


  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private AdviseStatusEnum status;

  @Column(name = "product")
  private String product;

  @Column(name = "province")
  private String province;

  @Column(name = "mobio_status")
  @Enumerated(EnumType.STRING)
  private MobioStatusEnum mobioStatus;
}
