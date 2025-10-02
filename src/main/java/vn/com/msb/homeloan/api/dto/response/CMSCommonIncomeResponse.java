package vn.com.msb.homeloan.api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.ApprovalFlowEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod2Enum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSCommonIncomeResponse {

  String uuid;

  //luồng phê duyệt
  ApprovalFlowEnum approvalFlow;

  //phương pháp ghi nhận thu nhập 1
  RecognitionMethod1Enum recognitionMethod1;

  //phương pháp ghi nhận thu nhập 2
  RecognitionMethod2Enum recognitionMethod2;
}
