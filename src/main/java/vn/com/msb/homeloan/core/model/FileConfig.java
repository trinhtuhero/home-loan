package vn.com.msb.homeloan.core.model;

import java.util.ArrayList;
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
public class FileConfig {

  String uuid;

  String fileConfigCategoryId;

  String code;

  String name;

  String description;

  String shortGuideDesc;

  String fileFormatDesc;

  String longGuideDesc;

  String cmsLongGuideDesc;

  Boolean require;

  Integer maxLimit;

  Integer orderNumber;

  String zipPrefix;

  //Instant createdAt;

  //Instant updatedAt;

  List<LoanUploadFile> loanUploadFileList = new ArrayList<>();

  List<UploadFileComment> comments;

  UploadFileStatus uploadFileStatus;

  FileConfigCategory fileConfigCategory;
}
