package vn.com.msb.homeloan.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NhomTSMValueEnum {
    BDS("BDS", "2"),
    PTVT("PTVT", "3");
    private final String code;
    private final String name;

}
