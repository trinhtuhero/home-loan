package vn.com.msb.homeloan.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MValueValuationStatusEnum {
    NULL("NULL", "NULL"),
    DNDG("DNDG", "Đã gửi đề nghị định giá"),
    TBDG("TBDG", "Đã có thông báo định giá");

    private final String code;
    private final String name;
}
