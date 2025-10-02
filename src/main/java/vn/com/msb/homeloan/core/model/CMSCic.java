package vn.com.msb.homeloan.core.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSCic {

  String loanApplicationId;

  BigDecimal mue;

  BigDecimal dti;

  String name;

  List<CMSCicItem> cicItems;
}
