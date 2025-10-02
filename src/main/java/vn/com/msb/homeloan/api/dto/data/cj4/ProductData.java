package vn.com.msb.homeloan.api.dto.data.cj4;

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
public class ProductData {

  String productCode;
  String productName;
  String bannerUrl;
  String mobileBannerUrl;
  String[] benefits;
}
