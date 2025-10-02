package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

public enum DownloadStatus {
  READY_FOR_DOWNLOAD(1),
  NEED_UPLOAD_ZIP(0),
  UPLOAD_FAILED_BUSINESS_CAUSE(3),
  NO_FILE_EXISTS(4);

  @Getter
  private final int value;

  DownloadStatus(int value) {
    this.value = value;
  }

}
