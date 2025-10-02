package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserSearchParam {

  Paging paging;
  String keyWord;
  String fullName;
  String phone;
  List<String> status;
  String branchCode;
  List<String> roles;
}
