package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
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
public class CMSAmlResponse {

  String uuid;

  String loanApplicationId;

  String idPassport;

  String metaData;

  Boolean pass;

  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Saigon")
  Date checkDate;

  Boolean active;
}
