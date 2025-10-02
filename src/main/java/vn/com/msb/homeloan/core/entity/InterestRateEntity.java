package vn.com.msb.homeloan.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "interest_rate")
@Getter
@Setter
@ToString
public class InterestRateEntity extends BaseEntity {

  private String name;

  private String key;

  private Integer fromMonth;

  private Integer toMonth;

  private Double interestRate;

  private Double prepaymentFee;
}
