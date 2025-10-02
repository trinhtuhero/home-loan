package vn.com.msb.homeloan.core.model.download;

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
public class DownloadFile {

  String zipPrefix;
  int length;
  byte[] content;
  String fileName;
  String code;
}
