package vn.com.msb.homeloan.core.constant.collateral;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LoanInvestmentFixedAssetEnum {

  VAY_MUA_BĐS_CO_SAN("VAY_MUA_BĐS_CO_SAN", "Vay mua BĐS có sẵn"),
  VAY_HOAN_VON_MUA_BĐS("VAY_HOAN_VON_MUA_BĐS", "Vay hoàn vốn mua BĐS"),
  VAY_MUA_BĐS_QUA_DAU_GIA_PHAT_MAI("VAY_MUA_BĐS_QUA_DAU_GIA_PHAT_MAI",
      "Vay mua BĐS qua đấu giá phát mại"),
  MUA_PTVT_MMTB_TSCĐ_PHUC_VU_KD("MUA_PTVT_MMTB_TSCĐ_PHUC_VU_KD",
      "Mua PTVT/Máy móc thiết bị/Tài sản cố định khác phục vụ kinh doanh"),
  XD_SUA_CHUA_NHA_XUONG_BĐS_KD("XD_SUA_CHUA_NHA_XUONG_BĐS_KD",
      "Xây dựng sửa chữa nhà xưởng, BĐS làm địa điểm kinh doanh"),
  VAY_HOAN_VON_XD_SUA_CHUA_NHA_XUONG_BĐS_KD("VAY_HOAN_VON_XD_SUA_CHUA_NHA_XUONG_BĐS_KD",
      "Vay hoàn vốn xây dựng sửa chữa nhà xưởng, BĐS làm địa điểm kinh doanh");

  private String code;
  private String name;

  LoanInvestmentFixedAssetEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getName()));
}
