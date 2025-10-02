package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.util.DateUtils;

@Getter
@Setter
@ToString
public class LandTransactionRequest {

  private String uuid;
  @JsonFormat(shape = Shape.STRING, pattern = DateUtils.DATE_FORMAT)
  private Timestamp transactionTime;
  private String propertySale;
  private String buyer;
  private BigDecimal transactionValue;
  private BigDecimal initialCapital;
  private BigDecimal brokerageFees;
  private BigDecimal feeTransfer;
  private BigDecimal profit;
}
