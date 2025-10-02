package vn.com.msb.homeloan.api.dto.request.customer;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountInfoByCifResponse {

  List<AccountInfo> lstAccountInfo;

}
