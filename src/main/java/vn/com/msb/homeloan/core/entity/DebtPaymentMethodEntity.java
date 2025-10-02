package vn.com.msb.homeloan.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "debt_payment_method")
@Getter
@Setter
@ToString
public class DebtPaymentMethodEntity extends BaseEntity {

  private String name;

  private String key;

  private Integer fromMonth;

  private Integer toMonth;

  private String formulaPrincipal;

  private String formulaInterest;

  private String condition;
}
