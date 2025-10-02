package vn.com.msb.homeloan.api.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSMvalueDocumentResponse {

  long id;
  String parentId;
  String code;
  String name;
  String description;
  String collateralType;
  String type;
  List<CMSMvalueUploadResponse> entities;
}
