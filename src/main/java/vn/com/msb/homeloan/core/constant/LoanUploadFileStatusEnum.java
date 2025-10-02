package vn.com.msb.homeloan.core.constant;

public enum LoanUploadFileStatusEnum {
  UPLOADING("UPLOADING", "Chờ upload file"),

  UPLOADED("UPLOADED", "Đã upload file"),

  DELETE("DELETE", "Đang xóa");
  private String code;
  private String name;

  LoanUploadFileStatusEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
