package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.Paging;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserSearchRequest {

  Paging paging;
  String keyWord;
  String fullName;
  String phone;
  List<String> status;
  String branchCode;
  List<String> roles;
}
