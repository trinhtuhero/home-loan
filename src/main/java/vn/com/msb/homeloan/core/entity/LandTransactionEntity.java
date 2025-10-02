package vn.com.msb.homeloan.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "land_transaction")
public class LandTransactionEntity extends BaseEntity {

  private Timestamp transactionTime;
  private String propertySale;
  private String buyer;
  private BigDecimal transactionValue;
  private BigDecimal initialCapital;
  private BigDecimal brokerageFees;
  private BigDecimal feeTransfer;
  private BigDecimal profit;
  private String otherIncomeId;
}
