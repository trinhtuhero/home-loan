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
public class FileConfigCategory {

  String uuid;

  String parentId;

  String code;

  String name;

  String description;

  //Instant createdAt;

  //Instant updatedAt;

  List<FileConfigCategory> fileConfigCategoryList = new ArrayList<>();

  List<FileConfig> fileConfigList = new ArrayList<>();
}
