package vn.com.msb.homeloan.api.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSCicResponse {

  String loanApplicationId;

  String name;

//    BigDecimal mue;
//
//    BigDecimal dti;

  List<CMSCicItemResponse> cicItems;
}
