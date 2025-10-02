package vn.com.msb.homeloan.core.model.cic;

import java.util.List;
import lombok.Data;
import vn.com.msb.homeloan.core.model.response.cic.CicCodeDetail;

@Data
public class IdentityCicQueryResult {

  private String cicCode;

  private String identifyNumber;

  private CicQueryResult cicQueryResult;

  private List<CicCodeDetail> cicCodeDetails;

  private boolean pass;

  private String metaData;
}
