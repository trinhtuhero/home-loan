package vn.com.msb.homeloan.core.model.cic;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vn.com.msb.homeloan.core.model.response.cic.CicQueryData;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CicQueryResult {

  private Integer code;
  private String cicCode;
  //  private String message;
//  private CicQueryErrorCode category;
  private CicQueryData.CicQueryDetail value;
  private long transactionTime;
  //  private Boolean active;
//  private String status;
//  private CicStep step;
  private boolean isKnockout;
}
