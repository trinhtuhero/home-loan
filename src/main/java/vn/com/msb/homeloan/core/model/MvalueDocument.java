package vn.com.msb.homeloan.core.model;

import lombok.*;
import vn.com.msb.homeloan.core.entity.MvalueUploadFilesEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MvalueDocument {
  long id;
  String parentId;
  String code;
  String name;
  String description;
  String collateralType;
  String type;
  List<MvalueUploadFilesEntity> entities;
}
